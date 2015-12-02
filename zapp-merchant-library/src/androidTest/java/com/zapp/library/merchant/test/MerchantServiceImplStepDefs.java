package com.zapp.library.merchant.test;

import com.zapp.core.Address;
import com.zapp.core.BillDetails;
import com.zapp.core.CheckoutType;
import com.zapp.core.DeliveryType;
import com.zapp.core.Merchant;
import com.zapp.core.PaymentRequest;
import com.zapp.core.PaymentType;
import com.zapp.core.RTPType;
import com.zapp.core.RetrievalMethod;
import com.zapp.core.Transaction;
import com.zapp.core.TransactionId;
import com.zapp.core.User;
import com.zapp.core.ZappModelValidationException;
import com.zapp.library.merchant.exception.Error;
import com.zapp.library.merchant.exception.ErrorType;
import com.zapp.library.merchant.model.PaymentRequestBuilder;
import com.zapp.library.merchant.model.SettlementStatus;
import com.zapp.library.merchant.model.callback.OnResponseListener;
import com.zapp.library.merchant.model.callback.ProcessResponseListener;
import com.zapp.library.merchant.model.callback.ResponseWrapper;
import com.zapp.library.merchant.service.delegate.IMerchantNetworkServiceDelegate;
import com.zapp.library.merchant.service.delegate.IZappMerchantUIDelegate;
import com.zapp.library.merchant.service.impl.MerchantServiceImpl;
import com.zapp.library.merchant.service.impl.ZappMerchantServiceFactory;
import com.zapp.library.merchant.utils.CurrencyAmountUtils;

import org.mockito.ArgumentCaptor;
import org.mockito.MockSettings;
import org.mockito.Mockito;
import org.mockito.invocation.DescribedInvocation;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.listeners.InvocationListener;
import org.mockito.listeners.MethodInvocationReport;
import org.mockito.stubbing.Answer;
import org.mockito.verification.VerificationMode;

import android.content.Context;
import android.test.InstrumentationTestCase;
import android.util.Log;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.withSettings;

/**
 * Created by msagi on 22/06/15.
 */

public class MerchantServiceImplStepDefs extends InstrumentationTestCase {

    /**
     * Tag for logging.
     */
    private static final String TAG = "Test.MechantService";

    /**
     * Time out for async method.
     */
    public static final int TIMEOUT_10_SECONDS = 10;

    /**
     * The payment request builder.
     */
    private final PaymentRequestBuilder paymentRequestBuilder = new PaymentRequestBuilder();

    /**
     * The request to pay type of the request.
     */
    private RTPType mRTPType;

    /**
     * The payment type of the request.
     */
    private PaymentType mPaymentType;

    /**
     * The BRN (Zapp Code).
     */
    private String mZappCode;

    /**
     * The transaction id.
     */
    private TransactionId mTransactionId;

    /**
     * The transaction.
     */
    private Transaction mTransaction;

    /**
     * The merchant network service delegate instance.
     */
    private static IMerchantNetworkServiceDelegate sMerchantNetworkServiceDelegate;

    /**
     * The merchant UI delegate instance.
     */
    private static IZappMerchantUIDelegate sZappMerchantUIDelegate;

    /**
     * The static merchant service instance.
     */
    private static MerchantServiceImpl sMerchantService;

    /**
     * Flag to track if merchant service callback has been completed.
     */
    private boolean mIsMerchantServiceCallbackCompleted;

    /**
     * Countdown to fail safe infinite waiting for merchant service callback.
     */
    private int mFailSafeCounter;

    /**
     * The result of the initiate payment request.
     */
    private Boolean mPaymentInitiated;

    /**
     * The mocked error for initiate payment request.
     */
    private Error mMockedInitiatePaymentError;

    /**
     * The mocked error for notify payment request.
     */
    private Error mMockedNotifyPaymentError;

    /**
     * The result of the notify merchant payment request.
     */
    private Boolean mPaymentNotified;

    /**
     * The settlement retrieval id.
     */
    private String mSettlementRetrievalId;

    /**
     * The merchant id.
     */
    private String mMerchantId;

    /**
     * The settlement status.
     */
    private SettlementStatus mSettlementStatus;

    /**
     * The mocked error for get payment status request.
     */
    private Error mMockedGetSettlementStatusError;

