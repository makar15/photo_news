package com.example.makarov.photonews.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.makarov.photonews.PhotoNewsApp;
import com.example.makarov.photonews.R;
import com.example.makarov.photonews.adapters.TagAdapter;
import com.example.makarov.photonews.ui.activity.MainActivity;

import java.util.List;

public class TagsListFragment extends Fragment implements View.OnClickListener {

    public static final String TAGS_LIST_KEY = "tags_list";

    private RecyclerView mRecyclerView;
    private TagAdapter mTagAdapter;
    private LinearLayoutManager mLayoutManager;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tags_list_fragment, null);

        v.findViewById(R.id.search_by_tag_btn).setOnClickListener(this);
        v.findViewById(R.id.search_by_location_btn).setOnClickListener(this);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.lvTags);
        setLayoutManagerForRecyclerView();

        List<String> tags = PhotoNewsApp.getApp().getTagDbAdapter().open().getAllTags();
        setAdapterForRecyclerView(tags);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_by_tag_btn: {
                openOperationTag();
            }
            break;

            case R.id.search_by_location_btn: {
                openGoogleMap();
            }
            break;

            default:
                break;
        }
    }

    public void openListPhotoHistoryTag(String lineTag) {
        Bundle bundle = new Bundle();
        bundle.putString(TagsListFragment.TAGS_LIST_KEY, lineTag);
        ((MainActivity) getActivity()).openListPhotoHistoryTagFragment(bundle);
    }

    public void openOperationTag() {
        Bundle bundle = new Bundle();
        bundle.putString(TagsListFragment.TAGS_LIST_KEY, null);
        ((MainActivity) getActivity()).openOperationTagFragment(bundle);
    }

    public void openGoogleMap() {
        Bundle bundle = new Bundle();
        bundle.putString(OperationTagFragment.OPERATION_KEY, null);
        ((MainActivity) getActivity()).openGoogleMapFragment(bundle);
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
