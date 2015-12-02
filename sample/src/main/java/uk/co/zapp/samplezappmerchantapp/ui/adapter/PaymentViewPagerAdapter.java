package uk.co.zapp.samplezappmerchantapp.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 *  Implementation of {@link android.support.v4.view.PagerAdapter}
 *  that is used to display tabs content in {@link uk.co.zapp.samplezappmerchantapp.MainActivity}
 *
 * @author nsevciuc
 */
public class PaymentViewPagerAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();

    private final List<String> mFragmentTitleList = new ArrayList<>();

    public PaymentViewPagerAdapter(final FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(final int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        return mFragmentTitleList.get(position);
    }

    public void addItem(final Fragment fragment, final String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }
}
