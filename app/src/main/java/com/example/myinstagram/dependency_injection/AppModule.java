package com.example.myinstagram.dependency_injection;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;

@Module
interface AppModule {
    @Binds
    Context provideContext(Application application);
}