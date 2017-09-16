package com.svkrdj.arduinobluetooth;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by skdj on 10/9/17.
 */

public class CategoryAdapter extends FragmentPagerAdapter {
    /**
     * Create a new {@link CategoryAdapter} object.
     *
     * @param fm is the fragment manager that will keep each fragment's state in the adapter
     *           across swipes.
     */
    private Context mContext;
    public CategoryAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext=context;
    }

    /**
     * Return the {@link Fragment} that should be displayed for the given page number.
     */
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new ThroughSwitch();
        } else {
            return new ThroughSensor();
        }
    }


    /**
     * Return the total number of pages.
     */
    @Override
    public int getCount() {
        return 2;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.Switch);
        } else {
            return mContext.getString(R.string.Sensor);
        }
    }
}
