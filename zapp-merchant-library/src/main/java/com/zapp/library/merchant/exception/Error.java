package com.zapp.library.merchant.exception;

import com.zapp.library.merchant.R;

import android.content.Context;

import java.io.Serializable;

/**
 * Flexible error class provides type, title, message and arguments features for Zapp merchant library.
 *
 * @author msagi
 */
public class Error implements Serializable {

    /**
     * The type of the error.
     */
    private ErrorType mType = ErrorType.GENERIC_INTERNAL_ERROR;

    /**
     * The custom title of the error.
     */
    private final String mCustomTitle;

    /**
     * The custom message of the error.
     */
    private final String mCustomMessage;

    /**
     * The arguments for the error.
     */
    private final String[] mArguments;

    /**
     * Create new error instance using given error type, default title and message.
     *
     * @param type The error type to use.
     */
    public Error(final ErrorType type) {
        this(type, null, null);
    }

    /**
     * Create new error instance using given error type, default title and message and custom arguments.
     *
     * @param type The error type to use.
     * @param args The arguments to use.
     */
    public Error(final ErrorType type, final String[] args) {
        this(type, null, null, args);
    }

    /**
     * Create new error instance using given error type, custom title and custom message.
     *
     * @param type          The error type to use.
     * @param customTitle   The custom title to use.
     * @param customMessage The custom message to use.
     */
    public Error(final ErrorType type, final String customTitle, final String customMessage) {
        this(type, customTitle, customMessage, null);
    }

    /**
     * Create new error instance using given error type, custom title, custom message and arguments.
     *
     * @param type          The error type to use.
     * @param customTitle   The custom error title to use.
     * @param customMessage The custom error message to use.
     * @param args          The arguments to use.
     */
    public Error(final ErrorType type, final String customTitle, final String customMessage, final String[] args) {
        if (mType == null) {
            throw new IllegalArgumentException("type == null");
        }
        mType = type;
        mCustomTitle = customTitle;
        mCustomMessage = customMessage;
        if (type == ErrorType.GENERIC_VALIDATION_ERROR && args.length != 1) {
            throw new IllegalArgumentException("type == GENERIC_VALIDATION_ERROR && args.length != 1");
        }
        mArguments = args;
    }

    /**
     * Get the type of this error.
     *
     * @return The {@link com.zapp.library.merchant.exception.ErrorType} error type.
     */
    public ErrorType getType() {
        return mType;
    }

    /**
     * Get the language specific error message for this error.
     *
     * @param context The context to use.
     * @return The {@link java.lang.String} error message for this error.
     */
    public String getErrorMessage(final Context context) {
        if (mCustomMessage != null) {
            return mCustomMessage;
        }
        if (context == null) {
            return null;
        }

        final String errorMessage;
        switch (mType) {
            case CONSUMER_CUSTOM_ERROR:
                errorMessage = context.getString(R.string.zapp_error_message_consumer);
                break;
            case NETWORK_ERROR:
                errorMessage = context.getString(R.string.zapp_error_message_network_error);
                break;
            case INVALID_APTRID:
                errorMessage = context.getString(R.string.zapp_error_msg_invalid_aptrid);
                break;
            case INVALID_APTID:
                errorMessage = context.getString(R.string.zapp_error_msg_invalid_aptid);
                break;
            case INVALID_SETTLEMENT_RETRIEVAL_ID:
                errorMessage = context.getString(R.string.zapp_error_msg_invalid_settlement_retrieval_id);
                break;
            case INVALID_MERCHANT_ID:
                errorMessage = context.getString(R.string.zapp_error_msg_invalid_merchant_id);
                break;
            case INVALID_BRN:
                errorMessage = context.getString(R.string.zapp_error_msg_invalid_brn);
                break;
            case INVALID_SERVICE_DELEGATE_RESPONSE:
                errorMessage = context.getString(R.string.zapp_error_msg_service_delegate_response);
                break;
            case PAYMENT_NOT_CONFIRMED:
                errorMessage = context.getString(R.string.zapp_error_msg_payment_not_confirmed);
                break;
            case INVALID_SERVER_RESPONSE:
                errorMessage = context.getString(R.string.zapp_error_msg_invalid_server_response);
                break;
            case GENERIC_VALIDATION_ERROR:
                errorMessage = context.getString(R.string.zapp_error_msg_generic_validation_error, mArguments[0]);
                break;
            case GENERIC_INTERNAL_ERROR:
            default:
                errorMessage = context.getString(R.string.zapp_error_msg_generic_internal_error);
                break;
        }
        return errorMessage;
    }

    /**
     * Get the language specific error title for this error.
     *
     * @param context The context to use.
     * @return The {@link java.lang.String} error title for this error.
     */
    public String getErrorTitle(final Context context) {
        if (mCustomTitle != null) {
            return mCustomTitle;
        }
        if (context == null) {
            return null;
        }

        final String errorTitle;
        switch (mType) {
            case GENERIC_VALIDATION_ERROR:
                errorTitle = context.getString(R.string.zapp_error_title_generic_validation_error);
                break;
            case CONSUMER_CUSTOM_ERROR:
            case NETWORK_ERROR:
            case INVALID_APTRID:
            case INVALID_APTID:
            case INVALID_MERCHANT_ID:
            case INVALID_BRN:
            case INVALID_SERVICE_DELEGATE_RESPONSE:
            case PAYMENT_NOT_CONFIRMED:
            case INVALID_SERVER_RESPONSE:
            case GENERIC_INTERNAL_ERROR:
            default:
                errorTitle = context.getString(R.string.zapp_error_title_generic);
                break;
        }
        return errorTitle;
    }
}
