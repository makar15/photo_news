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

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Created by makarov on 08.12.15.
 */
public class ListPhotoHistoryTagFragment extends Fragment {

    private ListView lvPhotoHistoryTag;
    private List<String> mUrlPhotos;

    private AuthenticationNetwork mAuthenticationNetwork;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_photo_history_tag_fragment, null);

        try {
            mAuthenticationNetwork = new AuthenticationNetwork();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        lvPhotoHistoryTag = (ListView) v.findViewById(R.id.lvPhotoHistoryTag);

        //PhotoHistoryTagAdapter photoAdapter = new PhotoHistoryTagAdapter(getActivity(), );
        //lvPhotoHistoryTag.setAdapter(photoAdapter);

        return v;
    }

}
