package com.example.makarov.photonews.utils;

import android.app.DialogFragment;
import android.app.FragmentManager;

public class CreateDialogUtils {

    private static final String TAG_DIALOG = "CreateDialogUtils";

    private final FragmentManager mFragmentManager;

    public CreateDialogUtils(FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
    }

    public void createDialog(DialogFragment dialog) {
        dialog.show(mFragmentManager, TAG_DIALOG);
    }
}
