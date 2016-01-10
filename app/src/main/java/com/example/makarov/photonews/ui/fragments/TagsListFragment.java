package com.example.makarov.photonews.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.makarov.photonews.PhotoNewsApp;
import com.example.makarov.photonews.R;
import com.example.makarov.photonews.adapters.TagAdapter;
import com.example.makarov.photonews.ui.activity.MainActivity;

import java.util.List;

public class TagsListFragment extends Fragment {

    public static final String TAGS_LIST_KEY = "tags_list";
    private final String TAG = "myLogs";

    private RecyclerView mRecyclerView;
    private TagAdapter mTagAdapter;
    private LinearLayoutManager mLayoutManager;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tags_list_fragment, null);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.lvTags);
        setLayoutManagerForRecyclerView();

        Button searchBtn = (Button) v.findViewById(R.id.enter_search_btn);
        searchBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                openOperationTag(null);
            }
        });

        List<String> tags = PhotoNewsApp.getApp().getTagDbAdapter().open().getAllTags();
        setAdapterForRecyclerView(tags);

        return v;
    }

    public void openListPhotoHistoryTag(String lineTag) {
        Bundle bundle = new Bundle();
        bundle.putString(TagsListFragment.TAGS_LIST_KEY, lineTag);
        ((MainActivity) getActivity()).openListPhotoHistoryTagFragment(bundle);
    }

    public void openOperationTag(String key) {
        Bundle bundle = new Bundle();
        bundle.putString(TagsListFragment.TAGS_LIST_KEY, key);
        ((MainActivity) getActivity()).openOperationTagFragment(bundle);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializeHistoryRecyclerView();
    }

    private void setLayoutManagerForRecyclerView() {
        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void setAdapterForRecyclerView(List<String> tags) {
        mTagAdapter = new TagAdapter(tags, new TagAdapter.OnClickOpenPhotoNews() {
            @Override
            public void onClick(String clickLineTag) {
                openListPhotoHistoryTag(clickLineTag);
            }
        });
        mRecyclerView.setAdapter(mTagAdapter);
    }

    private void initializeHistoryRecyclerView() {
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        PhotoNewsApp.getApp().getTagDbAdapter().close();
    }
}
