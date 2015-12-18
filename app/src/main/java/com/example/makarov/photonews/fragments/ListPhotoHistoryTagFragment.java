package com.example.makarov.photonews.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.makarov.photonews.R;

/**
 * Created by makarov on 08.12.15.
 */
public class ListPhotoHistoryTagFragment extends Fragment {

    private ListView lvPhotoHistoryTag;

    private String mLineTag;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_photo_history_tag_fragment, null);

        mLineTag = getArgumentsBundleLineTag(getArguments());

        lvPhotoHistoryTag = (ListView) v.findViewById(R.id.lv_photo_history_tag);

        return v;
    }

    private String getArgumentsBundleLineTag(Bundle savedInstanceState) {
        if (savedInstanceState == null)
            return null;
        if (!savedInstanceState.containsKey(TagsListFragment.TAGS_LIST_KEY))
            return null;
        return savedInstanceState.getString(TagsListFragment.TAGS_LIST_KEY);

    }
}
