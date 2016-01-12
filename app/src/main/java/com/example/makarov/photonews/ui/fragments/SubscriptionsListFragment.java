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
import com.example.makarov.photonews.adapters.AdapterSubscriptions;
import com.example.makarov.photonews.ui.activity.MainActivity;

import java.util.List;

public class SubscriptionsListFragment extends Fragment implements View.OnClickListener {

    public static final String SUBSCRIPTIONS_LIST_KEY = "subscriptions_list";

    private RecyclerView mLvSubscriptions;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.subscriptions_list_fragment, null);

        v.findViewById(R.id.search_by_tag_btn).setOnClickListener(this);
        v.findViewById(R.id.search_by_location_btn).setOnClickListener(this);

        mLvSubscriptions = (RecyclerView) v.findViewById(R.id.lv_subscriptions);
        setLayoutManagerForRecyclerView();

        List<String> photoNews = PhotoNewsApp.getApp().getTagDbAdapter().open().getAllTags();
        setAdapterForRecyclerView(photoNews);

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
        bundle.putString(SubscriptionsListFragment.SUBSCRIPTIONS_LIST_KEY, lineTag);
        ((MainActivity) getActivity()).openListPhotoHistoryTagFragment(bundle);
    }

    public void openOperationTag() {
        Bundle bundle = new Bundle();
        bundle.putString(SubscriptionsListFragment.SUBSCRIPTIONS_LIST_KEY, null);
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
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLvSubscriptions.setLayoutManager(mLayoutManager);
    }

    private void setAdapterForRecyclerView(List<String> tags) {
        AdapterSubscriptions mSubscriptionsAdapter =
                new AdapterSubscriptions(tags, new AdapterSubscriptions.OnClickOpenPhotoNews() {
            @Override
            public void onClick(String clickLineTag) {
                openListPhotoHistoryTag(clickLineTag);
            }
        });
        mLvSubscriptions.setAdapter(mSubscriptionsAdapter);
    }

    private void initializeHistoryRecyclerView() {
        mLvSubscriptions.setHasFixedSize(false);
        mLvSubscriptions.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        PhotoNewsApp.getApp().getTagDbAdapter().close();
    }
}
