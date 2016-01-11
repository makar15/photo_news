package com.example.makarov.photonews.ui.fragments;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.makarov.photonews.R;
import com.example.makarov.photonews.ui.activity.MainActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class GoogleMapFragment extends Fragment implements View.OnClickListener {

    public static final String GOOGLE_MAP_KEY = "google_map";

    private EditText mLineNameLocation;
    private MapView mMapView;

    private GoogleMap mMap;
    private Marker mMarker;
    private Address mAddress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.google_map, null);


        MapsInitializer.initialize(getContext());

        mMapView = (MapView) v.findViewById(R.id.map);
        mLineNameLocation = (EditText) v.findViewById(R.id.line_name_location);
        v.findViewById(R.id.search_location_btn).setOnClickListener(this);
        v.findViewById(R.id.open_photo_btn).setOnClickListener(this);

        mMapView.onCreate(savedInstanceState);
        mMap = mMapView.getMap();

        if (mMap != null) {
            initGoogleMap();
        }
        return v;
    }

    private void initGoogleMap() {

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                //TODO bag , long click ocean, NOT Geocoder
                Geocoder geocoder = new Geocoder(getContext());
                List<Address> list;
                try {
                    list = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                } catch (IOException e) {
                    return;
                }

                mAddress = list.get(0);
                if (mMarker != null) {
                    mMarker.remove();
                }
                MarkerOptions options = new MarkerOptions()
                        .title(mAddress.getLocality())
                        .position(new LatLng(latLng.latitude, latLng.longitude));

                mMarker = mMap.addMarker(options);
            }
        });
    }

    @Override
    public void onClick(View v) {

        String location = mLineNameLocation.getText().toString();

        switch (v.getId()) {
            case R.id.search_location_btn: {
                try {
                    if (!TextUtils.isEmpty(location)) {
                        findLocation(location);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            break;

            case R.id.open_photo_btn: {
                if (mMarker != null) {
                    openListPhotoResultLocation(mAddress);
                }
            }
            break;

            default:
                break;
        }
    }

    public void findLocation(String location) throws IOException {

        Geocoder geocoder = new Geocoder(getContext());
        List<Address> list = geocoder.getFromLocationName(location, 1);

        mAddress = list.get(0);

        String locality = mAddress.getLocality();
        LatLng latLng = new LatLng(mAddress.getLatitude(), mAddress.getLongitude());

        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, 15);
        mMap.moveCamera(update);

        if (mMarker != null) {
            mMarker.remove();
        }

        MarkerOptions markerOptions = new MarkerOptions()
                .title(locality)
                .position(new LatLng(mAddress.getLatitude(), mAddress.getLongitude()));

        mMarker = mMap.addMarker(markerOptions);

    }

    public void openListPhotoResultLocation(Address addressLocation) {
        double[] pointLocation = {addressLocation.getLatitude(), addressLocation.getLongitude()};
        Bundle bundle = new Bundle();
        bundle.putDoubleArray(GoogleMapFragment.GOOGLE_MAP_KEY, pointLocation);
        ((MainActivity) getActivity()).openListPhotoResultLocationFragment(bundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();

        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getContext());

        if (resultCode == ConnectionResult.SUCCESS) {
            Toast.makeText(getContext(), "isGooglePlayServicesAvailable SUCCESS",
                    Toast.LENGTH_LONG).show();
        } else {
            final int RQS_GooglePlayServices = 1;
            GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(), RQS_GooglePlayServices);
        }
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
