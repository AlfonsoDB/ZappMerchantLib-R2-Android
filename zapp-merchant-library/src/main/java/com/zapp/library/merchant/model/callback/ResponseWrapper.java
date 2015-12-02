package com.zapp.library.merchant.model.callback;

/**
 * A simple typed wrapper to get/pass any kind of typed response.
 *
 * @author Vasile Chelban on 6/27/14.
 */
public final class ResponseWrapper<T> {

    /**
     * Object that need to be delivered.
     */
    private final T response;

    /**
     * @param response Object that need to be delivered
     */
    public ResponseWrapper(T response) {
        this.response = response;
    }

    /**
     * Get the wrapped response object.
     *
     * @return the {@link T} response object.
     */
    public T getResponse() {
        return response;
    }
}
