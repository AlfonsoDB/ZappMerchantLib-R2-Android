package com.zapp.library.merchant.model.callback;

/**
 * Callback interface that will send back to the UI the result coming from the service.
 *
 * @param <T> response class.
 * @author Vasile Chelban
 * @see com.zapp.library.merchant.model.callback.OnSuccessListener
 */
public interface OnResponseListener<T> extends OnSuccessListener<T> {

    /**
     * Callback for notifying about any {@link com.zapp.library.merchant.exception.Error} occurred during a call to any Zapp services.
     *
     * @param error {@link com.zapp.library.merchant.exception.Error} containing information about the context of the error.
     */
    void onFailure(com.zapp.library.merchant.exception.Error error);
}
