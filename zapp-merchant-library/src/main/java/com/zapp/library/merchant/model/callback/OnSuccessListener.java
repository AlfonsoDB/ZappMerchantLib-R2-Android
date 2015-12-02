package com.zapp.library.merchant.model.callback;

/**
 * Callback interface that will send back to the UI the success result coming from the service.
 *
 * @author Vasile Chelban on 6/27/2014.
 */
public interface OnSuccessListener<T> {

    /**
     * Callback to notify implementers about a successful Zapp operation.
     *
     * @param response the typed response returned after a successful Zapp operation.
     */
    void onSuccess(ResponseWrapper<T> response);
}
