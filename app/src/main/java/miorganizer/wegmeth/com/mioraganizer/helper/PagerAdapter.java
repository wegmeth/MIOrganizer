package miorganizer.wegmeth.com.mioraganizer.helper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import miorganizer.wegmeth.com.mioraganizer.fragments.GroupFragment;
import miorganizer.wegmeth.com.mioraganizer.fragments.SemesterFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int nNumOfTabs;

    public PagerAdapter(FragmentManager fm, int numOfTabs){

        super(fm);
        this.nNumOfTabs = numOfTabs;

    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                SemesterFragment semester = new SemesterFragment();
                return  semester;
            case 1:
                GroupFragment group = new GroupFragment();
                return group;
        }

        return null;
    }


    @Override
    public int getCount() {
        return nNumOfTabs;
    }
}
