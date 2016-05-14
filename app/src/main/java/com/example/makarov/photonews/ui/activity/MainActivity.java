package com.example.makarov.photonews.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.example.makarov.photonews.R;
import com.example.makarov.photonews.ui.fragments.GoogleMapFragment;
import com.example.makarov.photonews.ui.fragments.OperationTagFragment;
import com.example.makarov.photonews.ui.fragments.PhotoLocationFragment;
import com.example.makarov.photonews.ui.fragments.MediaPostFragment;
import com.example.makarov.photonews.ui.fragments.PhotoTagFragment;
import com.example.makarov.photonews.ui.fragments.SubscriptionsListFragment;

public class MainActivity extends FragmentActivity {

    private static final String KEY_ADD_FRAGMENT_TO_BACK_STACK = "KeyAddFragmentToBackStack";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_activity_main, new SubscriptionsListFragment()).commit();
    }

    public void openOperationTagFragment(Bundle bundle) {
        openFragment(new OperationTagFragment(), bundle, true);
    }

    public void openListResultTagFragment(Bundle bundle) {
        openFragment(new PhotoTagFragment(), bundle, true);
    }

    public void openListResultLocationFragment(Bundle bundle) {
        openFragment(new PhotoLocationFragment(), bundle, true);
    }

    public void openListSaveMediaPostsFragment(Bundle bundle) {
        openFragment(new MediaPostFragment(), bundle, true);
    }

    public void openGoogleMapFragment(Bundle bundle) {
        openFragment(new GoogleMapFragment(), bundle, true);
    }

    private void openFragment(Fragment fragment, Bundle bundle, boolean saveInBackStack) {
        fragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.layout_activity_main, fragment);
        if (saveInBackStack)
            ft.addToBackStack(KEY_ADD_FRAGMENT_TO_BACK_STACK);
        ft.commit();
    }
}
