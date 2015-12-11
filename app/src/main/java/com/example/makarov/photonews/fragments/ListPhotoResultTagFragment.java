package com.example.makarov.photonews.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.makarov.photonews.R;
import com.example.makarov.photonews.adapters.PhotoResultTagAdapter;
import com.example.makarov.photonews.network.RequestService;

import java.io.IOException;
import java.util.List;

/**
 * Created by makarov on 08.12.15.
 */
public class ListPhotoResultTagFragment extends Fragment {

    private ListView lvPhotoResultTag;

    private RequestService mRequestService;
    private List<String> mUrlPhotos;
    private String mLineTag;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_photo_result_tag_fragment, null);

        mLineTag = getArgumentsBundleLineTag(getArguments());

        try {
            mRequestService = new RequestService();
            mRequestService.requestPhotosTag(mLineTag);
        } catch (IOException e) {
            e.printStackTrace();
        }

        lvPhotoResultTag = (ListView) v.findViewById(R.id.lvPhotoResultTag);

        mUrlPhotos = mRequestService.getUrlImages();
        PhotoResultTagAdapter photoAdapter = new PhotoResultTagAdapter(getActivity(), mUrlPhotos);
        lvPhotoResultTag.setAdapter(photoAdapter);

        return v;
    }

    private String getArgumentsBundleLineTag(Bundle savedInstanceState) {
        if (savedInstanceState == null)
            return null;
        if (!savedInstanceState.containsKey(OperationTagFragment.OPERATION_KEY))
            return null;
        return savedInstanceState.getString(OperationTagFragment.OPERATION_KEY);

    }
}
