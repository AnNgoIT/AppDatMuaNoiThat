package ute.fit.noithatapp.Activity.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import ute.fit.noithatapp.Activity.Fragment.HomeFragment;
import ute.fit.noithatapp.Activity.Fragment.UserFragment;
import ute.fit.noithatapp.Activity.Fragment.UserProfileFragment;

public class ViewPagerHomeAdapter extends FragmentStatePagerAdapter {


    public ViewPagerHomeAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1:
                return new UserFragment();
            case 2:
                return new UserProfileFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }


}
