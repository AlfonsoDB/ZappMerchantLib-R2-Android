package com.zapp.library.merchant.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Class that manages default shared preferences
 * <br>
 * Created by gdragan on 8/11/2014.
 */
public final class SharedPrefs {

    /**
     * Constant for banking app button clicked shared preferences key.
     */
    public static final String BANKING_APP_BTN_CLICKED = "open_banking_app_btn_clicked";

    /**
     * The context.
     */
    private final Context mContext;

    /**
     * The shared preferences.
     */
    private final SharedPreferences mSharedPreferences;

    public SharedPrefs(final Context context) {
        mContext = context;
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Checks whether the user has opened the bank app by clicking the button from the popup dialog.
     * If so, second time the bank app should be opened automatically.
     *
     * @return true if it was clicked, false otherwise.
     */
    public boolean isOpenBankingAppButtonClicked() {
        return mSharedPreferences.getBoolean(BANKING_APP_BTN_CLICKED, false);
    }

    /**
     * Set the open banking app button clicked flag in shared preferences.
     * @param value The new flag value.
     */
    public void setOpenBankingAppButtonClicked(final boolean value) {
        mSharedPreferences.edit().putBoolean(BANKING_APP_BTN_CLICKED, value).apply();
    }
}
