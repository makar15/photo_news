package com.example.makarov.photonews.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.makarov.photonews.R;
import com.example.makarov.photonews.activitys.MainActivity;

/**
 * Created by makarov on 08.12.15.
 */
public class TagsListFragment extends Fragment{

    public static final String TAGS_LIST_KEY = "tags_list";

    private ListView lvTags;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tags_list_fragment, null);

        lvTags = (ListView) v.findViewById(R.id.lvTags);

        return v;
    }

    public void openListPhotoHistoryTag(String key) {
        Bundle bundle = new Bundle();
        bundle.putString(TagsListFragment.TAGS_LIST_KEY, key);
        ((MainActivity) getActivity()).openListPhotoHistoryTagFragment(bundle);
    }

    public void openOperationTag(String key) {
        Bundle bundle = new Bundle();
        bundle.putString(TagsListFragment.TAGS_LIST_KEY, key);
        ((MainActivity) getActivity()).openOperationTagFragment(bundle);
    }
}
