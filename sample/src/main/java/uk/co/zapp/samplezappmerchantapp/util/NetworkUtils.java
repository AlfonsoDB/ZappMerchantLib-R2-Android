package uk.co.zapp.samplezappmerchantapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Utility class for network utilities.
 * Created by Zeeshan Muhammad on 16/01/14.
 */
public final class NetworkUtils {

    /**
     * Hidden constructor for utility class.
     */
    private NetworkUtils() {
    }

    /**
     * Checks to see if there is an active network connection.
     *
     * @return <code>true</code> if the user has a network connection, <code>false</code> if not.
     */
    public static boolean isNetworkConnectionAvailable(final Context context) {
        final ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        final NetworkInfo[] networkInfos = connectivity.getAllNetworkInfo();
        if (networkInfos != null) {
            for (final NetworkInfo networkInfo : networkInfos) {
                if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }
}
