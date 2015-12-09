package com.example.makarov.photonews.activitys;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.example.makarov.photonews.R;
import com.example.makarov.photonews.fragments.ListPhotoHistoryTagFragment;

public class MainActivity extends FragmentActivity {

    private static final String KEY_ADD_FRAGMENT_TO_BACK_STACK = "KEY_ADD_FRAGMENT_TO_BACK_STACK";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openBaseFragment(new ListPhotoHistoryTagFragment());

    }

    private void openFragment(Fragment fragment, boolean saveInBackstack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.LayoutActivity, fragment);
        if(saveInBackstack)
            ft.addToBackStack(KEY_ADD_FRAGMENT_TO_BACK_STACK);
        ft.commit();
    }

    private void openBaseFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.LayoutActivity, fragment).commit();
    }
}
