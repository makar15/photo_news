package com.example.makarov.photonews;

public class CreateAppComponent {

    private AppComponent mAppComponent;

    public CreateAppComponent() {
        mAppComponent = DaggerAppComponent.builder()
                .factoryModule(new FactoryModule())
                .build();
    }

    public AppComponent getComponent() {
        return mAppComponent;
    }
}
