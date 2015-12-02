package uk.co.zapp.samplezappmerchantapp.network.response;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import com.zapp.library.merchant.model.SettlementStatus;

import android.util.Log;

import java.lang.reflect.Type;

/**
 * A custom deserializer for {@link SettlementStatusResponse}.
 */
public class SettlementStatusDeserializer extends CustomDeserializer implements JsonDeserializer<SettlementStatusResponse> {

    private static final String TAG = SettlementStatusDeserializer.class.getSimpleName();

    @Override
    public SettlementStatusResponse deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) {
        final JsonObject jsonObject = json.getAsJsonObject();
        final SettlementStatusResponse response = new SettlementStatusResponse();

        response.setStatus(getStringElement(jsonObject, "status"));
        response.setErrorMessage(getStringElement(jsonObject, "errorMessage"));
        response.setErrorCode(getIntElement(jsonObject, "errorCode"));

        response.setSettlementStatus(toZappSettlementStatus(getIntElement(jsonObject, "settlementStatus")));

        return response;
    }

    /**
     * Converts settlement status value from http response to Zapp {@link SettlementStatus} object.
     *
     * @param status value received in json body.
     * @return The {@link SettlementStatus} value.
     */
    private static SettlementStatus toZappSettlementStatus(final int status) {
        SettlementStatus settlementStatus = null;

        switch (status) {
            case 0:
                settlementStatus = SettlementStatus.AUTHORISED;
                break;
            case 1:
                settlementStatus = SettlementStatus.DECLINED;
                break;
            case 2:
                settlementStatus = SettlementStatus.INCOMPLETE;
                break;
            case 3:
                settlementStatus = SettlementStatus.IN_PROGRESS;
                break;
            case 4:
                settlementStatus = SettlementStatus.PAYMENT_ENQUIRY_FAILED;
                break;
            case 5:
                settlementStatus = SettlementStatus.NEVER_AUTHORISED;
                break;
            case 6:
                settlementStatus = SettlementStatus.LATE_AUTHORISED;
                break;
            case 7:
                settlementStatus = SettlementStatus.NEVER_CONFIRMED;
                break;
            default:
                Log.w(TAG, String.format("Unknown settlement status: %d", status));
        }

        return settlementStatus;
    }
}
