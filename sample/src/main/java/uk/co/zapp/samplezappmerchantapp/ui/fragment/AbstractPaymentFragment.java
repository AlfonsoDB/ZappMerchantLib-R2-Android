package uk.co.zapp.samplezappmerchantapp.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;

import uk.co.zapp.samplezappmerchantapp.FeatureDemoActivity;
import uk.co.zapp.samplezappmerchantapp.IListItemClickListener;
import uk.co.zapp.samplezappmerchantapp.R;
import uk.co.zapp.samplezappmerchantapp.configuration.Features;
import uk.co.zapp.samplezappmerchantapp.ui.adapter.FeatureDemoAdapter;

/**
 * Abstract base fragment for payment feature lists.
 *
 * @author msagi
 */
public abstract class AbstractPaymentFragment extends Fragment implements IListItemClickListener {

    /**
     * The delay before showing the feature detail screen.
     */
    private static final int FEATURE_DETAIL_SCREEN_SHOW_DELAY_MS = 0;

    /**
     * A simple date formatter to format and parse dates.
     */
    protected static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Merchant identifier.
     */
    protected static final String MERCHANT_ID = "MerchantDemo2";

    /**
     * Merchant name (matching merchant identifier).
     */
    protected static final String MERCHANT_NAME = "Merchant Demo 2";

    /**
     * Handler on the UI thread
     */
    protected final Handler mMainHandler = new Handler(Looper.getMainLooper());

    /**
     * The application context.
     */
    protected Context mContext;

    /**
     * The features data model.
     */
    protected Features mFeatures;

    /**
     * Recycler view of features to demo.
     */
    protected RecyclerView mFeatureDemoRecyclerView;

    /**
     * Adapter for the feature list.
     */
    protected FeatureDemoAdapter mFeaturesAdapter;

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        mContext = context.getApplicationContext();
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFeatures = Features.getInstance(mContext);
        setupFeatureDemos();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_payment, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFeatureDemoRecyclerView = (RecyclerView) view.findViewById(R.id.feature_demo_recycler_view);

        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mFeatureDemoRecyclerView.setLayoutManager(layoutManager);
        mFeatureDemoRecyclerView.setAdapter(mFeaturesAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mFeatureDemoRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    @Override
    public void onListItemClicked(final int position, final int featureId) {
        final Intent featureDemoIntent = new Intent(getActivity(), FeatureDemoActivity.class);
        featureDemoIntent.putExtra(FeatureDemoActivity.KEY_FEATURE_ID, featureId);

        final FeatureDemoAdapter.ViewHolder viewHolder = (FeatureDemoAdapter.ViewHolder) mFeatureDemoRecyclerView.findViewHolderForLayoutPosition(position);
        final String containerName = getString(R.string.transition_container);
        final String titleName = getString(R.string.transition_title);
        final Pair<View, String> containerPair = Pair.create(viewHolder.getContainerView(), containerName);
        final Pair<View, String> titlePair = Pair.create(viewHolder.getTitleView(), titleName);
        @SuppressWarnings("unchecked")
        final ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), containerPair, titlePair);

        mMainHandler.post(new Runnable() {
            @Override
            public void run() {
                startActivity(featureDemoIntent);
            }
        });
    }

    /**
     * Abstract method to override in custom payment fragments.
     */
    protected abstract void setupFeatureDemos();
}
