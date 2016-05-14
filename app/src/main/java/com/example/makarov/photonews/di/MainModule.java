package com.example.makarov.photonews.di;

import android.content.Context;

import com.example.makarov.photonews.DataManager;
import com.example.makarov.photonews.FactoryPostFinder;
import com.example.makarov.photonews.database.LocationDbAdapter;
import com.example.makarov.photonews.database.MediaPostDbAdapter;
import com.example.makarov.photonews.database.TagDbAdapter;
import com.octo.android.robospice.GsonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    private final Context mContext;

    public MainModule(Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return mContext;
    }

    @Provides
    @Singleton
    SpiceManager provideSpiceManager() {
        SpiceManager spiceManager = new SpiceManager(GsonSpringAndroidSpiceService.class);
        spiceManager.start(mContext);
        return spiceManager;
    }

    @Provides
    @Singleton
    FactoryPostFinder provideFactoryPostFinder(SpiceManager spiceManager) {
        return new FactoryPostFinder(spiceManager);
    }

    @Provides
    @Singleton
    TagDbAdapter provideTagDbAdapter() {
        return new TagDbAdapter(mContext);
    }

    @Provides
    @Singleton
    LocationDbAdapter provideLocationDbAdapter() {
        return new LocationDbAdapter(mContext);
    }

    @Provides
    @Singleton
    MediaPostDbAdapter provideMediaPostDbAdapter() {
        return new MediaPostDbAdapter(mContext);
    }

    @Provides
    @Singleton
    DataManager provideDataManager() {
        return new DataManager();
    }
}
