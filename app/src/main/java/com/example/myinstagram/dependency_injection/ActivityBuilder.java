package com.example.myinstagram.dependency_injection;

import com.example.myinstagram.fragments.HomeFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
interface ActivityBuilder {

    @ContributesAndroidInjector(modules = {HTTPClientModule.class, SharedPreferencesStorageModule.class})
    HomeFragment bindHomeFragment();
}
