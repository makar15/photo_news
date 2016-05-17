package com.example.makarov.photonews.adapters;

import android.app.FragmentManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.makarov.photonews.R;
import com.example.makarov.photonews.database.LocationDbAdapter;
import com.example.makarov.photonews.database.TagDbAdapter;
import com.example.makarov.photonews.di.AppInjector;
import com.example.makarov.photonews.models.Location;
import com.example.makarov.photonews.models.Subscription;
import com.example.makarov.photonews.models.Tag;
import com.example.makarov.photonews.ui.dialog.ChangeNameLocationDialog;
import com.example.makarov.photonews.utils.CreateDialogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SubscriptionsAdapter
        extends RecyclerView.Adapter<SubscriptionsAdapter.SubscriptionViewHolder> {

    private final int TYPE_HOLDER_TAG = 0;
    private final int TYPE_HOLDER_LOCATION = 1;

    private final FragmentManager mFragmentManager;
    private final List<Subscription> mSubscriptions = new ArrayList<>();
    private final TagDbAdapter mTagDbAdapter = AppInjector.get().getTagDbAdapter();
    private final LocationDbAdapter mLocationDbAdapter = AppInjector.get().getLocationDbAdapter();

    @Nullable private OnClickSubscriptionListener mOnClickSubscriptionListener;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            if (mOnClickSubscriptionListener != null) {
                mOnClickSubscriptionListener.onClick(getItem(position));
            }
        }
    };

    public interface OnClickSubscriptionListener {
        void onClick(Subscription subscription);
    }

    public SubscriptionsAdapter(FragmentManager manager) {
        mFragmentManager = manager;
    }

    public SubscriptionsAdapter(List<Subscription> subscriptions, FragmentManager manager) {
        mFragmentManager = manager;
        mSubscriptions.addAll(subscriptions);
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof Tag) {
            return TYPE_HOLDER_TAG;
        } else {
            return TYPE_HOLDER_LOCATION;
        }
    }

    @Override
    public SubscriptionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        switch (viewType) {
            case TYPE_HOLDER_TAG: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_tag, parent, false);
                return new TagViewHolder(view);
            }
            case TYPE_HOLDER_LOCATION: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_location, parent, false);
                return new LocationViewHolder(view);
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(SubscriptionViewHolder holder, final int position) {
        holder.fillView(position);
        holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return mSubscriptions.size();
    }

    private Subscription getItem(int position) {
        return mSubscriptions.get(position);
    }

    public void setOnClickOpenMediaPosts(OnClickSubscriptionListener listener) {
        mOnClickSubscriptionListener = listener;
    }

    public void update(List<Subscription> subscriptions) {
        mSubscriptions.clear();
        mSubscriptions.addAll(subscriptions);
        notifyDataSetChanged();
    }

    public abstract class SubscriptionViewHolder extends RecyclerView.ViewHolder {

        public SubscriptionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public abstract void fillView(int position);
    }

    public class TagViewHolder extends SubscriptionViewHolder {

        @Bind(R.id.name_tag)
        TextView mNameTag;
        @Bind(R.id.delete_tag_btn)
        Button mDeleteTag;

        private Tag mTag;

        private final View.OnClickListener mOnClickDeleteTagListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTagDbAdapter.open().delete(mTag)) {
                    mSubscriptions.remove(mTag);
                    notifyDataSetChanged();
                }
                mTagDbAdapter.close();
            }
        };

        public TagViewHolder(View v) {
            super(v);
            mDeleteTag.setOnClickListener(mOnClickDeleteTagListener);
        }

        @Override
        public void fillView(int position) {
            itemView.setTag(position);

            mTag = (Tag) getItem(position);
            mNameTag.setText(mTag.getName());
        }
    }

    public class LocationViewHolder extends SubscriptionViewHolder {

        @Bind(R.id.name_location)
        TextView mNameLocation;
        @Bind(R.id.delete_location_btn)
        Button mDeleteLocation;
        @Bind(R.id.change_name_location_btn)
        Button mChangeNameLocation;

        private Location mLocation;

        private final View.OnClickListener mOnClickDeleteLocationListener =
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mLocationDbAdapter.open().delete(mLocation)) {
                            mSubscriptions.remove(mLocation);
                            notifyDataSetChanged();
                        }
                        mLocationDbAdapter.close();
                    }
                };

        private final View.OnClickListener mOnClickChangeNameLocationListener =
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CreateDialogUtils createDialog = new CreateDialogUtils(mFragmentManager);
                        createDialog.createDialog(new ChangeNameLocationDialog
                                (mLocation, SubscriptionsAdapter.this));
                    }
                };

        public LocationViewHolder(View v) {
            super(v);
            mDeleteLocation.setOnClickListener(mOnClickDeleteLocationListener);
            mChangeNameLocation.setOnClickListener(mOnClickChangeNameLocationListener);
        }

        @Override
        public void fillView(int position) {
            itemView.setTag(position);

            mLocation = (Location) getItem(position);
            mNameLocation.setText(mLocation.getName());
        }
    }
}
