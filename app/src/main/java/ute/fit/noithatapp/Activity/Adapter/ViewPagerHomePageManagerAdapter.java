package ute.fit.noithatapp.Activity.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import ute.fit.noithatapp.Activity.Fragment.HomePageManagerFragment;
import ute.fit.noithatapp.Activity.Fragment.OrderManageFragment;
import ute.fit.noithatapp.Activity.Fragment.ProductManageFragment;
import ute.fit.noithatapp.Activity.Fragment.RevenueStatisticsFragment;

public class ViewPagerHomePageManagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerHomePageManagerAdapter(@NonNull FragmentManager fm, int behavior){
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return new ProductManageFragment();
            case 2:
                return new OrderManageFragment();
            case 3:
                return new RevenueStatisticsFragment();
            default:
                return new HomePageManagerFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
