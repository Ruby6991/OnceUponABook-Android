package com.example.onceuponabook.Activities;

import android.os.Bundle;

import com.example.onceuponabook.Adapters.ViewPagerAdapter;
import com.example.onceuponabook.Fragments.HistoryFragment;
import com.example.onceuponabook.Fragments.ProfileFragment;
import com.example.onceuponabook.R;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

public class MyAccountActivity extends AppCompatActivity {

    private TabLayout tabLayoutAccount;
    private ViewPager viewPagerAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        tabLayoutAccount=findViewById(R.id.tab_layout_account);
        viewPagerAccount=findViewById(R.id.view_pager_account);
        ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager());

        //Adding fragments
        adapter.AddFragment(new ProfileFragment(),"Profile");
        adapter.AddFragment(new HistoryFragment(),"Purchase History");

        //Adapter setup
        viewPagerAccount.setAdapter(adapter);
        tabLayoutAccount.setupWithViewPager(viewPagerAccount);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("My Account");
        setSupportActionBar(toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

    }
}
