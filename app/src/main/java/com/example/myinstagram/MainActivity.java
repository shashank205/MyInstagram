package com.example.myinstagram;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.example.myinstagram.databinding.ActivityMainBinding;
import com.example.myinstagram.fragment.HomeFragment;
import com.example.myinstagram.fragment.SearchFragment;

public class MainActivity extends AppCompatActivity implements ModifyFragment{

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                if (item.getItemId() == R.id.navigation_home) {
                    getNewFragment(HomeFragment.newInstance());
                    return true;
                } else if (item.getItemId() == R.id.navigation_search) {
                    getNewFragment(SearchFragment.newInstance());
                    return true;
                }
                return false;
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Fragment onCreateFragment = HomeFragment.newInstance();
        getSupportFragmentManager()
            .beginTransaction()
            .add(R.id.fragment_container, onCreateFragment)
            .commit();
    }

    @Override
    public void getNewFragment(Fragment fragmentToReplaceWith) {
        getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragmentToReplaceWith)
                    .commit();
    }
}