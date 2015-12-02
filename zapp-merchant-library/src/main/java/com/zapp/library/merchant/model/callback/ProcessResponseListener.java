package com.zapp.library.merchant.model.callback;


import com.zapp.library.merchant.exception.ErrorClickHandler;

/**
 * The purpose of this class is to notify the UI when the background operation has been started and finished.
 * It was designed to be as an abstract class in order to not force the user to implement these methods.
 *
 * @author vchelban on 5/12/2014.
 */
public abstract class ProcessResponseListener<T> implements OnResponseListener<T> {

    /**
     * Empty hook method which is called before {@link com.zapp.library.merchant.service.delegate.IMerchantNetworkServiceDelegate network delegate}
     * starts to execute the request.
     * <br>
     * Default implementation is empty: override as needed.
     * <br>
     * <b>Note:</b> this method is called on UI thread to change the UI as needed before
     * {@link com.zapp.library.merchant.service.delegate.IMerchantNetworkServiceDelegate network delegate}
     * implementation executes a long-running request.
     */
    public void onStart() {
    }

    /**
     * Callback for notifying about any {@link com.zapp.library.merchant.exception.Error} occurred
     * during a call to any Zapp merchant service.
     *
     * @param error   {@link com.zapp.library.merchant.exception.Error} containing information about the context of the error.
     * @param handler handles post failure actions
     */
    public void onFailure(final com.zapp.library.merchant.exception.Error error, final ErrorClickHandler handler) {
    }
}
