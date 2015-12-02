package uk.co.zapp.samplezappmerchantapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

/**
 * The base abstract activity.
 */
public class AbstractActivity extends AppCompatActivity {

    /**
     * Sets the action bar.
     */
    protected void setActionBar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Standard logic to switch fragments.
     *
     * @param fragment The fragment class to be displayed.
     */
    public void switchFragment(final Fragment fragment) {
        switchFragment(fragment, true);
    }

    /**
     * Standard logic to switch fragments.
     *
     * @param fragment           The fragment class to be displayed.
     * @param isAddedToBackStack Add this transaction to the back stack, true if the transaction should be remembered otherwise false.
     */
    protected void switchFragment(final Fragment fragment, final boolean isAddedToBackStack) {
        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment);
        if (isAddedToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getClass().getName());
        }
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        final int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
