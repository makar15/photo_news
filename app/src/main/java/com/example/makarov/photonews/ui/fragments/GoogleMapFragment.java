package com.example.makarov.photonews.ui.fragments;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.makarov.photonews.R;
import com.example.makarov.photonews.database.LocationDbAdapter;
import com.example.makarov.photonews.di.AppInjector;
import com.example.makarov.photonews.models.Location;
import com.example.makarov.photonews.ui.activity.MainActivity;
import com.example.makarov.photonews.utils.SystemUtils;
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

    private static final float SETUP_MAPS_ZOOM = 12;
    private static final int MAX_RESULT_SEARCH = 3;
    private static final int INDEX_REMOVE_THE_POINT = 2;
    private static final int RQS_GOOGLE_PLAY_SERVICES = 1;
    private static final int ERR_ADD_LOCATION = -1;

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
    @Nullable private Marker mMarker;
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

                if (result == ERR_ADD_LOCATION) {
                    showMassage(getString(R.string.err_add_location));
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
                        list = geocoder.getFromLocation(latLng.latitude,
                                latLng.longitude, MAX_RESULT_SEARCH);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return;
                    }

                    if (list.isEmpty()) {
                        showMassage(getString(R.string.err_search_location));
                        return;
                    }
                    mAddress = list.get(0);

                    removeExistingMarker();
                    initMarker();
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
        List<Address> list = geocoder.getFromLocationName(location, MAX_RESULT_SEARCH);

        if (list.isEmpty()) {
            showMassage(getString(R.string.err_search_location));
            return;
        }
        mAddress = list.get(0);

        LatLng latLng = new LatLng(mAddress.getLatitude(), mAddress.getLongitude());
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, SETUP_MAPS_ZOOM);
        mMap.moveCamera(update);

        removeExistingMarker();
        initMarker();
    }

    private void openListPhotoResultLocation(Location location) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(PhotoFragment.LOCATION_KEY, location);
        ((MainActivity) getActivity()).openListResultLocationFragment(bundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();

        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getContext());
        if (resultCode == ConnectionResult.SUCCESS) {
            showMassage(getString(R.string.success_google_service));
            return;
        }
        GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(), RQS_GOOGLE_PLAY_SERVICES);
    }

    private void showMassage(String massage) {
        SystemUtils.toastMassage(getContext(), massage);
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
        return result.substring(0, result.length() - INDEX_REMOVE_THE_POINT);
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
