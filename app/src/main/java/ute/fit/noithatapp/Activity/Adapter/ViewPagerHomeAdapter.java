package ute.fit.noithatapp.Activity.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import ute.fit.noithatapp.Activity.Fragment.HomeFragment;
import ute.fit.noithatapp.Activity.Fragment.UserFragment;

public class ViewPagerHomeAdapter extends FragmentStatePagerAdapter {


    public ViewPagerHomeAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new UserFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }


}
