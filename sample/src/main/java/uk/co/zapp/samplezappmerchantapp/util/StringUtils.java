package uk.co.zapp.samplezappmerchantapp.util;

import com.zapp.core.CheckoutType;
import com.zapp.core.DeliveryType;
import com.zapp.core.PaymentType;
import com.zapp.core.RTPType;

/**
 * String utility class.
 *
 * @author msagi
 */
public class StringUtils {

    /**
     * Convert the payment type to human readable string.
     *
     * @param paymentType The payment type to convert.
     * @return The human readable version of the payment type.
     */
    public static String toHumanReadableString(final PaymentType paymentType) {
        if (paymentType == null) {
            return null;
        }
        switch (paymentType) {
            case BILL_PAY:
                return "Bill pay";
            case INSTANT_PAYMENT:
                return "Instant payment";
            case SMB:
                return "Small and micro business payment";
        }
        return "unknown payment type";
    }

    /**
     * Convert the checkout type to human readable string.
     *
     * @param checkoutType The checkout type to convert.
     * @return The human readable version of the checkout type.
     */
    public static String toHumanReadableString(final CheckoutType checkoutType) {
        if (checkoutType == null) {
            return null;
        }
        switch (checkoutType) {
            case NORMAL:
                return "Normal checkout";
            case QUICK:
                return "Quick checkout";
        }
        return "unknown checkout type";
    }

    /**
     * Convert the delivery type to human readable string.
     *
     * @param deliveryType The delivery type to convert.
     * @return The human readable version of the delivery type.
     */
    public static String toHumanReadableString(final DeliveryType deliveryType) {
        if (deliveryType == null) {
            return null;
        }
        switch (deliveryType) {
            case ADDRESS:
                return "Deliver to address";
            case COLLECT_IN_STORE:
                return "Collect in store";
            case DIGITAL:
                return "Digital delivery";
            case FACE_2_FACE:
                return "Face to face delivery";
            case SERVICE:
                return "Service delivery";
        }
        return "unknown delivery type";
    }

    /**
     * Convert the payment request type to human readable string.
     *
     * @param rtpType The payment request type to convert.
     * @return The human readable version of the payment request type.
     */
    public static String toHumanReadableString(final RTPType rtpType) {
        if (rtpType == null) {
            return null;
        }
        switch (rtpType) {
            case IMMEDIATE:
                return "Immediate";
            case DEFERRED:
                return "Deferred";
        }
        return "unknown payment request type";
    }
}
