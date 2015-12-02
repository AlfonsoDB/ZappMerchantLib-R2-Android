package uk.co.zapp.samplezappmerchantapp;

/**
 * List item click listener interface.
 *
 * @author msagi
 */
public interface IListItemClickListener {

    /**
     * Event callback for 'list item clicked' event.
     *
     * @param featureId The id of the clicked feature list item.
     * @param position  The position of the clicked list item.
     */
    void onListItemClicked(int position, int featureId);
}