    /**
     * A mock for response listener used by merchant service to verify which method is called and the params passed to it.
     */
    private OnResponseListener<SettlementStatus> mServiceResponseListenerMock;

    /**
     * Allows one or more threads to wait until a set of operations being performed in other threads completes.
     */
    private final CountDownLatch mCountDownLatch = new CountDownLatch(1);

    /**
     * Set up mocks
     */
    public void setupMocks() {

        if (sMerchantService == null) {
            Log.i(TAG, "Initalising merchant service");

            assertNull(ZappMerchantServiceFactory.getMerchantService());
            assertNull(ZappMerchantServiceFactory.getNetworkServiceDelegateSupplier());
            assertNull(ZappMerchantServiceFactory.getUIDelegateSupplier());

            sMerchantNetworkServiceDelegate = mock(IMerchantNetworkServiceDelegate.class);
            sZappMerchantUIDelegate = mock(IZappMerchantUIDelegate.class);
            sMerchantService = (MerchantServiceImpl) MerchantServiceImpl.newInstance(sMerchantNetworkServiceDelegate, sZappMerchantUIDelegate);

            assertNotNull(ZappMerchantServiceFactory.getMerchantService());
            assertNotNull(ZappMerchantServiceFactory.getNetworkServiceDelegateSupplier());
            assertNotNull(ZappMerchantServiceFactory.getUIDelegateSupplier());

            sMerchantService = (MerchantServiceImpl) MerchantServiceImpl.newInstance(null, null);
            assertNull(sMerchantService.getNetworkServiceDelegate());
            assertNull(sMerchantService.getUIDelegate());

            sMerchantService = (MerchantServiceImpl) MerchantServiceImpl.newInstance(sMerchantNetworkServiceDelegate, sZappMerchantUIDelegate);
            assertEquals(sMerchantNetworkServiceDelegate, sMerchantService.getNetworkServiceDelegate());
            assertEquals(sZappMerchantUIDelegate, sMerchantService.getUIDelegate());
        }

        doAnswer(new Answer() {
            @Override
            public Void answer(final InvocationOnMock invocationOnMock) throws Throwable {
                final Object[] arguments = invocationOnMock.getArguments();
                assertTrue(arguments.length == 2);

                final PaymentRequest paymentRequest = (PaymentRequest) arguments[0];
                final OnResponseListener<Transaction> responseListener = (OnResponseListener<Transaction>) arguments[1];

                if (mMockedInitiatePaymentError == null) {
                    try {
                        mTransaction = new Transaction(mZappCode, mTransactionId, paymentRequest, /* paymentAuth */ null, /* retrievalExpiryInterval */
                                0, /* confirmationExpiryInterval */ 0, RetrievalMethod.INVOCATION, "callbackUrl", /* askForPayconnect */ false, /* initiatorIsAuthoriser */
                                null, "settlementRetrievalId", false);
                        Log.e(TAG, "Mocked callback: .onSuccess");
                        responseListener.onSuccess(new ResponseWrapper<>(mTransaction));
                    } catch (ZappModelValidationException zmve) {
                        responseListener.onFailure(new Error(ErrorType.GENERIC_VALIDATION_ERROR, new String[]{zmve.getMessage()}));
                        Log.e(TAG, "Mocked callback: .onFailure, GENERIC_VALIDATION_ERROR");
                    }
                } else {
                    Log.e(TAG, "Mocked callback: .onFailure with mocked error: type: " + mMockedInitiatePaymentError.getType());
                    responseListener.onFailure(mMockedInitiatePaymentError);
                }
                return null;
            }
        }).when(sMerchantNetworkServiceDelegate).initiatePayment(any(PaymentRequest.class), any(OnResponseListener.class));

        doAnswer(new Answer() {
            @Override
            public Void answer(final InvocationOnMock invocationOnMock) throws Throwable {
                final Object[] arguments = invocationOnMock.getArguments();
                assertTrue(arguments.length == 2);

                final Transaction transaction = (Transaction) arguments[0];
                final OnResponseListener<Transaction> responseListener = (OnResponseListener<Transaction>) arguments[1];

                //TODO what if transaction == null?
                if (mMockedNotifyPaymentError == null) {
                    Log.e(TAG, "Mocked callback: .onSuccess");
                    responseListener.onSuccess(new ResponseWrapper<>(mTransaction));
                } else {
                    Log.e(TAG, "Mocked callback: .onFailure with mocked error: type: " + mMockedNotifyPaymentError.getType());
                    responseListener.onFailure(mMockedNotifyPaymentError);
                }
                return null;
            }
        }).when(sMerchantNetworkServiceDelegate).notifyMerchantPayment(any(Transaction.class), any(OnResponseListener.class));
    }

