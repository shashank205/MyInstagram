package com.example.myinstagram.dependency_injection;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;

@Module
interface ContextModule {

    @Binds
    Context provideContext(Application application);
}