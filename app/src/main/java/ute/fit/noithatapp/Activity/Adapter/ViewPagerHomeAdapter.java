package ute.fit.noithatapp.Activity.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import ute.fit.noithatapp.Activity.Fragment.HomeFragment;
import ute.fit.noithatapp.Activity.Fragment.UserFragment;
<<<<<<< HEAD
=======
import ute.fit.noithatapp.Activity.Fragment.UserProfileFragment;
>>>>>>> origin/main

public class ViewPagerHomeAdapter extends FragmentStatePagerAdapter {


    public ViewPagerHomeAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
<<<<<<< HEAD
            case 0:
                return new HomeFragment();
            case 1:
                return new UserFragment();
=======
            case 1:
                return new UserFragment();
            case 2:
                return new UserProfileFragment();
>>>>>>> origin/main
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getCount() {
<<<<<<< HEAD
        return 2;
=======
        return 3;
>>>>>>> origin/main
    }


}
