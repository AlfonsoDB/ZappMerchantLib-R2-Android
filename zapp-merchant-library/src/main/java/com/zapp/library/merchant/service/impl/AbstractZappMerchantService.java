package com.zapp.library.merchant.service.impl;

import com.zapp.library.merchant.model.callback.OnResponseListener;
import com.zapp.library.merchant.model.callback.ResponseWrapper;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * The base class that contains necessary methods for executing tasks in the background.
 * It provides queue where all runnables are placed and executed sequentially managed by a service executor thread.
 * Also it provides methods for model validation and invocation of the callback methods in the UI thread.
 * <p/>
 *
 * @author Vasile Chelban on 6/27/2014.
 */
abstract class AbstractZappMerchantService {

    /**
     * The executor thread.
     */
    private ZappServiceExecutorThread mExecutorThread;

    /**
     * The queue for runnables.
     */
    private final Deque<Runnable> mRunnables;

    /**
     * Create new instance.
     */
    AbstractZappMerchantService() {
        mRunnables = new ArrayDeque<Runnable>();
    }

    /**
     * A thread that executes all runnables from the queue one by one.
     */
    private class ZappServiceExecutorThread extends Thread {

        @Override
        public void run() {
            boolean hasTasks;
            synchronized (mRunnables) {
                hasTasks = !mRunnables.isEmpty();
            }

            while (hasTasks) {
                final Runnable runnable = mRunnables.pollFirst();
                runnable.run();
                synchronized (mRunnables) {
                    hasTasks = !mRunnables.isEmpty();
                }
            }
        }
    }

    /**
     * Posts a the specified {@link java.lang.Runnable} to android main {@link android.os.Looper} (handling the UI {@link java.lang.Thread}).
     *
     * @param runnable {@link java.lang.Runnable} to be executed on UI {@link java.lang.Thread}
     * @return <b>true</b> if the {@link java.lang.Runnable} was posted successfully, <b>false</b> - otherwise.
     */
    boolean executeOnUIThread(final Runnable runnable) {
        boolean result = false;
        if (runnable != null) {
            final Handler uiHandler = new Handler(Looper.getMainLooper());
            result = uiHandler.post(runnable);
        }
        return result;
    }

    /**
     * Adds a specified {@link java.lang.Runnable} to the execution queue of this {@link java.lang.Thread}
     * <p/>
     * It will be automatically removed from the queue when it will finish.
     */
    void addRunnable(final Runnable runnable) {
        if (runnable == null) {
            throw new IllegalArgumentException("Can't add to execution queue and empty Runnable. Think again!");
        }
        synchronized (mRunnables) {
            mRunnables.addLast(runnable);
        }
        startWorkerThreadIfNeeded();
    }

    /**
     * Creates an executor thread and executes all runnables from the queue.
     */
    private void startWorkerThreadIfNeeded() {
        if (mExecutorThread == null || !mExecutorThread.isAlive()) {
            mExecutorThread = new ZappServiceExecutorThread();
            mExecutorThread.start();
        }
    }

    /**
     * Ensures that the listener is not null in order to enforce the user to provide a callback implementation for the service methods.
     *
     * @param responseListener the callback implementation for notifying the application with a response.
     * @throws IllegalArgumentException is thrown if the responseListener is null.
     */
    void assertListenerNotNull(final OnResponseListener responseListener) throws IllegalArgumentException {
        if (responseListener == null) {
            throw new IllegalArgumentException("Parameter responseListener can't be null.");
        }
    }
}
