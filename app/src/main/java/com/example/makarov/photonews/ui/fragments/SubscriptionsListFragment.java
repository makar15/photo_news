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
import com.example.makarov.photonews.models.Address;
import com.example.makarov.photonews.models.Subscription;
import com.example.makarov.photonews.models.Tag;
import com.example.makarov.photonews.ui.activity.MainActivity;
import com.example.makarov.photonews.utils.sorting.FastSort;

import java.util.ArrayList;
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

        List<Subscription> dbSubscription = getListDbSubscription();

        if (dbSubscription != null) {
            setAdapterForRecyclerView(getSortedListDbSubscription(dbSubscription));
        }

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

    private void openListPhotoResultTag(Tag tag) {
        Bundle bundle = new Bundle();
        bundle.putString(ListPhotoResultTagFragment.PHOTO_RESULT_TAG_KEY, tag.getNameTag());
        ((MainActivity) getActivity()).openListPhotoResultTagFragment(bundle);
    }

    private void openListPhotoResultLocation(com.example.makarov.photonews.models.Address address) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(GoogleMapFragment.GOOGLE_MAP_KEY, address);
        ((MainActivity) getActivity()).openListPhotoResultLocationFragment(bundle);
    }

    private void openOperationTag() {
        Bundle bundle = new Bundle();
        bundle.putString(SubscriptionsListFragment.SUBSCRIPTIONS_LIST_KEY, null);
        ((MainActivity) getActivity()).openOperationTagFragment(bundle);
    }

    private void openGoogleMap() {
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

    private void setAdapterForRecyclerView(List<Subscription> subscriptions) {
        AdapterSubscriptions mSubscriptionsAdapter = new AdapterSubscriptions(subscriptions,
                new AdapterSubscriptions.OnClickOpenPhotoNews() {
                    @Override
                    public void onClick(Subscription clickSubscription) {

                        openClickInstanceOfSubscription(clickSubscription);
                    }
                });
        mLvSubscriptions.setAdapter(mSubscriptionsAdapter);
    }

    private void openClickInstanceOfSubscription(Subscription clickSubscription) {
        if (clickSubscription instanceof Tag) {
            openListPhotoResultTag(((Tag) clickSubscription));
        } else if (clickSubscription instanceof Address) {
            openListPhotoResultLocation((Address) clickSubscription);
        }
    }

    private void initializeHistoryRecyclerView() {
        mLvSubscriptions.setHasFixedSize(false);
        mLvSubscriptions.setItemAnimator(new DefaultItemAnimator());
    }

    private List<Subscription> getListDbSubscription() {
        List<Subscription> subscriptions = new ArrayList<>();
        subscriptions.addAll(PhotoNewsApp.getApp().getTagDbAdapter().open().getAllTags());
        subscriptions.addAll(PhotoNewsApp.getApp().getLocationDbAdapter().open().getAllLocations());

        if (subscriptions.isEmpty()) {
            return null;
        } else {
            return subscriptions;
        }
    }

    private List<Subscription> getSortedListDbSubscription(List<Subscription> subscriptions) {

        return FastSort.sort(subscriptions, 0, subscriptions.size() - 1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        PhotoNewsApp.getApp().getTagDbAdapter().close();
        PhotoNewsApp.getApp().getLocationDbAdapter().close();
    }
}
