package com.example.makarov.photonews.utils;

import android.app.DialogFragment;
import android.app.FragmentManager;

public class CreateDialogUtils {

    private final String TAG_DIALOG = "dlg";
    private FragmentManager mFragmentManager;

    public CreateDialogUtils(FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
    }

    public void createDialog(DialogFragment dialog) {
        dialog.show(mFragmentManager, TAG_DIALOG);
    }
}
