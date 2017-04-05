package com.example.use.drivers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.*;
import fragments.Empowerment;
import fragments.Realization;
import fragments.Refund;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by use on 05.04.17.
 */
public class MainTab extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ListView mDrawerListView;
    private String[] mDrawerItem;
    private DrawerLayout drawlayout;
    public String pref_ip;
    public SharedPreferences prefs;
    private String login = "";
    private Intent intent;
    private ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_tabs);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().hide();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        pref_ip = prefs.getString("pref_ip", "");
        login = prefs.getString("login", "");

        drawlayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerListView = (ListView)findViewById(R.id.left_drawer);
        mDrawerItem = getResources().getStringArray(R.array.drawer_items);
        mDrawerListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1,
                mDrawerItem));
        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                drawlayout.closeDrawers();
                switch (position){
                    case 0:
                        break;
                    case 1:
                        intent = new Intent(getApplicationContext(), PrefActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        break;
                    case 3:
                        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("login", "");
                        editor.commit();
                        intent = new Intent(getApplicationContext(), Splash.class);
                        startActivity(intent);
                        finish();
                        break;
                }
            }
        });

        LinearLayout layout = (LinearLayout)findViewById(R.id.layout_logo);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "позвонить", Toast.LENGTH_SHORT);
                toast.show();
            }
        });


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new Realization(), "Реализация");
        adapter.addFrag(new Empowerment(), "Деньги");
        adapter.addFrag(new Refund(), "Возврат");
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

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
