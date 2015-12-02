package com.zapp.library.merchant.util;

import com.zapp.core.Transaction;
import com.zapp.core.TransactionId;
import com.zapp.library.merchant.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Class which provides utility methods accessed from different places in the app.
 */
public final class AppUtils {

    /**
     * Hidden constructor for utility class.
     */
    private AppUtils() {
    }

    /**
     * Checks if the device is tablet.
     *
     * @param context application context
     * @return true if tablet
     */
    public static boolean isCurrentDeviceTablet(final Context context) {
        return context.getResources().getBoolean(R.bool.is_tablet);
    }

    /**
     * Checks if there is any of the bank apps installed on the device.
     *
     * @param context application context
     * @return true if available
     */
    public static boolean isAnyZappBankAppAvailable(final Context context) {
        final Intent zappIntent = new Intent();
        zappIntent.setData(new Uri.Builder().scheme(Const.ZAPP_SCHEME).build());
        zappIntent.setAction(Intent.ACTION_VIEW);
        final ResolveInfo resolveInfo = context.getPackageManager().resolveActivity(zappIntent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo != null;
    }

    /**
     * Open banking application for given activivty and transaction.
     *
     * @param activity    The activity which starts the banking application.
     * @param transaction The transaction for which the banking application is to be started.
     */
    public static void openBankingApp(@NonNull Activity activity, @NonNull final Transaction transaction) {
        final Intent bankingAppStartIntent = createBankingAppIntent(transaction);
        if (bankingAppStartIntent != null && !activity.isFinishing()) {
            activity.startActivity(bankingAppStartIntent);
        }
    }

    /**
     * Creates an intent can be intercepted by the bank app.
     *
     * @param transaction The transaction to build the intent from.
     * @return The {@link Intent} to start the bank app or null if given transaction is null.
     */
    public static Intent createBankingAppIntent(@Nullable final Transaction transaction) {
        if (transaction == null) {
            return null;
        }
        final TransactionId transactionId = transaction.getTransactionId();
        return createBankingAppIntent(transactionId.getAptId(), transactionId.getAptrId());
    }

    /**
     * Creates an intent that can be intercepted by the any of the bank app. For this purpose it contains a custom scheme.
     *
     * @param aptId  used to build the intent
     * @param aptrId used to build the intent
     * @return intent that opens a bank app
     */
    public static Intent createBankingAppIntent(final String aptId, final String aptrId) {
        Intent intent = null;
        final Uri appUri = createBankingAppUri(aptId, aptrId);
        if (appUri != null) {
            intent = new Intent(Intent.ACTION_VIEW, appUri);
        }
        return intent;
    }

    /**
     * Creates a custom Uri which opens a bank app.
     *
     * @param transactionID used to build the uri
     * @param aptrID        used to build the uri
     * @return the uri
     */
    public static Uri createBankingAppUri(final String transactionID, final String aptrID) {
        Uri startUri = null;
        if (transactionID != null && aptrID != null) {
            startUri = Uri.parse(Const.ZAPP_SCHEME + "://" + transactionID + '/' + aptrID);
        }
        return startUri;
    }
}
