package uk.co.zapp.samplezappmerchantapp.network.response;

/**
 * Base server response data object.
 *
 * Note: field names do not match coding standards. This is a trade off to be able to easily convert this data object to network request body using Gson.
 */
public class BaseServerResponse {

    /**
     * The status.
     */
    private String status;

    /**
     * The error message.
     */
    private String errorMessage;

    /**
     * The error code.
     */
    private int errorCode;

    /**
     * Constant for generic error error code.
     */
    public static final int ERR_CODE_NOT_VERIFIED = 12;

    /**
     * This error code's  is server-specific.
     */
    public static final int ERR_CODE_REQUEST_INVALIDATED = 4;

    /**
     * Get the error code.
     * @return The error code.
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * Set the error code.
     * @param errorCode The error code to use.
     */
    public void setErrorCode(final int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Get the status.
     * @return The {@link java.lang.String} status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Set the status.
     * @param status The status to use.
     */
    public void setStatus(final String status) {
        this.status = status;
    }

    /**
     * Get the error message.
     * @return The {@link java.lang.String} error message.
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(final String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
