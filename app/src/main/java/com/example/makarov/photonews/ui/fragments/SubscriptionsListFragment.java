package com.example.makarov.photonews.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.makarov.photonews.R;
import com.example.makarov.photonews.adapters.SubscriptionsAdapter;
import com.example.makarov.photonews.database.LocationDbAdapter;
import com.example.makarov.photonews.database.TagDbAdapter;
import com.example.makarov.photonews.di.AppInjector;
import com.example.makarov.photonews.models.Location;
import com.example.makarov.photonews.models.Subscription;
import com.example.makarov.photonews.models.Tag;
import com.example.makarov.photonews.ui.activity.MainActivity;
import com.example.makarov.photonews.utils.FastSort;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SubscriptionsListFragment extends Fragment {

    @Bind(R.id.lv_subscriptions)
    RecyclerView mLvSubscriptions;
    @Bind(R.id.search_by_location_btn)
    FloatingActionButton mSearchByLocation;
    @Bind(R.id.search_by_tag_btn)
    FloatingActionButton mSearchByTag;
    @Bind(R.id.open_media_posts_btn)
    Button mOpenMediaPosts;

    @Inject
    TagDbAdapter mTagDbAdapter;
    @Inject
    LocationDbAdapter mLocationDbAdapter;

    private final SubscriptionsAdapter.OnClickSubscriptionListener mOnClickSubscriptionListener =
            new SubscriptionsAdapter.OnClickSubscriptionListener() {
                @Override
                public void onClick(Subscription subscription) {
                    openClickInstanceOfSubscription(subscription);
                }
            };

    private final View.OnClickListener mOnClickSearchByLocationListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) getActivity()).openGoogleMapFragment(null);
                }
            };

    private final View.OnClickListener mOnClickSearchByTagListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((MainActivity) getActivity()).openOperationTagFragment(null);
        }
    };

    private final View.OnClickListener mOnClickOpenPostListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((MainActivity) getActivity()).openListSaveMediaPostsFragment(null);
        }
    };

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.subscriptions_list_fragment, null);
        ButterKnife.bind(this, v);
        AppInjector.get().inject(this);
        setLayoutManager();
        setAdapter(getSortedSubscriptionsDb(getSubscriptionsDb()));

        mSearchByLocation.setOnClickListener(mOnClickSearchByLocationListener);
        mSearchByTag.setOnClickListener(mOnClickSearchByTagListener);
        mOpenMediaPosts.setOnClickListener(mOnClickOpenPostListener);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //initializeHistoryRecyclerView
        mLvSubscriptions.setHasFixedSize(false);
        mLvSubscriptions.setItemAnimator(new DefaultItemAnimator());
    }

    private void setLayoutManager() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLvSubscriptions.setLayoutManager(layoutManager);
    }

    private void setAdapter(List<Subscription> subscriptions) {
        SubscriptionsAdapter subscriptionsAdapter = new SubscriptionsAdapter(subscriptions,
                getActivity().getFragmentManager());

        subscriptionsAdapter.setOnClickOpenMediaPosts(mOnClickSubscriptionListener);
        mLvSubscriptions.setAdapter(subscriptionsAdapter);
    }

    private void openClickInstanceOfSubscription(Subscription subscription) {
        if (subscription instanceof Tag) {
            openListPhotoResultTag((Tag) subscription);
        } else if (subscription instanceof Location) {
            openListPhotoResultLocation((Location) subscription);
        }
    }

    private void openListPhotoResultTag(Tag tag) {
        Bundle bundle = new Bundle();
        bundle.putString(PhotoFragment.TAG_KEY, tag.getName());
        ((MainActivity) getActivity()).openListResultTagFragment(bundle);
    }

    private void openListPhotoResultLocation(Location location) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(PhotoFragment.LOCATION_KEY, location);
        ((MainActivity) getActivity()).openListResultLocationFragment(bundle);
    }

    private List<Subscription> getSubscriptionsDb() {
        List<Subscription> subscriptions = new ArrayList<>();
        subscriptions.addAll(mTagDbAdapter.open().getAllTags());
        subscriptions.addAll(mLocationDbAdapter.open().getAllLocations());
        mTagDbAdapter.close();
        mLocationDbAdapter.close();
        return subscriptions;
    }

    private List<Subscription> getSortedSubscriptionsDb(List<Subscription> subscriptions) {
        return FastSort.sort(subscriptions, 0, subscriptions.size() - 1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
