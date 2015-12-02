package com.zapp.library.merchant.ui.fragment;

import com.zapp.library.merchant.R;
import com.zapp.library.merchant.exception.Error;
import com.zapp.library.merchant.exception.ErrorType;
import com.zapp.library.merchant.view.PBBAButton;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Pay by Bank app popup implementation for error popups.
 *
 * @author msagi
 */
public class PBBAPopupErrorFragment extends PBBAAnimatedPopupFragment {

    /**
     * Create new error popup fragment instance.
     *
     * @param journeyType The journey type for this transaction.
     * @param error       The error. It is set only if failed to create transaction.
     * @return The new fragment instance.
     */
    public static PBBAPopupErrorFragment newInstance(@NonNull final PBBAPopupFragment.JourneyType journeyType, @NonNull final Error error) {
        final PBBAPopupErrorFragment fragment = new PBBAPopupErrorFragment();
        final Bundle arguments = new Bundle();
        arguments.putSerializable(PBBAPopupFragment.KEY_JOURNEY_TYPE, journeyType);
        arguments.putSerializable(PBBAPopupFragment.KEY_ERROR, error);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View content = inflater.inflate(R.layout.pbba_popup_fragment_error, container, false);

        final TextView titleTextView = (TextView) content.findViewById(R.id.pbba_popup_error_title_text);
        final TextView messageTextView = (TextView) content.findViewById(R.id.pbba_popup_error_message_text);

        final Bundle arguments = getArguments();
        final JourneyType journeyType = (JourneyType) arguments.getSerializable(KEY_JOURNEY_TYPE);
        final ErrorType errorType = ((com.zapp.library.merchant.exception.Error) arguments.getSerializable(KEY_ERROR)).getType();

        //set error title
        switch (errorType) {
            case PAYMENT_BRN_EXPIRED:
                titleTextView.setText(getString(R.string.pbba_popup_error_expired_title));
                break;
            default:
                titleTextView.setText(getString(R.string.pbba_popup_error_generic_title));
                break;
        }

        switch (journeyType) {

            case ECOMM:
                switch (errorType) {
                    case PAYMENT_BRN_EXPIRED:
                        messageTextView.setText(getString(R.string.pbba_popup_error_ecomm_expired_message));
                        break;
                    default:
                        messageTextView.setText(getString(R.string.pbba_popup_error_ecomm_generic_message));
                        break;
                }
                break;
            case FIRST_TIME:
                switch (errorType) {
                    case PAYMENT_BRN_EXPIRED:
                        messageTextView.setText(getString(R.string.pbba_popup_error_first_time_expired_message));
                        break;
                    default:
                        messageTextView.setText(getString(R.string.pbba_popup_error_first_time_generic_message));
                        break;
                }
                break;
            case MCOMM:
                switch (errorType) {
                    case PAYMENT_BRN_EXPIRED:
                        messageTextView.setText(getString(R.string.pbba_popup_error_mcomm_expired_message));
                        break;
                    default:
                        messageTextView.setText(getString(R.string.pbba_popup_error_mcomm_generic_message));
                        break;
                }
                break;
        }

        final PBBAButton pbbaButton = (PBBAButton) content.findViewById(R.id.pbba_button);
        pbbaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mCallback.onPayByBankApp();
            }
        });

        return content;
    }
}
