package com.example.myinstagram;

import android.support.v4.app.Fragment;

import com.example.myinstagram.fragment.FragmentType;
import com.example.myinstagram.fragment.HomeFragment;
import com.example.myinstagram.fragment.SearchFragment;

public class FragmentFactory {

    private FragmentFactory() {
        //Do Nothing
    }

    public static Fragment getFragment(FragmentType fragmentType) {
        switch (fragmentType) {
            case HOME:
                return new HomeFragment();
            case SEARCH:
                return new SearchFragment();
            default:
                return null;
        }
    }
}
