package com.example.myinstagram;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.myinstagram.fragment.FragmentType;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if(item.getItemId() == R.id.navigation_home) {
                replaceFragment(FragmentType.HOME);
                return true;
            }
            else if(item.getItemId() == R.id.navigation_search) {
                replaceFragment(FragmentType.SEARCH);
                return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Fragment homeFragment = FragmentFactory.getFragment(FragmentType.HOME);
        if(homeFragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, homeFragment)
                    .commit();
        }
    }

    public void replaceFragment(FragmentType fragmentType) {
        Fragment newFragment = FragmentFactory.getFragment(fragmentType);
        if(newFragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, newFragment)
                    .commit();
        }
    }
}