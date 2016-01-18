package com.example.makarov.photonews;

import com.example.makarov.photonews.ui.activity.MainActivity;
import com.example.makarov.photonews.ui.fragments.PhotoFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {FactoryModule.class})
public interface AppComponent {

    void inject(PhotoFragment fragment);

    void inject(MainActivity fragment);
}
