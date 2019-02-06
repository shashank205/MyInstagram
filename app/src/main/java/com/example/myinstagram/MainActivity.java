package com.example.myinstagram;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements ModifyFragment{

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                if (item.getItemId() == R.id.navigation_home) {
                    getNewFragment(FragmentFactory.HOME);
                    return true;
                } else if (item.getItemId() == R.id.navigation_search) {
                    getNewFragment(FragmentFactory.SEARCH);
                    return true;
                }
                return false;
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Fragment homeFragment = FragmentFactory.getFragment(FragmentFactory.HOME);
        if(homeFragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, homeFragment)
                    .commit();
        }
    }

    @Override
    public void getNewFragment(int fragmentToReplaceWith) {
        Fragment newFragment = FragmentFactory.getFragment(fragmentToReplaceWith);
        if(newFragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, newFragment)
                    .commit();
        }
    }
}