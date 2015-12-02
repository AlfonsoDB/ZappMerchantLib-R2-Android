package com.zapp.library.merchant.ui.fragment;

import com.zapp.core.Transaction;
import com.zapp.library.merchant.R;
import com.zapp.library.merchant.util.AppUtils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Popup fragment for electronic commerce (ecomm) journey with bank app installed.
 *
 * @author gdragan
 * @author msagi
 */
public class PBBAPopupECommFragment extends PBBAAnimatedPopupFragment {

    /**
     * Create new instance.
     *
     * @param transaction The transaction details. It is set only if the transaction is created successfully.
     * @return The new fragment instance.
     */
    public static PBBAPopupECommFragment newInstance(final Transaction transaction) {
        final PBBAPopupECommFragment fragment = new PBBAPopupECommFragment();
        final Bundle arguments = new Bundle();
        arguments.putSerializable(PBBAPopupFragment.KEY_TRANSACTION, transaction);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View content = inflater.inflate(R.layout.pbba_popup_fragment_ecomm, container, false);

        final boolean isBankAppAvailable = AppUtils.isAnyZappBankAppAvailable(getActivity());
        if (isBankAppAvailable) {
            final View noBankAppMessagesContainer = content.findViewById(R.id.pbba_ecomm_popup_body_no_bank_app_messages);
            if (noBankAppMessagesContainer != null) {
                //we have this container on tablet layouts
                noBankAppMessagesContainer.setVisibility(View.GONE);
            }
        }

        final char[] code = mTransaction.getBrn().toCharArray();
        ((TextView) content.findViewById(R.id.code_value_1)).setText(String.valueOf(code[0]));
        ((TextView) content.findViewById(R.id.code_value_2)).setText(String.valueOf(code[1]));
        ((TextView) content.findViewById(R.id.code_value_3)).setText(String.valueOf(code[2]));
        ((TextView) content.findViewById(R.id.code_value_4)).setText(String.valueOf(code[3]));
        ((TextView) content.findViewById(R.id.code_value_5)).setText(String.valueOf(code[4]));
        ((TextView) content.findViewById(R.id.code_value_6)).setText(String.valueOf(code[5]));

        return content;
    }
}
