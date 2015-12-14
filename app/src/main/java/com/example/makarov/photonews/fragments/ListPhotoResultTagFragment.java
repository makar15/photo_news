package com.example.makarov.photonews.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.makarov.photonews.MyApp;
import com.example.makarov.photonews.R;
import com.example.makarov.photonews.adapters.PhotoResultTagAdapter;
import com.example.makarov.photonews.adapters.scrolling.EndlessRecyclerOnScrollListener;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

/**
 * Created by makarov on 08.12.15.
 */
public class ListPhotoResultTagFragment extends Fragment {

    private PhotoResultTagAdapter photoAdapter;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;

    private List<String> mUrlPhotos;
    private String mLineTag;

    public ListPhotoResultTagFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_photo_result_tag_fragment, null);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.lvPhotoResultTag);
        mLineTag = getArgumentsBundleLineTag(getArguments());

        try {
            MyApp.getApp().getRequestService().requestPhotosTag(mLineTag);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mUrlPhotos = MyApp.getApp().getRequestService().getUrlImages();

        setLayoutManagerForRecyclerView();
        setAdapterForRecyclerView();

        mRecyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int current_page) throws MalformedURLException {

                MyApp.getApp().getRequestService().nextRequestPhotosTag(mLineTag);
                List<String> addUrlList = MyApp.getApp().getRequestService().getUrlImages();

                for (int i = 0; i < addUrlList.size(); i++) {
                    mUrlPhotos.add(addUrlList.get(i));
                }

                photoAdapter.notifyDataSetChanged();
            }
        });

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializeHistoryRecyclerView();
    }

    private void setLayoutManagerForRecyclerView(){
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void setAdapterForRecyclerView(){
        photoAdapter = new PhotoResultTagAdapter(mUrlPhotos);
        mRecyclerView.setAdapter(photoAdapter);
    }

    private void initializeHistoryRecyclerView() {
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private String getArgumentsBundleLineTag(Bundle savedInstanceState) {
        if (savedInstanceState == null)
            return null;
        if (!savedInstanceState.containsKey(OperationTagFragment.OPERATION_KEY))
            return null;
        return savedInstanceState.getString(OperationTagFragment.OPERATION_KEY);

    }
}
