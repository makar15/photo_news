package com.example.makarov.photonews.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.makarov.photonews.R;
import com.example.makarov.photonews.database.MediaPostDbAdapter;
import com.example.makarov.photonews.di.AppInjector;
import com.example.makarov.photonews.models.MediaPost;
import com.example.makarov.photonews.utils.PicassoBigCache;
import com.makeramen.roundedimageview.RoundedImageView;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * generates a list of photos, with updating
 */
public class MediaPostsAdapter extends RecyclerView.Adapter<MediaPostsAdapter.ResultViewHolder> {

    private final int TYPE_HOLDER_MEDIA_POST = 0;
    private final int TYPE_HOLDER_SAVE_MEDIA_POST = 1;

    private final List<MediaPost> mMediaPosts = new ArrayList<>();
    private final MediaPostDbAdapter mMediaPostDbAdapter = AppInjector.get().getMediaPostDbAdapter();

    public MediaPostsAdapter() {

    }

    public MediaPostsAdapter(List<MediaPost> mediaPosts) {
        mMediaPosts.addAll(mediaPosts);
    }

    @Override
    public ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_media_post, parent, false);

        return new ResultViewHolder(view, mMediaPostDbAdapter);
    }

    @Override
    public void onBindViewHolder(ResultViewHolder holder, final int position) {
        holder.fillView(getItem(position));
    }

    @Override
    public int getItemCount() {
        return mMediaPosts.size();
    }

    private MediaPost getItem(int position) {
        return mMediaPosts.get(position);
    }

    public void update(List<MediaPost> newMediaPosts) {
        for (MediaPost post : newMediaPosts) {
            addItem(post);
        }
        notifyDataSetChanged();
    }

    public void addItem(MediaPost mediaPost) {
        mMediaPosts.add(mediaPost);
    }

    public static class ResultViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.profile_picture)
        RoundedImageView mProfilePicture;
        @Bind(R.id.icon)
        ImageView mIcon;
        @Bind(R.id.like)
        ImageView mLike;
        @Bind(R.id.author)
        TextView mAuthor;
        @Bind(R.id.count_likes)
        TextView mCountLikes;
        @Bind(R.id.media_post_btn)
        FloatingActionButton mOperationMediaPost;

        private final MediaPostDbAdapter mMediaPostDbAdapter;

        private MediaPost mMediaPost;

        private final View.OnClickListener mOnClickOperationMediaPostListener =
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMediaPostDbAdapter.open().add(mMediaPost);
                        mMediaPostDbAdapter.close();
                    }
                };

        public ResultViewHolder(View v, MediaPostDbAdapter mediaPostDbAdapter) {
            super(v);
            ButterKnife.bind(this, v);
            mMediaPostDbAdapter = mediaPostDbAdapter;
        }

        public void fillView(final MediaPost post) {
            mMediaPost = post;

            PicassoBigCache.INSTANCE.getPicassoBigCache(itemView.getContext())
                    .load(mMediaPost.getProfilePicture())
                    .into(mProfilePicture);
            PicassoBigCache.INSTANCE.getPicassoBigCache(itemView.getContext())
                    .load(mMediaPost.getUrlAddress())
                    .into(mIcon);

            mAuthor.setText(mMediaPost.getAuthor());
            mCountLikes.setText(String.valueOf(mMediaPost.getCountLikes()));
            mOperationMediaPost.setOnClickListener(mOnClickOperationMediaPostListener);
        }
    }

}

