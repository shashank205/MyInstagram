package com.example.myinstagram.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.example.myinstagram.interfaces.ModifyFragment;
import com.example.myinstagram.R;
import com.example.myinstagram.databinding.ActivityMainBinding;
import com.example.myinstagram.fragments.HomeFragment;
import com.example.myinstagram.fragments.SearchFragment;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity implements ModifyFragment, HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = navigationItemSelected -> {
                if (navigationItemSelected.getItemId() == R.id.navigation_home) {
                    replaceFragment(HomeFragment.newInstance());
                    return true;
                } else if (navigationItemSelected.getItemId() == R.id.navigation_search) {
                    replaceFragment(SearchFragment.newInstance());
                    return true;
                }
                return false;
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.navigation.setOnNavigationItemSelectedListener(this.onNavigationItemSelectedListener);
        createFirstFragment(HomeFragment.newInstance());
    }

    @Override
    public void createFirstFragment(Fragment firstFragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, firstFragment)
                .commit();
    }

    @Override
    public void replaceFragment(Fragment fragmentToReplaceWith) {
        getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragmentToReplaceWith)
                    .commit();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}