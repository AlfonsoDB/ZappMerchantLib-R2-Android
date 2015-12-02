package uk.co.zapp.samplezappmerchantapp.network.exception;

public class PaymentNotFinishedException extends Exception {

    public PaymentNotFinishedException() {
        super("Payment not confirmed yet.");
    }
}
