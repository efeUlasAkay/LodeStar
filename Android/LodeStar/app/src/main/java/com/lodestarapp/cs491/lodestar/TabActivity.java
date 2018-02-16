package com.lodestarapp.cs491.lodestar;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lodestarapp.cs491.lodestar.Interfaces.MyOnFocusListenable;
import com.lodestarapp.cs491.lodestar.Models.QRCodeInfo;

import java.util.ArrayList;
import java.util.List;

public class TabActivity extends AppCompatActivity {
    TripActivity trip = new TripActivity();
    boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);


        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(viewPager);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);


        tabLayout.getTabAt(0).setIcon(R.drawable.ic_004_homepage);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_003_departures);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_002_star);


        tabLayout.getTabAt(0).getIcon().setAlpha(255);
        tabLayout.getTabAt(1).getIcon().setAlpha(90);
        tabLayout.getTabAt(2).getIcon().setAlpha(90);


        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.my_tab, null);
        tabThree.setText("Favorites");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_002_star, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);


        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.my_tab, null);
        tabTwo.setText("Trip");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_003_departures, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);



        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.my_tab, null);
        tabOne.setText("Home");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_004_homepage, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);






        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        tabLayout.getTabAt(0).getIcon().setAlpha(255);
                        tabLayout.getTabAt(1).getIcon().setAlpha(90);
                        tabLayout.getTabAt(2).getIcon().setAlpha(90);
                        break;
                    case 1:
                        tabLayout.getTabAt(0).getIcon().setAlpha(90);
                        tabLayout.getTabAt(1).getIcon().setAlpha(255);
                        tabLayout.getTabAt(2).getIcon().setAlpha(90);
                        break;
                    case 2:
                        tabLayout.getTabAt(0).getIcon().setAlpha(90);
                        tabLayout.getTabAt(1).getIcon().setAlpha(90);
                        tabLayout.getTabAt(2).getIcon().setAlpha(255);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    public void readQRCode(View view){
        Intent intent = new Intent(this, QRCodeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if(exit){
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);



        }
        else{
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 2 * 1000);


        }

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeActivity(), "Home");
        adapter.addFragment(trip, "Trip");
        adapter.addFragment(new FavoritesFragment(), "Favorites");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        Bundle data = getIntent().getExtras();
        if (data != null) {
            QRCodeInfo qrCodeInfo = data.getParcelable("QRCodeInfo");

            if(qrCodeInfo != null){
                ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
                viewPager.setCurrentItem(1);
            }
        }

        if(trip instanceof MyOnFocusListenable) {
            ((MyOnFocusListenable) trip).onWindowFocusChanged(hasFocus);
        }
    }
    public void weatherStart(View view){
        Intent intent = new Intent(this, WeatherInformationActivity.class);
        startActivity(intent);
    }


    public void currencyStart(View view){
        Intent intent = new Intent(this, CurrencyActivity.class);
        startActivity(intent);
    }

    public void livingStart(View view){
        Intent intent = new Intent(this, LivingExpensesActivity.class);
        startActivity(intent);
    }


}