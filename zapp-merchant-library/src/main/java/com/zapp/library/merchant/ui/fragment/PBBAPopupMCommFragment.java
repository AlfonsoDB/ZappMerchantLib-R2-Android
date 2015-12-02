package com.zapp.library.merchant.ui.fragment;

import com.zapp.core.Transaction;
import com.zapp.core.TransactionId;
import com.zapp.library.merchant.R;
import com.zapp.library.merchant.util.AppUtils;
import com.zapp.library.merchant.util.SharedPrefs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Popup fragment for mobile commerce (mcomm) journey with bank app installed.
 *
 * @author gdragan
 * @author msagi
 */
public class PBBAPopupMCommFragment extends PBBAAnimatedPopupFragment {

    /**
     * Create new instance.
     *
     * @param transaction The transaction details. It is set only if the transaction is created successfully.
     * @return The new fragment instance.
     */
    public static PBBAPopupMCommFragment newInstance(@NonNull final Transaction transaction) {
        final PBBAPopupMCommFragment fragment = new PBBAPopupMCommFragment();
        final Bundle arguments = new Bundle();
        arguments.putSerializable(PBBAPopupFragment.KEY_TRANSACTION, transaction);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!AppUtils.isAnyZappBankAppAvailable(getActivity())) {
            mCallback.onNoBankAppAvailable();
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View content = inflater.inflate(R.layout.pbba_popup_fragment_mcomm, container, false);

        final View openBankingAppButton = content.findViewById(R.id.pbba_button_open_banking_app);
        openBankingAppButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Activity activity = getActivity();
                if (activity != null) {
                    new SharedPrefs(activity.getApplicationContext()).setOpenBankingAppButtonClicked(true);
                    if (AppUtils.isAnyZappBankAppAvailable(activity)) {
                        AppUtils.openBankingApp(activity, mTransaction);
                    } else {
                        mCallback.onNoBankAppAvailable();
                    }
                }
            }
        });

        final View getCodeButton = content.findViewById(R.id.pbba_button_get_code);
        if (getCodeButton != null) {
            //'Get Code' button is only on phone layout
            getCodeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    mCallback.onDisplayCode();
                }
            });
        }

        final View codeContainer = content.findViewById(R.id.pbba_popup_code_container);
        if (codeContainer != null) {
            //code container
            final char[] code = mTransaction.getBrn().toCharArray();
            ((TextView) content.findViewById(R.id.code_value_1)).setText(String.valueOf(code[0]));
            ((TextView) content.findViewById(R.id.code_value_2)).setText(String.valueOf(code[1]));
            ((TextView) content.findViewById(R.id.code_value_3)).setText(String.valueOf(code[2]));
            ((TextView) content.findViewById(R.id.code_value_4)).setText(String.valueOf(code[3]));
            ((TextView) content.findViewById(R.id.code_value_5)).setText(String.valueOf(code[4]));
            ((TextView) content.findViewById(R.id.code_value_6)).setText(String.valueOf(code[5]));
        }
        //note: 'What is Pay by Bank app' link is set up in the layout .xml

        return content;
    }
}
