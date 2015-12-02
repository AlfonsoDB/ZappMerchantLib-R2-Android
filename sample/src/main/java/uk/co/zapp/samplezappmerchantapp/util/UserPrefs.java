package uk.co.zapp.samplezappmerchantapp.util;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import com.zapp.core.Transaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Utility class for storing user preferences.
 * Created by Vasile Chelban on 2/17/14.
 */
public final class UserPrefs {

    /**
     * Constant for shared preferences file name.
     */
    private static final String PREFS_NAME = "merchant_prefs";

    /**
     * Constant key for 'last payment request' value.
     */
    private static final String KEY_LAST_PAYMENT_REQUEST = "last_payment_request";

    /**
     * Hidden constructor for utility class.
     */
    private UserPrefs() {
    }

    /**
     * Write payment object to the last payment value.
     *
     * @param transaction The payment object
     * @param context     The context to use.
     */
    public static void writeTransactionObject(final Transaction transaction, final Context context) {
        if (transaction != null) {
            final SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
            final String paymentAsString = new Gson().toJson(transaction);
            editor.putString(KEY_LAST_PAYMENT_REQUEST, paymentAsString);
            editor.apply();
        }
    }

    /**
     * Read Transaction object from last transaction value.
     *
     * @param context The application context.
     * @return The last transaction object saved or null if there is not any.
     */
    public static Transaction readTransactionObject(final Context context) {
        final SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        final String transactionAsString = prefs.getString(KEY_LAST_PAYMENT_REQUEST, "");
        try {
            return new Gson().fromJson(transactionAsString, Transaction.class);
        } catch (JsonSyntaxException e) {
            Log.w(Const.DEBUG_TAG, e.getMessage(), e);
        }
        return null;
    }
}
