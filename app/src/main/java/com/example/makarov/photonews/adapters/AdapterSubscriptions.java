package com.example.makarov.photonews.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.makarov.photonews.PhotoNewsApp;
import com.example.makarov.photonews.R;
import com.example.makarov.photonews.models.Address;
import com.example.makarov.photonews.models.Subscription;
import com.example.makarov.photonews.models.Tag;

import java.util.ArrayList;
import java.util.List;

public class AdapterSubscriptions
        extends RecyclerView.Adapter<AdapterSubscriptions.SubscriptionViewHolder> {

    private final int TYPE_HOLDER_TAG = 0;
    private final int TYPE_HOLDER_LOCATION = 1;

    private List<Subscription> mSubscriptions = new ArrayList<>();
    private OnClickOpenPhotoNews mOnClickOpenPhotoNews;

    public AdapterSubscriptions(List<Subscription> subscriptions,
                                OnClickOpenPhotoNews onClickOpenPhotoNews) {
        mSubscriptions = subscriptions;
        mOnClickOpenPhotoNews = onClickOpenPhotoNews;
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

        holder.setDataOnView(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnClickOpenPhotoNews.onClick(getItem(position));
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

    public abstract class SubscriptionViewHolder extends RecyclerView.ViewHolder {
        public SubscriptionViewHolder(View itemView) {
            super(itemView);
        }

        public abstract void setDataOnView(int position);
    }

    public class TagViewHolder extends SubscriptionViewHolder implements View.OnClickListener {

        private TextView mNameTag;
        private Tag mTag;

        public TagViewHolder(View v) {
            super(v);

            mNameTag = (TextView) v.findViewById(R.id.name_tag);
            v.findViewById(R.id.delete_tag_btn).setOnClickListener(this);
        }

        @Override
        public void setDataOnView(int position) {
            mTag = (Tag) mSubscriptions.get(position);

            this.mNameTag.setText(mTag.getNameTag());
        }

        private void deleteTag() {
            PhotoNewsApp.getApp().getTagDbAdapter().open().deleteTag(mTag);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.delete_tag_btn: {
                    deleteTag();
                    PhotoNewsApp.getApp().getTagDbAdapter().close();
                }
                default:
                    break;
            }
        }
    }

    public class LocationViewHolder extends SubscriptionViewHolder implements View.OnClickListener {

        private TextView mNameLocation;
        private Address mAddress;

        public LocationViewHolder(View v) {
            super(v);

            mNameLocation = (TextView) v.findViewById(R.id.name_location);
            v.findViewById(R.id.delete_location_btn).setOnClickListener(this);
            v.findViewById(R.id.change_name_location_btn).setOnClickListener(this);
        }

        @Override
        public void setDataOnView(int position) {
            mAddress = (Address) mSubscriptions.get(position);

            this.mNameLocation.setText(mAddress.getCountryName() + ", "
                    + mAddress.getLocality() + ", " + mAddress.getThoroughfare());
        }

        private boolean deleteLocation() {
            return PhotoNewsApp.getApp().getLocationDbAdapter().open().deleteLocation(mAddress);
        }

        private void changeNameLocation() {
            //return PhotoNewsApp.getApp().getLocationDbAdapter().open().updateLocation(mAddress);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.delete_location_btn: {
                    deleteLocation();
                    PhotoNewsApp.getApp().getLocationDbAdapter().close();
                }
                break;

                case R.id.change_name_location_btn: {
                    changeNameLocation();
                    PhotoNewsApp.getApp().getLocationDbAdapter().close();
                }
                break;

                default:
                    break;
            }
        }
    }

    public interface OnClickOpenPhotoNews {
        void onClick(Subscription tempClickItem);
    }

}
