package miorganizer.wegmeth.com.mioraganizer.helper;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

public class MainOnTabSelectedListener implements TabLayout.OnTabSelectedListener {

    ViewPager viewPager;

   public  MainOnTabSelectedListener(ViewPager pager){

       viewPager = pager;

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }

}
