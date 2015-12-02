package com.zapp.library.merchant.test;

import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoAssertionError;
import org.mockito.exceptions.verification.ArgumentsAreDifferent;
import org.mockito.exceptions.verification.TooLittleActualInvocations;
import org.mockito.exceptions.verification.WantedButNotInvoked;
import org.mockito.internal.verification.api.VerificationData;
import org.mockito.verification.VerificationMode;

/**
 * An extension of {@link org.mockito.internal.verification.Times} verification mode. Used to verify methods invoked asynchronously.
 */
public final class Await implements VerificationMode {

    /**
     * The wait timeout.
     */
    private static final int TIMEOUT_10_SECONDS = 10000;

    /**
     * The verification mode which verifies that the method is invoked only once.
     */
    private final VerificationMode delegate;

    /**
     * Creates a new verification mode which verifies that the method is invoked only once.
     */
    public Await() {
        this(Mockito.times(1));
    }

    public Await(final VerificationMode delegate) {
        this.delegate = delegate;
    }

    @Override
    public void verify(final VerificationData data) {
        final Object mock = data.getWanted().getInvocation().getMock();
        synchronized (mock) {
            while (true) {
                try {
                    delegate.verify(data);
                    break;
                } catch (ArgumentsAreDifferent | TooLittleActualInvocations | WantedButNotInvoked
                        | org.mockito.exceptions.verification.junit.ArgumentsAreDifferent e) {
                    //catch the exception first time, wait until the asynchronous method finishes and verify again
                }
                try {
                    mock.wait(TIMEOUT_10_SECONDS);
                } catch (InterruptedException e) {
                    throw new MockitoAssertionError("interrupted");
                }
            }
        }
    }
}