package uk.co.zapp.samplezappmerchantapp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import uk.co.zapp.samplezappmerchantapp.IListItemClickListener;
import uk.co.zapp.samplezappmerchantapp.R;
import uk.co.zapp.samplezappmerchantapp.configuration.Feature;

/**
 * Adapter for feature demo list.
 *
 * @author msagi
 */
public class FeatureDemoAdapter extends RecyclerView.Adapter<FeatureDemoAdapter.ViewHolder> {

    /**
     * The list items.
     */
    private final List<Feature> mItems;

    /**
     * The click listener.
     */
    private final IListItemClickListener mOnClickListener;

    private final int mLayout;

    /**
     * Create new instance.
     *
     * @param items The list items for this adapter.
     */
    public FeatureDemoAdapter(final int layout, @NonNull final List<Feature> items, @NonNull final IListItemClickListener clickListener) {
        mLayout = layout;
        mItems = items;
        mOnClickListener = clickListener;
    }

    @Override
    public FeatureDemoAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, final int position) {
        // create a new view
        final View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(mLayout, parent, false);
        final ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final FeatureDemoAdapter.ViewHolder viewHolder, final int position) {
        final Feature feature = mItems.get(position);
        viewHolder.titleTextView.setText(feature.getTitle());
        viewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mOnClickListener.onListItemClicked(position, feature.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    /**
     * Inner view holder class.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private View container;

        private TextView titleTextView;

        public ViewHolder(final View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.card_container_layout);
            titleTextView = (TextView) itemView.findViewById(R.id.feature_demo_list_item_title);
        }

        public View getContainerView() {
            return container;
        }

        public View getTitleView() {
            return titleTextView;
        }
    }
}
