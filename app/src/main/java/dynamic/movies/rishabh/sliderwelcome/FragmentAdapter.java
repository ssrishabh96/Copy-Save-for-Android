package dynamic.movies.rishabh.sliderwelcome;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by RISHABH on 5/14/2016.
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    public FragmentAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=new FragmentA();
        return fragment;
    }

    @Override
    public int getCount() {
return 1;    }
}
