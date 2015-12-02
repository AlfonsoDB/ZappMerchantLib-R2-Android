package uk.co.zapp.samplezappmerchantapp.network.request;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import com.zapp.core.Address;
import com.zapp.core.AmountDetail;
import com.zapp.core.BillDetails;
import com.zapp.core.CurrencyAmount;
import com.zapp.core.DeliveryType;
import com.zapp.core.Merchant;
import com.zapp.core.MessageType;
import com.zapp.core.PaymentRequest;
import com.zapp.core.PaymentType;
import com.zapp.core.RTPType;
import com.zapp.core.User;

import android.util.Log;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A custom serializer for a request to pay.
 */
public class PaymentRequestSerializer implements JsonSerializer<PaymentRequest> {

    /**
     * Log tag.
     */
    private static final String TAG = PaymentRequestSerializer.class.getSimpleName();

    /**
     * Date formatter to convert dates from string to date and from date to string.
     */
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Date formatter for deferred payment expire date.
     */
    private static final SimpleDateFormat DEFRD_EXP_DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    @Override
    public JsonElement serialize(final PaymentRequest paymentRequest, final Type typeOfSrc, final JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();

        final Merchant merchant = paymentRequest.getMerchant();
        final User user = paymentRequest.getUser();
        final String email = user == null ? null : user.getEmail();
        final RTPType rtpType = paymentRequest.getRtpType();

        jsonObject.addProperty("rtpType", paymentRequest.getRtpType().ordinal());
        jsonObject.addProperty("checkoutType", paymentRequest.getCheckoutType().ordinal());
        jsonObject.addProperty("sourceType", fromZappMessageType(paymentRequest.getMessageType()));
        jsonObject.add("addressDetails", getAddressDetails(paymentRequest));
        jsonObject.addProperty("email", email);
        jsonObject.addProperty("merchantId", merchant.getIdentifier());
        jsonObject.addProperty("merchantCallbackUrl", paymentRequest.getMerchantCallbackUrl());
        jsonObject.addProperty("paymentType", fromZappPaymentType(paymentRequest.getPaymentType()));
        jsonObject.addProperty("deliveryType", fromZappDeliveryType(paymentRequest.getDeliveryType()));
        jsonObject.add("billDetails", getBillDetails(paymentRequest.getBillDetails()));
        if (rtpType == RTPType.DEFERRED) {
            jsonObject.addProperty("acrType", paymentRequest.getAcrType().ordinal());
            final Date defrdRTPExpDateTime = paymentRequest.getDefrdRTPExpDateTime();
            if (defrdRTPExpDateTime != null) {
                jsonObject.addProperty("defrdRTPExpDateTime", DEFRD_EXP_DATE_FORMATTER.format(defrdRTPExpDateTime));
            }
            jsonObject.addProperty("defrdRTPAgrmtAmount", paymentRequest.getDefrdRTPAgrmtAmount().getValue());
            final CurrencyAmount defrdRTPMaxAgrdAmount = paymentRequest.getDefrdRTPMaxAgrdAmount();
            if (defrdRTPMaxAgrdAmount != null) {
                jsonObject.addProperty("defrdRTPMaxAgrdAmount", defrdRTPMaxAgrdAmount.getValue());
            }
            final AmountDetail[] defrdAmountDetails = paymentRequest.getDefrdAmountDetails();
            if (defrdAmountDetails != null) {
                jsonObject.add("defrdAmountDetails", getAmountDetails(defrdAmountDetails));
            }
        } else {
            jsonObject.addProperty("totalAmount", paymentRequest.getAmount().getValue());
        }

        if (paymentRequest.getPaymentType() == PaymentType.SMB) {
            final JsonObject browserInfo = new JsonObject();
            browserInfo.addProperty("activeHeaders", "NA");
            browserInfo.addProperty("screen", "NA");
            browserInfo.addProperty("timeZone", "NA");
            browserInfo.addProperty("userAgent", "Android");
            jsonObject.add("browserInfo", browserInfo);
        }

        return jsonObject;
    }

    /**
     * Get message type by Zapp message type.
     *
     * @param messageType The Zapp message type.
     * @return The message type or -1 if unable to map from Zapp message type.
     */
    private static Integer fromZappMessageType(final MessageType messageType) {
        Integer type;
        switch (messageType) {
            case BRN:
                type = 0;
                break;
            case MOBILE:
                type = 1;
                break;
            case GENERAL:
                type = 2;
                break;
            default:
                Log.w(TAG, "Unable to map messageType type (" + messageType + ")");
                type = null;
                break;
        }
        return type;
    }

