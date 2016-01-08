package com.example.makarov.photonews.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.makarov.photonews.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by makarov on 03.01.16.
 */
public class GoogleMapFragment extends Fragment implements GoogleMap.InfoWindowAdapter{

    private MapView mMapView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.google_map, null);

        MapsInitializer.initialize(getContext());
        mMapView = (MapView) v.findViewById(R.id.map);

        mMapView.onCreate(savedInstanceState);
        GoogleMap map = mMapView.getMap();
        /*
        if (map != null) {
            map.setInfoWindowAdapter(this);
            map.setMyLocationEnabled(true);
            LatLng sydney = new LatLng(-33.867, 151.206);
            CameraUpdate camera = CameraUpdateFactory.newLatLngZoom(sydney, 8);
            map.animateCamera(camera);

            Marker marker = map.addMarker(new MarkerOptions()
                    .title("Sydney")
                    .snippet("The most populous city in Australia.")
                    .position(sydney));

        }*/
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
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

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