    @Given("^the Request to Pay Type is \"(.*?)\"$")
    public void the_Request_to_Pay_Type_is(String arg1) throws Throwable {
        mRTPType = RTPType.valueOf(arg1);
        paymentRequestBuilder.withRtpType(mRTPType);
    }

    @Given("^the Payment Type is \"([^\"]*)\"$")
    public void the_Payment_Type_is(final String arg1) throws Throwable {
        mPaymentType = PaymentType.valueOf(arg1);
        paymentRequestBuilder.withPaymentType(mPaymentType);
    }

    @And("^the Checkout Type is \"([^\"]*)\"$")
    public void the_Checkout_Type_is(final String arg1) throws Throwable {
        paymentRequestBuilder.withCheckoutType(CheckoutType.valueOf(arg1));
    }

    @And("^the Delivery Type is \"([^\"]*)\"$")
    public void the_Delivery_Type_is(final String arg1) throws Throwable {
        paymentRequestBuilder.withDeliveryType(DeliveryType.valueOf(arg1));
    }

    @And("^the Currency Amount is \"([^\"]*)\"$")
    public void the_Currency_Amount_is_valid(final String arg1) throws Throwable {
        paymentRequestBuilder.withAmount(CurrencyAmountUtils.fromTestString(arg1));
    }

    @And("^the Merchant Delegate initiate payment endpoint responds with failure \"(.*?)\"$")
    public void the_merchant_delegate_initiate_payment_endpoint_responds_with_failure(final String arg1) throws Exception {
        mMockedInitiatePaymentError = new Error(ErrorType.valueOf(arg1));
        Log.e(TAG, "Initiate payment request error mocked: " + mMockedInitiatePaymentError + ", type: " + mMockedInitiatePaymentError.getType());
    }

    @And("^the Merchant Delegate initiate payment endpoint responds with Zapp Code \"(.*?)\" only transaction$")
    public void the_merchant_delegate_initiate_payment_endpoint_responds_with_zapp_code_only_transaction(final String arg1) throws Exception {
        mZappCode = arg1;
    }

    @And("^the Merchant Delegate initiate payment endpoint responds with Transaction Id \"(.*?)\" only transaction$")
    public void the_merchant_delegate_initiate_payment_endpoint_responds_with_transaction_id_only_transaction(final String arg1) throws Exception {
        mTransactionId = new TransactionId(arg1, arg1);
    }

    @And("^the Merchant Delegate initiate payment endpoint responds with Zapp Code \"(.*?)\" and Transaction Id \"(.*?)\" transaction$")
    public void the_merchant_delegate_responds_with_zapp_code_and_transaction_id_transaction(final String arg1, final String arg2) throws Exception {
        the_merchant_delegate_initiate_payment_endpoint_responds_with_zapp_code_only_transaction(arg1);
        the_merchant_delegate_initiate_payment_endpoint_responds_with_transaction_id_only_transaction(arg2);
    }

    @Then("^the settlement status is retrieved successfully$")
    public void the_settlement_status_is_retrieved_successfully() throws Throwable {
        final ArgumentCaptor<ResponseWrapper> captor = ArgumentCaptor.forClass(ResponseWrapper.class);
        verify(mServiceResponseListenerMock).onSuccess(captor.capture());
        final ResponseWrapper<SettlementStatus> status = captor.getValue();
        assertEquals(mSettlementStatus, status.getResponse());
    }

    @When("^I try to get settlement status$")
    public void i_try_to_get_settlement_status() throws Throwable {
        mServiceResponseListenerMock = spy(OnResponseListener.class);

        doAnswer(settlementStatusAnswer(mSettlementStatus, mMockedGetSettlementStatusError)).when(sMerchantNetworkServiceDelegate)
                .getSettlementStatus(anyString(), anyString(), any(OnResponseListener.class));

        sMerchantService.getSettlementStatus(mMerchantId, mSettlementRetrievalId, mServiceResponseListenerMock);
        mCountDownLatch.await(TIMEOUT_10_SECONDS, TimeUnit.SECONDS);
    }

