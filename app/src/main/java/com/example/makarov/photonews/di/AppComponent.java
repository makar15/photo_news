package com.example.makarov.photonews.di;

import com.example.makarov.photonews.ui.activity.MainActivity;
import com.example.makarov.photonews.ui.fragments.GoogleMapFragment;
import com.example.makarov.photonews.ui.fragments.OperationTagFragment;
import com.example.makarov.photonews.ui.fragments.PhotoFragment;
import com.example.makarov.photonews.ui.fragments.SubscriptionsListFragment;
import com.example.makarov.photonews.ui.dialog.ChangeNameLocationDialog;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {MainModule.class})
public interface AppComponent {

    void inject(PhotoFragment fragment);

    void inject(MainActivity activity);

    void inject(OperationTagFragment fragment);

    void inject(GoogleMapFragment fragment);

    void inject(SubscriptionsListFragment fragment);

    void inject(ChangeNameLocationDialog dialog);

}
