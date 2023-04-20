package ute.fit.noithatapp.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import ute.fit.noithatapp.Activity.Adapter.ViewPagerHomeAdapter;
import ute.fit.noithatapp.R;

public class HomeActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewpager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //transaction
        overridePendingTransition(R.anim.slide_in_form_right, R.anim.slide_out_to_left);
        //
        setContentView(R.layout.activity_home);
        Init();

    }

    @SuppressLint("WrongViewCast")
    private void Init() {
        mTabLayout=findViewById(R.id.tab_layout_home);
        mViewpager=findViewById(R.id.view_pager_home);
        ViewPagerHomeAdapter viewPagerHomeAdapter=new ViewPagerHomeAdapter(getSupportFragmentManager(),FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewpager.setAdapter(viewPagerHomeAdapter);
        mTabLayout.setupWithViewPager(mViewpager);
        mTabLayout.getTabAt(0).setIcon(R.drawable.baseline_home_24);
        mTabLayout.getTabAt(1).setIcon(R.drawable.baseline_settings_24);
        //

    }

}
