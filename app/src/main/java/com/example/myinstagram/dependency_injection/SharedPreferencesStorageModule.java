package com.example.myinstagram.dependency_injection;

import android.content.Context;

import com.example.myinstagram.storage.SharedPreferencesStorage;

import dagger.Module;
import dagger.Provides;

@Module
class SharedPreferencesStorageModule {

    @Provides
    SharedPreferencesStorage provideSharedPreferencesStorage(Context context) {
        return new SharedPreferencesStorage(context);
    }
}
