package uk.co.zapp.samplezappmerchantapp.network.response;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import com.zapp.core.Address;
import com.zapp.core.DeliveryAddress;
import com.zapp.core.DeliveryAddressType;
import com.zapp.core.TransactionStatus;
import com.zapp.core.User;
import com.zapp.core.ZappModelValidationException;

import android.util.Log;

import java.lang.reflect.Type;

import uk.co.zapp.samplezappmerchantapp.util.Const;

/**
 * A custom deserializer for {@link PaymentNotificationResponse}
 */
public class PaymentNotificationDeserializer extends CustomDeserializer implements JsonDeserializer<PaymentNotificationResponse> {

    private static final String TAG = PaymentNotificationDeserializer.class.getSimpleName();

    @Override
    public PaymentNotificationResponse deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) {
        final JsonObject jsonObject = json.getAsJsonObject();
        final PaymentNotificationResponse response = new PaymentNotificationResponse();

        response.setStatus(getStringElement(jsonObject, "status"));
        response.setErrorMessage(getStringElement(jsonObject, "errorMessage"));
        response.setErrorCode(getIntElement(jsonObject, "errorCode"));

        final DeliveryAddress deliveryAddress = getAddressDetails(jsonObject, "addressDetails");
        final String email = getStringElement(jsonObject, "email");
        final String mobile = getStringElement(jsonObject, "mobile");

        response.setAddressDetails(deliveryAddress);
        response.setEmail(email);
        response.setMobile(mobile);
        setConsumerEmailAndMobile(deliveryAddress, email, mobile);

        response.setInitiatorIsAuthorizer(getBooleanElement(jsonObject, "initiatorIsAuthorizer"));
        response.setTransactionStatus(toZappPaymentStatus(getIntElement(jsonObject, "txnStatus")));
        return response;
    }

    /**
     * Sets the email if present to delivery address addressee field.
     *
     * @param deliveryAddress The delivery address to get the addressee from.
     * @param email           The email to be set to addressee field.
     * @param mobile          The phone number to be set to addressee field.
     */
    private void setConsumerEmailAndMobile(final DeliveryAddress deliveryAddress, final String email, final String mobile) {
        if (deliveryAddress != null && email != null) {
            final User addressee = deliveryAddress.getAddressee();
            if (addressee != null) {
                addressee.setEmail(email);
                addressee.setPhone(mobile);
            }
        }
    }

    /**
     * Retrieves the {@link DeliveryAddress} from the json body.
     *
     * @param jsonObject The json body to retrieve the delivery address from.
     * @param name       The json delivery address field name.
     * @return The DeliveryAddress.
     */
    private DeliveryAddress getAddressDetails(final JsonObject jsonObject, final String name) {
        final JsonElement jsonElement = jsonObject.get(name);
        DeliveryAddress address = null;
        if (jsonElement != null) {
            try {
                final JsonObject addressJson = jsonElement.getAsJsonObject();
                final User user = new User(
                        getStringElement(addressJson, "firstName"),
                        getStringElement(addressJson, "lastName"),
                        /*middle*/ null,
                        /*title*/ null,
                        /*email*/ null,
                        /*phone*/ null);

                address = new DeliveryAddress(
                        /*identifier*/ null,
                        DeliveryAddressType.GENERAL,
                        user,
                        getStringElement(addressJson, "addressLine1"),
                        getStringElement(addressJson, "addressLine2"),
                        getStringElement(addressJson, "addressLine3"),
                        getStringElement(addressJson, "addressLine4"),
                        getStringElement(addressJson, "addressLine5"),
                        getStringElement(addressJson, "addressLine6"),
                        getStringElement(addressJson, "postCode"),
                        Address.UK);

            } catch (ZappModelValidationException e) {
                Log.w(Const.DEBUG_TAG, "Validation error: required field missing.");
            }
        }
        return address;
    }

    /**
     * Converts payment status value from http response to Zapp {@link TransactionStatus} object.
     */
    public static TransactionStatus toZappPaymentStatus(final int status) {
        TransactionStatus transactionStatus = null;

        switch (status) {
            case 0:
                transactionStatus = TransactionStatus.AUTHORIZED;
                break;
            case 1:
                transactionStatus = TransactionStatus.DECLINED;
                break;
            case 2:
                transactionStatus = TransactionStatus.INCOMPLETE;
                break;
            case 3:
                transactionStatus = TransactionStatus.IN_PROGRESS;
                break;
            case 4:
                transactionStatus = TransactionStatus.PAYMENT_ENQUIRY_FAILED;
                break;
            case 5:
                transactionStatus = TransactionStatus.AWAITING_SETTLEMENT;
                break;
            default:
                Log.w(TAG, String.format("Unknown payment status: %d", status));
        }

        return transactionStatus;
    }

}
