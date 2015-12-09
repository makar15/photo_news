package com.example.makarov.photonews.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.makarov.photonews.R;
import com.example.makarov.photonews.adapters.PhotoHistoryTagAdapter;
import com.example.makarov.photonews.network.AuthenticationNetwork;

/**
 * Created by makarov on 08.12.15.
 */
public class ListPhotoHistoryTagFragment extends Fragment {

    private ListView lvPhotoHistoryTag;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_photo_history_taf_fragment, null);

        AuthenticationNetwork mAuthenticationNetwork = new AuthenticationNetwork(getActivity());

        lvPhotoHistoryTag = (ListView) v.findViewById(R.id.lvPhotoHistoryTag);

        PhotoHistoryTagAdapter photoAdapter = new PhotoHistoryTagAdapter(getActivity());
        lvPhotoHistoryTag.setAdapter(photoAdapter);

        return v;
    }

}
