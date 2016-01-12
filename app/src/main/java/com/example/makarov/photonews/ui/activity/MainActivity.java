package com.example.makarov.photonews.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.example.makarov.photonews.R;
import com.example.makarov.photonews.ui.fragments.GoogleMapFragment;
import com.example.makarov.photonews.ui.fragments.ListPhotoHistoryTagFragment;
import com.example.makarov.photonews.ui.fragments.ListPhotoResultLocationFragment;
import com.example.makarov.photonews.ui.fragments.ListPhotoResultTagFragment;
import com.example.makarov.photonews.ui.fragments.OperationTagFragment;
import com.example.makarov.photonews.ui.fragments.SubscriptionsListFragment;

public class MainActivity extends FragmentActivity {

    private static final String KEY_ADD_FRAGMENT_TO_BACK_STACK = "key_add_fragment_to_back_stack";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openBaseFragment(new SubscriptionsListFragment());
    }

    public void openOperationTagFragment(Bundle bundle) {
        Fragment newFragment = new OperationTagFragment();
        newFragment.setArguments(bundle);
        openFragment(newFragment, true);
    }

    public void openListPhotoHistoryTagFragment(Bundle bundle) {
        Fragment newFragment = new ListPhotoHistoryTagFragment();
        newFragment.setArguments(bundle);
        openFragment(newFragment, true);
    }

    public void openListPhotoResultTagFragment(Bundle bundle) {
        Fragment newFragment = new ListPhotoResultTagFragment();
        newFragment.setArguments(bundle);
        openFragment(newFragment, true);
    }

    public void openListPhotoResultLocationFragment(Bundle bundle) {
        Fragment newFragment = new ListPhotoResultLocationFragment();
        newFragment.setArguments(bundle);
        openFragment(newFragment, true);
    }

    public void openGoogleMapFragment(Bundle bundle) {
        Fragment newFragment = new GoogleMapFragment();
        newFragment.setArguments(bundle);
        openFragment(newFragment, true);
    }

    private void openFragment(Fragment fragment, boolean saveInBackStack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.layout_activity_main, fragment);
        if (saveInBackStack)
            ft.addToBackStack(KEY_ADD_FRAGMENT_TO_BACK_STACK);
        ft.commit();
    }

    private void openBaseFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_activity_main, fragment).commit();
    }
}
