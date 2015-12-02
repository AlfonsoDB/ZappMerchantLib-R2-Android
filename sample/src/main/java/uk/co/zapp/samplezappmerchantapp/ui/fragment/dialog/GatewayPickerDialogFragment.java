package uk.co.zapp.samplezappmerchantapp.ui.fragment.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.ArrayAdapter;

import java.util.List;

import uk.co.zapp.samplezappmerchantapp.R;
import uk.co.zapp.samplezappmerchantapp.configuration.AppConfigurationUtils;
import uk.co.zapp.samplezappmerchantapp.configuration.Gateway;

/**
 * Custom dialog fragment implementation to display gateway chooser dialog. This fragment does not support recreation intentionally.
 *
 * @author msagi
 */
public class GatewayPickerDialogFragment extends DialogFragment {

    public interface IGatewayPickerDialogCallback {
        void onGatewayPicked(@NonNull String gatewayInfo);
    }

    /**
     * Tag to tag the fragment in the fragment manager.
     */
    public static final String TAG = GatewayPickerDialogFragment.class.getSimpleName();

    /**
     * The callback interface for the picker dialog.
     */
    private IGatewayPickerDialogCallback mGatewayPickerDialogCallback;

    @Override
    public void onPause() {
        super.onPause();
        if (isVisible()) {
            dismiss();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mGatewayPickerDialogCallback = null;
    }

    /**
     * Set the callback listener.
     * @param callback The callback listener to use.
     */
    public void setCallback(@NonNull final IGatewayPickerDialogCallback callback) {
        mGatewayPickerDialogCallback = callback;
    }

    @Override
    public AlertDialog onCreateDialog(final Bundle savedInstanceState) {
        final AppConfigurationUtils configuration = AppConfigurationUtils.getInstance(getActivity());
        final List<Gateway> gateways = configuration.getAvailableGateways();
        final Gateway activeGateway = configuration.getActiveGateway();
        int selectedIndex = 0;
        for (int index = 0; index < gateways.size(); index++) {
            final Gateway gateway = gateways.get(index);
            if (activeGateway.equals(gateway)) {
                selectedIndex = index;
            }
        }
        final ArrayAdapter<Gateway> gatewayListAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_single_choice, gateways);

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.settings_choose_gateway_title)
                .setSingleChoiceItems(gatewayListAdapter, selectedIndex, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        dismiss();

                        final Gateway selectedGateway = gatewayListAdapter.getItem(which);
                        configuration.setActiveGateway(selectedGateway);

                        if (mGatewayPickerDialogCallback != null) {
                            mGatewayPickerDialogCallback.onGatewayPicked(selectedGateway.toString());
                        }
                    }
                })
                .setCancelable(false)
                .create();
    }
}
