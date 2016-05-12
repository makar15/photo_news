package com.example.makarov.photonews.adapters;

import android.app.FragmentManager;
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

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AdapterSubscriptions
        extends RecyclerView.Adapter<AdapterSubscriptions.SubscriptionViewHolder> {

    private final int TYPE_HOLDER_TAG = 0;
    private final int TYPE_HOLDER_LOCATION = 1;

    private final FragmentManager mFragmentManager;
    private final List<Subscription> mSubscriptions;
    private final TagDbAdapter mTagDbAdapter;
    private final LocationDbAdapter mLocationDbAdapter;

    private OnClickOpenMediaPosts mOnClickOpenMediaPosts;

    public AdapterSubscriptions(List<Subscription> subscriptions, FragmentManager manager) {
        mFragmentManager = manager;
        mSubscriptions = subscriptions;
        mTagDbAdapter = AppInjector.get().getTagDbAdapter();
        mLocationDbAdapter = AppInjector.get().getLocationDbAdapter();
    }

    @Override
    public int getItemViewType(int position) {
        if (mSubscriptions.get(position) instanceof Tag) {
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnClickOpenMediaPosts.onClick(getItem(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSubscriptions.size();
    }

    private Subscription getItem(int position) {
        return mSubscriptions.get(position);
    }

    public void setOnClickOpenMediaPosts(OnClickOpenMediaPosts onClickOpenMediaPosts) {
        mOnClickOpenMediaPosts = onClickOpenMediaPosts;
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

        public TagViewHolder(View v) {
            super(v);

            mDeleteTag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteTag();
                    mTagDbAdapter.close();
                }
            });
        }

        @Override
        public void fillView(int position) {
            mTag = (Tag) mSubscriptions.get(position);
            mNameTag.setText(mTag.getName());
        }

        private boolean deleteTag() {
            mSubscriptions.remove(mTag);
            AdapterSubscriptions.this.notifyDataSetChanged();
            return mTagDbAdapter.open().delete(mTag);
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

        public LocationViewHolder(View v) {
            super(v);

            mDeleteLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteLocation();
                    mLocationDbAdapter.close();
                }
            });
            mChangeNameLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openDialogChangeNameLocation(mLocation);
                }
            });
        }

        @Override
        public void fillView(int position) {
            mLocation = (Location) mSubscriptions.get(position);
            mNameLocation.setText(mLocation.getName());
        }

        private boolean deleteLocation() {
            mSubscriptions.remove(mLocation);
            AdapterSubscriptions.this.notifyDataSetChanged();
            return mLocationDbAdapter.open().delete(mLocation);
        }

        private void openDialogChangeNameLocation(Location location) {
            CreateDialogUtils createDialog = new CreateDialogUtils(mFragmentManager);
            createDialog.createDialog(new ChangeNameLocationDialog
                    (location, AdapterSubscriptions.this));
        }
    }

    public interface OnClickOpenMediaPosts {
        void onClick(Subscription tempClickItem);
    }

}
