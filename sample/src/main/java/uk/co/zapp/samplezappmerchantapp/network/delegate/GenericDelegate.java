package uk.co.zapp.samplezappmerchantapp.network.delegate;

import com.zapp.library.merchant.exception.Error;
import com.zapp.library.merchant.exception.ErrorType;
import com.zapp.library.merchant.model.callback.OnResponseListener;
import com.zapp.library.merchant.model.callback.ResponseWrapper;

import android.content.Context;

import uk.co.zapp.samplezappmerchantapp.R;
import uk.co.zapp.samplezappmerchantapp.util.NetworkUtils;

/**
 * Generic delegate base class.
 */
public abstract class GenericDelegate {

    /**
     * The context.
     */
    protected Context mContext;

    /**
     * Create new instance.
     *
     * @param context The context to use.
     */
    protected GenericDelegate(final Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context == null");
        }
        mContext = context.getApplicationContext();
    }

    /**
     * Check for network.
     *
     * @return Network error if network is unavailable, null otherwise.
     */
    protected Error checkNetwork() {
        if (!NetworkUtils.isNetworkConnectionAvailable(mContext)) {
            return new Error(ErrorType.NETWORK_ERROR, mContext.getString(R.string.exception_network_title), mContext.getString(R.string.exception_no_network_msg));
        }
        return null;
    }

    /**
     * Dispatch operation result to the listener.
     *
     * @param error            The error.
     * @param result           The result.
     * @param responseListener The response listener.
     * @param <T>              Generic type.
     */
    protected <T> void processAndDispatchOperationResult(final Error error, final T result, final OnResponseListener<T> responseListener) {
        if (error != null) {
            responseListener.onFailure(error);
        } else {
            responseListener.onSuccess(new ResponseWrapper<T>(result));
        }
    }
}
