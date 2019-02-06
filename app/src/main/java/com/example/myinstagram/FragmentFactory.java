package com.example.myinstagram;

import android.support.v4.app.Fragment;

import com.example.myinstagram.fragment.HomeFragment;
import com.example.myinstagram.fragment.SearchFragment;

public class FragmentFactory {

    public static final int HOME = 1;
    public static final int SEARCH = 2;

    private FragmentFactory() {
        //Do Nothing
    }

    public static Fragment getFragment(int fragmentType) {
        switch (fragmentType) {
            case HOME:
                return HomeFragment.newInstance();
            case SEARCH:
                return SearchFragment.newInstance();
            default:
                return null;
        }
    }
}