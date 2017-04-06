package com.example.use.drivers;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
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
    private String type_doc;
    private Waybill waybill;
    private Fragment fragment;

    final int DIALOG = 1;
    public static final String EXTRA_WAYBILL = "TYPE_DOC";

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        AlertDialog aDialog = (AlertDialog) dialog;
        ListAdapter lAdapter = aDialog.getListView().getAdapter();
        if (lAdapter instanceof BaseAdapter) {
            BaseAdapter bAdapter = (BaseAdapter) lAdapter;
            bAdapter.notifyDataSetChanged();
        }
    }

    OnClickListener myClickListener = new OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            String tel = "";
             switch (which){
                case 0: // тоговый представитель
                    tel = "123456";
                    break;
                case 1: // торговая точка
                    tel = waybill.data7;
                    break;
                case 2: // диспетчер
                    tel = waybill.data6;
                    break;
            }
            intent = new Intent();
            intent.setData(Uri.parse("tel:" + tel));
            intent.setAction(Intent.ACTION_CALL);
            startActivity(intent);
        }
    };

    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        //adb.setTitle(R.string.items);
        adb.setItems(R.array.dialog_items, myClickListener);
        return adb.create();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_tabs);

        waybill = getIntent().getParcelableExtra(EXTRA_WAYBILL);

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
                        intent = new Intent(getApplicationContext(), Fueling.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "начат обмен", Toast.LENGTH_SHORT);
                        toast.show();
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
                showDialog(DIALOG);
            }
        });
    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_settings:
                intent = new Intent(getApplicationContext(), PrefActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_qiute:
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

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        switch (waybill.data4){
            case "Реализация":
                fragment = new Realization();
                adapter.addFrag(fragment, "Реализация");
                break;
            case "Деньги":
                fragment = new Empowerment();
                adapter.addFrag(fragment, "Деньги");
                break;
            case "Возврат":
                fragment = new Refund();
                adapter.addFrag(fragment, "Возврат");
                break;
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable("waybill", waybill);
        fragment.setArguments(bundle);

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
