package com.example.myinstagram.dependency_injection;

import com.example.myinstagram.activities.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
interface AppBuilder {

    @ContributesAndroidInjector(modules = {MainActivityBuilder.class})
    MainActivity bindMainActivity();
}
