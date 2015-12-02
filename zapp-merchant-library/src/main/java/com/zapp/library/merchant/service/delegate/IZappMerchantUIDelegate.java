package com.zapp.library.merchant.service.delegate;

import com.zapp.core.PaymentRequest;
import com.zapp.core.Transaction;

/**
 * The UI delegate that is invoked by the sdk services. usually it is used when service implementations executes some operations.
 * and requires some actions to be executed bu the user.
 *
 * @author Vasile Chelban on 6/27/2014.
 */
public interface IZappMerchantUIDelegate extends IZappMerchantServiceDelegate {

    /**
     * Shows a payment confirmation screen to the user with the status whether it has been successful or rejected.
     * Also it allows the merchant app to display the payment details to the user.
     *
     * @param transaction Transaction details to be displayed.
     * @param error       The error message in case that payment has failed.
     */
    void showPaymentConfirmationScreen(Transaction transaction, com.zapp.library.merchant.exception.Error error);

    /**
     * Shows the screen with the Pay by Bank app code to the user for Small-Micro Business (SMB) transactions.
     *
     * @param transaction The SMB payment transaction details.
     */
    void showSMBScreen(Transaction transaction);

    /**
     * When user initiates a payment by clicking the Zapp button, he is provided with a set of instructions what to do next in order to make the payment.
     * All steps with the instructions are organized in a popup dialog that is displayed on top of other screens.
     *
     * @param transaction The payment transaction details. This parameter is set only if a transaction is created successfully and error is null.
     * @param paymentRequest The payment request details. This parameter is set only if failed to create transaction and error is not null.
     * @param error       The error in case the payment did not succeed, will display a popup with the corresponding error message.
     */
    void showPopupDialog(Transaction transaction, PaymentRequest paymentRequest, com.zapp.library.merchant.exception.Error error);
}