    /**
     * Converts gateway address details.
     *
     * @param address The Zapp address.
     * @param user    The Zapp user.
     * @return the JsonObject of address.
     */
    private JsonElement getAddressDetails(final PaymentRequest paymentRequest) {
        final DeliveryType deliveryType = paymentRequest.getDeliveryType();
        final Address address = paymentRequest.getAddress();
        JsonObject addressJson = null;
        if (address != null) {
            addressJson = new JsonObject();
            switch (deliveryType) {
                case COLLECT_IN_STORE:
                    final Merchant merchant = paymentRequest.getMerchant();
                    if (merchant != null) {
                        addressJson.addProperty("firstName", merchant.getName());
                        addressJson.addProperty("lastName", "");
                    }
                    break;
                default:
                    final User user = paymentRequest.getUser();
                    if (user != null) {
                        addressJson.addProperty("firstName", user.getFirstName());
                        addressJson.addProperty("lastName", user.getLastName());
                    }
                    break;
            }
            addressJson.addProperty("addressLine1", address.getLine1());
            addressJson.addProperty("addressLine2", address.getLine2());
            addressJson.addProperty("addressLine3", address.getLine3());
            addressJson.addProperty("addressLine4", address.getLine4());
            addressJson.addProperty("addressLine5", address.getLine5());
            addressJson.addProperty("addressLine6", address.getLine6());
            addressJson.addProperty("postCode", address.getPostCode());
        }
        return addressJson;
    }

    /**
     * Mapping between core lib {@link com.zapp.core.BillDetails} and gateway representation of it.
     *
     * @param billDetails The bill details.
     * @return the JsonObject of bill details.
     */
    private JsonObject getBillDetails(final BillDetails billDetails) {
        JsonObject billDetailsObj = null;
        if (billDetails != null) {
            billDetailsObj = new JsonObject();
            billDetailsObj.addProperty("accId", billDetails.getAccountId());
            billDetailsObj.addProperty("ref", billDetails.getReference());
            billDetailsObj.addProperty("periodFrom", DATE_FORMATTER.format(billDetails.getPeriodFrom()));
            billDetailsObj.addProperty("periodTo", DATE_FORMATTER.format(billDetails.getPeriodTo()));
        }
        return billDetailsObj;
    }

    /**
     * Mapping between core lib {@link com.zapp.core.AmountDetail} array and its gateway representation.
     *
     * @param amountDetails The amount details array.
     * @return the JsonArray of amount details.
     */
    private JsonArray getAmountDetails(final AmountDetail[] amountDetails) {
        if (amountDetails == null) {
            return null;
        }
        JsonArray jsonAmountDetails = new JsonArray();
        for (final AmountDetail amountDetail : amountDetails) {
            JsonObject jsonAmountDetail = new JsonObject();
            jsonAmountDetail.addProperty("type", amountDetail.getType().ordinal());
            final String description = amountDetail.getDescription();
            if (description != null) {
                jsonAmountDetail.addProperty("description", description);
            }
            jsonAmountDetail.addProperty("price", amountDetail.getPrice());
            final String rate = amountDetail.getRate();
            if (rate != null) {
                jsonAmountDetail.addProperty("rate", rate);
            }
            jsonAmountDetails.add(jsonAmountDetail);
        }
        return jsonAmountDetails;
    }

    /**
     * Get payment type by Zapp payment type.
     *
     * @param paymentType The Zapp payment type.
     * @return The payment type or -1 if unable to map from Zapp payment type.
     */
    private static Integer fromZappPaymentType(final PaymentType paymentType) {
        Integer type;
        switch (paymentType) {
            case INSTANT_PAYMENT:
                type = 1;
                break;
            case BILL_PAY:
                type = 0;
                break;
            case SMB:
                type = 2;
                break;
            default:
                Log.w(TAG, "Unable to map paymentType type (" + paymentType + ")");
                type = null;
                break;
        }
        return type;
    }

    /**
     * Get delivery type by Zapp delivery type.
     *
     * @param deliveryType The Zapp delivery type.
     * @return The delivery type or -1 if unable to map from Zapp delivery type.
     */
    public static Integer fromZappDeliveryType(final DeliveryType deliveryType) {
        Integer type;
        switch (deliveryType) {
            case ADDRESS:
                type = 0;
                break;
            case COLLECT_IN_STORE:
                type = 1;
                break;
            case DIGITAL:
                type = 2;
                break;
            case FACE_2_FACE:
                type = 3;
                break;
            case SERVICE:
                type = 4;
                break;
            default:
                Log.w(TAG, "Unable to map deliveryType type (" + deliveryType + ")");
                type = null;
                break;
        }
        return type;
    }
}
