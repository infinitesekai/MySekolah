package com.example.mysekolah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.List;

public class Introductory_Page extends AppCompatActivity {
    ImageView img_name,img_education;
    LottieAnimationView openingAnimationView;

    private static final int NUM_PAGES = 2;
    private ViewPager2 viewPager;
    private ScreenSlidePageAdapter pageAdapter;
    private List<Fragment> fragments;
    private Button btnGetStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory_page);
        img_name =findViewById(R.id.Img_Name);
        img_education =findViewById(R.id.Img_Education);
        openingAnimationView =findViewById(R.id.opening);
        btnGetStarted = findViewById(R.id.btn_get_started);

        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Introductory_Page.this,Login_page.class);
                startActivity(intent);
                finish();
            }
        });

        fragments=new ArrayList<>();
        fragments.add(new OnBoardingFragment1());
        fragments.add(new OnBoardingFragment_2());

        viewPager = findViewById(R.id.vp);
        pageAdapter = new ScreenSlidePageAdapter(this);
        viewPager.setAdapter(pageAdapter);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Log.i("test.......", "onPageSelected: "+position);
                btnGetStarted.setVisibility(position==fragments.size()-1?View.VISIBLE:View.GONE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        img_education.animate().translationY(2000).setDuration(3000).setStartDelay(4000);
        img_name.animate().translationY(2000).setDuration(3000).setStartDelay(4000);
        openingAnimationView.animate().translationY(2000).setDuration(3000).setStartDelay(4000).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                viewPager.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });


    }

    private class ScreenSlidePageAdapter extends FragmentStateAdapter {

        public ScreenSlidePageAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragments.get(position);
        }

        @Override
        public int getItemCount() {
            return fragments.size();
        }
    }



}