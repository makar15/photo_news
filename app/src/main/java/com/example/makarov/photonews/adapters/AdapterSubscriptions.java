package com.example.makarov.photonews.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    public Subscription getItem(int position) {
        return mSubscriptions.get(position);
    }

    public abstract class SubscriptionViewHolder extends RecyclerView.ViewHolder {
        public SubscriptionViewHolder(View itemView) {
            super(itemView);
        }

        public abstract void setDataOnView(int position);
    }

    public class TagViewHolder extends SubscriptionViewHolder {

        public TextView mNameTag;

        public TagViewHolder(View v) {
            super(v);

            mNameTag = (TextView) v.findViewById(R.id.name_tag);
        }

        @Override
        public void setDataOnView(int position) {
            Tag tag = (Tag) mSubscriptions.get(position);

            this.mNameTag.setText(tag.getNameTag());
        }
    }

    public class LocationViewHolder extends SubscriptionViewHolder {

        public TextView mNameLocation;

        public LocationViewHolder(View v) {
            super(v);

            mNameLocation = (TextView) v.findViewById(R.id.name_location);
        }

        @Override
        public void setDataOnView(int position) {
            Address address = (Address) mSubscriptions.get(position);

            this.mNameLocation.setText(address.getCountryName() + ", "
                    + address.getLocality() + ", " + address.getThoroughfare());
        }
    }

    public interface OnClickOpenPhotoNews {
        void onClick(Subscription tempClickItem);
    }

}
