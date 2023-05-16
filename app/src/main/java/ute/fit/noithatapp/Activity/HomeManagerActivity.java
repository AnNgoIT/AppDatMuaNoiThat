package ute.fit.noithatapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.tabs.TabLayout;

import ute.fit.noithatapp.Activity.Adapter.ViewPagerHomeAdapter;
import ute.fit.noithatapp.Activity.Adapter.ViewPagerHomePageManagerAdapter;
import ute.fit.noithatapp.Contants.SharedPrefManager;
import ute.fit.noithatapp.R;

public class HomeManagerActivity extends AppCompatActivity {
    private TabLayout mTabLayout;

    private ViewPager mViewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_manager);
        Init();
    }

    private void Init() {
        mTabLayout = findViewById(R.id.tab_lay_mhome);
        mViewpager = findViewById(R.id.vpHome_Manager);
        ViewPagerHomePageManagerAdapter viewPagerHomeManagerAdapter = new ViewPagerHomePageManagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewpager.setAdapter(viewPagerHomeManagerAdapter);
        mTabLayout.setupWithViewPager(mViewpager);
        mTabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
        mTabLayout.getTabAt(1).setIcon(R.drawable.ic_product);
        mTabLayout.getTabAt(2).setIcon(R.drawable.ic_order);
        mTabLayout.getTabAt(3).setIcon(R.drawable.ic_revenue_statistics);
    }


}