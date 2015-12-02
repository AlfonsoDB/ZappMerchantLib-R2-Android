package uk.co.zapp.samplezappmerchantapp.util;

import android.annotation.TargetApi;
import android.os.Build;

import java.io.Closeable;
import java.io.IOException;

/**
 * Utility class to auto close open resources.
 */
public class AutoCloseUtils {

    /**
     * Hidden constructor for utility class.
     */
    private AutoCloseUtils() {}

    /**
     * Close the given resource automatically and ignore any errors occured.
     *
     * @param closeable The resource to auto close. Must implement one of these interfaces: Closeable, AutoCloseable.
     */
    @TargetApi(19)
    public static void autoClose(final Object closeable) {
        if (closeable != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if (closeable instanceof AutoCloseable) {
                    try {
                        ((AutoCloseable) closeable).close();
                        return;
                    } catch (Exception e) {
                        //we ignore any exception
                    }
                }
            }
            if (closeable instanceof Closeable) {
                try {
                    ((Closeable) closeable).close();
                    return;
                } catch (IOException e) {
                    //we ignore any exception
                }
            }
            throw new IllegalArgumentException("unsupported type: " + closeable.getClass());
        }
    }
}
