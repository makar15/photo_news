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

import com.example.makarov.photonews.PhotoNewsApp;
import com.example.makarov.photonews.R;
import com.example.makarov.photonews.models.Location;
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
import java.util.Date;
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
        v.findViewById(R.id.add_location_btn).setOnClickListener(this);

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

                Geocoder geocoder = new Geocoder(getContext());
                List<Address> list;
                try {
                    list = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                } catch (IOException e) {
                    return;
                }

                if (!list.isEmpty()) {
                    mAddress = list.get(0);

                    removeExistingMarker();
                    initMarker();
                } else {
                    Toast.makeText(getContext(), "address is not found in Google maps",
                            Toast.LENGTH_LONG).show();
                }
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
                    openListPhotoResultLocation(initDbModelLocation());
                }
            }
            break;

            case R.id.add_location_btn: {
                //TODO see the location on the map
                if (mMarker != null) {
                    PhotoNewsApp.getApp().getLocationDbAdapter()
                            .open().add(initDbModelLocation());
                }
            }
            break;

            default:
                break;
        }
    }

    private void findLocation(String location) throws IOException {

        Geocoder geocoder = new Geocoder(getContext());
        List<Address> list = geocoder.getFromLocationName(location, 1);

        if (!list.isEmpty()) {
            mAddress = list.get(0);

            LatLng latLng = new LatLng(mAddress.getLatitude(), mAddress.getLongitude());
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, 12);
            mMap.moveCamera(update);

            removeExistingMarker();
            initMarker();
        } else {
            Toast.makeText(getContext(), "address is not found in Google maps",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void openListPhotoResultLocation(Location location) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(GoogleMapFragment.GOOGLE_MAP_KEY, location);
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

    private void initMarker() {
        MarkerOptions markerOptions = new MarkerOptions()
                .title(mAddress.getLocality())
                .position(new LatLng(mAddress.getLatitude(), mAddress.getLongitude()));

        mMarker = mMap.addMarker(markerOptions);
    }

    private void removeExistingMarker() {
        if (mMarker != null) {
            mMarker.remove();
        }
    }

    private Location initDbModelLocation() {
        return new Location(getInitNameLocation(),
                mAddress.getLatitude(), mAddress.getLongitude(), mAddress.getCountryName(),
                mAddress.getLocality(), mAddress.getThoroughfare(), new Date().getTime());
    }

    private String getNullableString(String string) {
        return string != null ? string + ", " : "";
    }

    private String getInitNameLocation() {
        String countryName, locality, thoroughfare;

        countryName = getNullableString(mAddress.getCountryName());
        locality = getNullableString(mAddress.getLocality());
        thoroughfare = getNullableString(mAddress.getThoroughfare());

        String result = countryName + locality + thoroughfare;
        return result.substring(0, result.length() - 2);
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
