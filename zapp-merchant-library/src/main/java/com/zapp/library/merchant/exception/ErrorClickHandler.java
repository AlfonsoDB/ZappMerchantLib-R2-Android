package com.zapp.library.merchant.exception;

/**
 * A callback that notifies the UI when closes the error dialog.
 */
public interface ErrorClickHandler {

    /**
     * Called when the error dialog (or other UI element) is dismissed/closed.
     */
    void onClose();
}