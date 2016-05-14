package com.example.makarov.photonews.ui.fragments;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.makarov.photonews.R;
import com.example.makarov.photonews.database.LocationDbAdapter;
import com.example.makarov.photonews.di.AppInjector;
import com.example.makarov.photonews.models.Location;
import com.example.makarov.photonews.ui.activity.MainActivity;

import com.github.florent37.materialtextfield.MaterialTextField;
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

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GoogleMapFragment extends Fragment {

    public static final String GOOGLE_MAP_KEY = "GoogleMapFragment";

    @Bind(R.id.input_layout_location)
    MaterialTextField mLayoutLocation;
    @Bind(R.id.line_name_location)
    EditText mLineNameLocation;
    @Bind(R.id.map)
    MapView mMapView;
    @Bind(R.id.search_location_btn)
    Button mSearchLocation;
    @Bind(R.id.open_photo_btn)
    Button mOpenPhoto;
    @Bind(R.id.add_location_btn)
    Button mAddLocation;
    @Bind(R.id.radius_search_bar)
    DiscreteSeekBar mRadiusSearch;

    @Inject
    LocationDbAdapter mLocationDbAdapter;

    private GoogleMap mMap;
    private Marker mMarker;
    private Address mAddress;

    private final View.OnClickListener mOnClickSearchLocationListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String location = mLineNameLocation.getText().toString();

            try {
                if (!TextUtils.isEmpty(location)) {
                    findLocation(location);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    private final View.OnClickListener mOnClickOpenPhotoListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mMarker != null) {
                openListPhotoResultLocation(initDbModelLocation());
            }
        }
    };

    private final View.OnClickListener mOnClickAddLocationListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO see the location on the map
            if (mMarker != null) {
                long result = mLocationDbAdapter.open().add(initDbModelLocation());
                mLocationDbAdapter.close();

                if (result == -1) {
                    Toast.makeText(getContext(), "location is already in the list",
                            Toast.LENGTH_LONG).show();
                }
            }
        }
    };

    private final GoogleMap.OnMapLongClickListener mOnMapLongClickListener =
            new GoogleMap.OnMapLongClickListener() {
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
            };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.google_map, null);
        ButterKnife.bind(this, v);
        AppInjector.get().inject(this);

        MapsInitializer.initialize(getContext());

        mSearchLocation.setOnClickListener(mOnClickSearchLocationListener);
        mOpenPhoto.setOnClickListener(mOnClickOpenPhotoListener);
        mAddLocation.setOnClickListener(mOnClickAddLocationListener);

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

        mMap.setOnMapLongClickListener(mOnMapLongClickListener);
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
        bundle.putParcelable(PhotoFragment.PHOTO_RESULT_LOCATION_KEY, location);
        ((MainActivity) getActivity()).openListResultLocationFragment(bundle);
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
                mAddress.getLatitude(),
                mAddress.getLongitude(),
                mAddress.getCountryName(),
                mAddress.getLocality(),
                mAddress.getThoroughfare(),
                new Date().getTime(),
                mRadiusSearch.getProgress());
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
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