    /**
     * Returns a successful or unsuccessful answer for get settlement status callback
     *
     * @param status      settlement status.
     * @param statusError error.
     * @return error if not null, status otherwise.
     */
    private Answer settlementStatusAnswer(final SettlementStatus status, final Error statusError) {
        return new Answer() {
            public Object answer(final InvocationOnMock invocation) {
                final Object[] args = invocation.getArguments();
                final OnResponseListener<SettlementStatus> listener = (OnResponseListener<SettlementStatus>) args[2];
                if (mMockedGetSettlementStatusError == null) {
                    listener.onSuccess(new ResponseWrapper<>(status));
                } else {
                    listener.onFailure(statusError);
                }
                mCountDownLatch.countDown();
                return null;
            }
        };
    }

    @Given("^the Merchant Delegate get settlement status endpoint returns settlement status \"(.*?)\"$")
    public void the_Merchant_Delegate_get_settlement_status_endpoint_returns_settlement_status(String arg1) throws Throwable {
        mSettlementStatus = SettlementStatus.valueOf(arg1);
    }

    @And("^the Merchant Delegate get settlement status endpoint responds with failure \"(.*?)\"$")
    public void the_Merchant_Delegate_get_settlement_status_endpoint_responds_with_failure(String arg1) throws Throwable {
        mMockedGetSettlementStatusError = new Error(ErrorType.valueOf(arg1));
    }

    @Given("^the settlement retrieval id is \"(.*?)\"$")
    public void the_settlement_retrieval_id_is(String arg1) throws Throwable {
        mSettlementRetrievalId = arg1;
    }

    @Given("^the merchant id is \"(.*?)\"$")
    public void the_merchant_id_is(String arg1) throws Throwable {
        mMerchantId = arg1;
    }

    @Then("^the settlement status fails with error \"(.*?)\"$")
    public void the_settlement_status_fails_with_error(String arg1) throws Throwable {
        final ArgumentCaptor<Error> captor = ArgumentCaptor.forClass(Error.class);
        verify(mServiceResponseListenerMock, await()).onFailure(captor.capture());
        final Error error = captor.getValue();
        assertEquals(ErrorType.valueOf(arg1), error.getType());
    }

    @When("^I try to initiate the payment with these details$")
    public void i_try_to_initiate_the_payment_with_these_details() throws Exception {

        //this is a workaround for a possible Mockito / Dexmaker bug: https://code.google.com/p/dexmaker/issues/detail?id=2
        System.setProperty("dexmaker.dexcache", getInstrumentation().getTargetContext().getCacheDir().getPath());
        setupMocks();

        final Merchant merchant = new Merchant("identifier", "name", /* email */ null, /* phone */ null, /* address */ null, /* website */ null, /*logoUrl*/ null);
        paymentRequestBuilder.withMerchant(merchant);
        final Address address = new Address("line1", /* line2 */ null, /* line3 */ null, /* line4 */ null, /* line5 */ null, /* line6 */ null, /* postCode */ null,
                "countryCode");
        paymentRequestBuilder.withAddress(address);
        if (mPaymentType == PaymentType.BILL_PAY) {
            final BillDetails billDetails = new BillDetails("accountId", "reference", new Date(System.currentTimeMillis() - 5 * 24 * 60 * 60 * 1000), new Date());
            paymentRequestBuilder.withBillDetails(billDetails);
        }
        final User user = new User("firstName", /* lastName */ null, /* middleName */ null, /* title */ null, "email@email.com", /* phone */ null);
        paymentRequestBuilder.withUser(user);
        final PaymentRequest paymentRequest = paymentRequestBuilder.build();

        mIsMerchantServiceCallbackCompleted = false;
        mFailSafeCounter = 100;

        final Context applicationContext = getInstrumentation().getContext().getApplicationContext();

        sMerchantService.initiatePayment(applicationContext, paymentRequest, new ProcessResponseListener<Transaction>() {
            @Override
            public void onFailure(final com.zapp.library.merchant.exception.Error error) {
                final Context context = getInstrumentation().getContext();
                Log.e(TAG, "onFailure: title: " + error.getErrorTitle(context) + ", message: " + error.getErrorMessage(context));
                mIsMerchantServiceCallbackCompleted = true;
                mPaymentInitiated = false;
            }

            @Override
            public void onSuccess(final ResponseWrapper<Transaction> response) {
                Log.e(TAG, "onSuccess");
                mIsMerchantServiceCallbackCompleted = true;
                mPaymentInitiated = true;
            }
        });

        try {
            Log.i(TAG, "Waiting for Merchant Service worker to wake me up...");
            while (!mIsMerchantServiceCallbackCompleted) {
                Thread.sleep(100);
                mFailSafeCounter--;
                if (mFailSafeCounter == 0) {
                    fail("Merchant Service callback timeout");
                    break;
                }
            }
            Log.i(TAG, "Merchant service worker woke me up");
        } catch (InterruptedException ie) {
            Log.e(TAG, "", ie);
        }
    }

