package com.example.makarov.photonews.ui.dialog;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.makarov.photonews.R;
import com.example.makarov.photonews.adapters.SubscriptionsAdapter;
import com.example.makarov.photonews.database.LocationDbAdapter;
import com.example.makarov.photonews.di.AppInjector;
import com.example.makarov.photonews.models.Location;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChangeNameLocationDialog extends DialogFragment {

    @Bind(R.id.et_new_name_location)
    EditText mEtNewNameLocation;
    @Bind(R.id.tv_name_location)
    TextView mTvNameLocation;
    @Bind(R.id.btn_cancel)
    Button mCancel;
    @Bind(R.id.btn_change)
    Button mChange;

    @Inject
    LocationDbAdapter mLocationDbAdapter;

    private final Location mLocation;
    private final SubscriptionsAdapter mSubscriptionsAdapter;

    private final View.OnClickListener mOnClickCancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };

    private final View.OnClickListener mOnClickChangeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String newNameLocation = mEtNewNameLocation.getText().toString();

            if (!TextUtils.isEmpty(newNameLocation)) {
                mLocation.setName(newNameLocation);
                mLocationDbAdapter.open().update(mLocation);
                mLocationDbAdapter.close();
                mSubscriptionsAdapter.notifyDataSetChanged();
                dismiss();
            }
        }
    };

    public ChangeNameLocationDialog(Location location, SubscriptionsAdapter adapter) {
        mLocation = location;
        mSubscriptionsAdapter = adapter;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Change name location");
        View v = inflater.inflate(R.layout.change_name_location_dialog, null);
        ButterKnife.bind(this, v);
        AppInjector.get().inject(this);

        mCancel.setOnClickListener(mOnClickCancelListener);
        mChange.setOnClickListener(mOnClickChangeListener);

        mTvNameLocation.setText(mLocation.getName());

        setCancelable(false);
        return v;
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }
}
