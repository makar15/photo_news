package com.example.makarov.photonews;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class FactoryModule {

    @Provides
    @Singleton
    FactoryPostFinder provideFactoryPostFinder() {
        return new FactoryPostFinder();
    }
}