    @Then("^the payment is initiated successfully$")
    public void the_payment_is_initiated_successfully() throws Exception {
        assertNotNull(mPaymentInitiated);
        assertTrue("Failed to initiate payment", mPaymentInitiated);
    }

    @Then("^the payment initiation is failed$")
    public void the_payment_initiation_is_failed() throws Exception {
        assertNotNull(mPaymentInitiated);
        assertTrue("Payment initiation should have failed", !mPaymentInitiated);
    }

    //
    // notify payment
    //

    @And("^the Merchant Delegate notify payment endpoint responds with failure \"(.*?)\"$")
    public void the_merchant_delegate_notify_payment_endpoint_responds_with_failure(final String arg1) throws Exception {
        mMockedNotifyPaymentError = new Error(ErrorType.valueOf(arg1));
        Log.e(TAG, "Notify payment request error mocked: " + mMockedNotifyPaymentError + ", type: " + mMockedNotifyPaymentError.getType());
    }

    @When("^I try to notify payment$")
    public void i_try_to_notify_payment() throws Exception {

        mIsMerchantServiceCallbackCompleted = false;
        mFailSafeCounter = 100;

        sMerchantService.notifyMerchantPayment(mTransaction, new ProcessResponseListener<Transaction>() {
            @Override
            public void onFailure(final com.zapp.library.merchant.exception.Error error) {
                final Context context = getInstrumentation().getContext();
                Log.e(TAG, "onFailure: title: " + error.getErrorTitle(context) + ", message: " + error.getErrorMessage(context));
                mIsMerchantServiceCallbackCompleted = true;
                mPaymentNotified = false;
            }

            @Override
            public void onSuccess(final ResponseWrapper<Transaction> response) {
                Log.e(TAG, "onSuccess");
                mIsMerchantServiceCallbackCompleted = true;
                mPaymentNotified = true;
            }
        });

        try {
            Log.i(TAG, "Waiting for Merchant Service worker to wake me up...");
            while (!mIsMerchantServiceCallbackCompleted) {
                Thread.sleep(100);
                mFailSafeCounter--;
                if (mFailSafeCounter == 0) {
                    fail("Merchant Service callback timeout");
                    break;
                }
            }
            Log.i(TAG, "Merchant service worker woke me up");
        } catch (InterruptedException ie) {
            Log.e(TAG, "", ie);
        }
    }

    @Then("^the payment notification is successful$")
    public void the_payment_notification_is_successful() throws Exception {
        assertNotNull(mPaymentNotified);
        assertTrue("Failed to notify merchant payment", mPaymentNotified);
    }

    @Then("^the payment notification is failed$")
    public void the_payment_notification_is_failed() throws Exception {
        assertNotNull(mPaymentNotified);
        assertTrue("Payment notification should have failed", !mPaymentNotified);
    }

    /**
     * Creates mock settings which awakes all threads when the mock has been invoked.
     *
     * @return A custom mock settings which notifies all threads that the mock has been invoked.
     */
    private static MockSettings async() {
        return withSettings().defaultAnswer(Mockito.RETURNS_DEFAULTS).invocationListeners(
                new InvocationListener() {

                    @Override
                    public void reportInvocation(final MethodInvocationReport methodInvocationReport) {
                        final DescribedInvocation invocation = methodInvocationReport.getInvocation();
                        if (invocation instanceof InvocationOnMock) {
                            final Object mock = ((InvocationOnMock) invocation).getMock();
                            synchronized (mock) {
                                mock.notifyAll();
                            }
                        }
                    }
                });
    }

    /**
     * Create a new await verification mode.
     *
     * @return A new await verification mode.
     */
    private static VerificationMode await() {
        return new Await();
    }
}
