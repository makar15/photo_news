package com.example.makarov.photonews.activitys;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.example.makarov.photonews.R;
import com.example.makarov.photonews.fragments.ListPhotoHistoryTagFragment;
import com.example.makarov.photonews.fragments.ListPhotoResultTagFragment;
import com.example.makarov.photonews.fragments.OperationTagFragment;

public class MainActivity extends FragmentActivity {

    private static final String KEY_ADD_FRAGMENT_TO_BACK_STACK = "key_add_fragment_to_back_stack";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openBaseFragment(new OperationTagFragment());

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

    private void openFragment(Fragment fragment, boolean saveInBackStack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.LayoutActivityMain, fragment);
        if (saveInBackStack)
            ft.addToBackStack(KEY_ADD_FRAGMENT_TO_BACK_STACK);
        ft.commit();
    }

    private void openBaseFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.LayoutActivityMain, fragment).commit();
    }
}
