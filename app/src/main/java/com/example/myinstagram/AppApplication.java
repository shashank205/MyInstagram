package com.example.myinstagram;

import android.app.Activity;
import android.app.Application;

import com.example.myinstagram.dependency_injection.AppComponent;
import com.example.myinstagram.dependency_injection.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class AppApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        AppComponent appComponent = DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this);

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().name("postRealm.realm").build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }
}
