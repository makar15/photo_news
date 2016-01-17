package com.example.makarov.photonews.ui.fragments.dialog;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.makarov.photonews.PhotoNewsApp;
import com.example.makarov.photonews.R;
import com.example.makarov.photonews.adapters.AdapterSubscriptions;
import com.example.makarov.photonews.models.Location;

public class ChangeNameLocationDialog extends DialogFragment implements View.OnClickListener {

    private Location mLocation;
    private EditText mEtNewNameLocation;
    private AdapterSubscriptions mAdapterSubscriptions;

    public ChangeNameLocationDialog(Location location, AdapterSubscriptions adapter) {
        mLocation = location;
        mAdapterSubscriptions = adapter;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Change name location");
        View v = inflater.inflate(R.layout.change_name_location_dialog, null);

        mEtNewNameLocation = (EditText) v.findViewById(R.id.et_new_name_location);
        TextView nameLocation = (TextView) v.findViewById(R.id.tv_name_location);
        v.findViewById(R.id.btn_cancel).setOnClickListener(this);
        v.findViewById(R.id.btn_change).setOnClickListener(this);

        nameLocation.setText(mLocation.getName());

        this.setCancelable(false);
        return v;
    }

    @Override
    public void onClick(View v) {
        String mNewNameLocation = mEtNewNameLocation.getText().toString();

        switch (v.getId()) {
            case R.id.btn_cancel: {
                dismiss();
            }
            break;

            case R.id.btn_change: {
                if (!TextUtils.isEmpty(mNewNameLocation)) {
                    mLocation.setName(mNewNameLocation);
                    PhotoNewsApp.getApp().getLocationDbAdapter().open().update(mLocation);
                    PhotoNewsApp.getApp().getLocationDbAdapter().close();
                    mAdapterSubscriptions.notifyDataSetChanged();
                    dismiss();
                }
            }
            break;

            default:
                break;
        }
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }
}
