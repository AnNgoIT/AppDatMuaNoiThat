package ute.fit.noithatapp.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import ute.fit.noithatapp.Activity.Adapter.ViewPagerHomeAdapter;
import ute.fit.noithatapp.Contants.SharedPrefManager;
import ute.fit.noithatapp.R;

public class HomeActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewpager;
    int MIN_DISTANCE=150;
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
        //Lấy id user
        System.out.println(SharedPrefManager.getInstance(this).getUserId());
        //sự kiện kéo xuống sẽ load lại trang

    }

    @SuppressLint("WrongViewCast")
    private void Init() {
        mTabLayout=findViewById(R.id.tab_layout_home);
        mViewpager=findViewById(R.id.view_pager_home);
        ViewPagerHomeAdapter viewPagerHomeAdapter=new ViewPagerHomeAdapter(getSupportFragmentManager(),FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewpager.setAdapter(viewPagerHomeAdapter);
        mTabLayout.setupWithViewPager(mViewpager);
        mTabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
        mTabLayout.getTabAt(1).setIcon(R.drawable.ic_bell);
        mTabLayout.getTabAt(2).setIcon(R.drawable.ic_person);
        //

    }
}
