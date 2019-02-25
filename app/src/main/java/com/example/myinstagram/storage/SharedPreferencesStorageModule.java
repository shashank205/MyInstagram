package com.example.myinstagram.storage;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class SharedPreferencesStorageModule {
    private Context context;

    public SharedPreferencesStorageModule(Context context) {
        this.context = context;
    }

    @Provides
    SharedPreferencesStorage provideSharedPreferencesStorage() {
        return new SharedPreferencesStorage(this.context);
    }
}
