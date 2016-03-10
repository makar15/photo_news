package com.example.makarov.photonews.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.makarov.photonews.models.MediaPost;

import java.util.ArrayList;
import java.util.List;

public class MediaPostDbAdapter {

    private final static String TAG = MediaPostDbAdapter.class.getSimpleName();

    private final Context mContext;

    private SQLiteDatabase mDatabase;
    private DataBaseHelper mDbHelper;

    public MediaPostDbAdapter(Context context) {
        mContext = context;
    }

    public MediaPostDbAdapter open() throws SQLException {
        mDbHelper = new DataBaseHelper(mContext);
        mDatabase = mDbHelper.getReadableDatabase();

        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public long add(MediaPost mediaPost) {
        ContentValues initialValues = createContentValues(mediaPost);

        return mDatabase.insert(DataBaseHelper.TABLE_MEDIA_POSTS, null, initialValues);
    }

    public boolean update(MediaPost mediaPost) {
        ContentValues updateValues = updateContentValues(mediaPost);

        return mDatabase.update(DataBaseHelper.TABLE_MEDIA_POSTS, updateValues,
                DataBaseHelper.ID_POST_COLUMN + " = ? ",
                new String[]{mediaPost.getId()}) > 0;
    }

    public boolean delete(MediaPost mediaPost) {
        return mDatabase.delete(DataBaseHelper.TABLE_MEDIA_POSTS,
                DataBaseHelper.ID_POST_COLUMN + " = ? ",
                new String[]{mediaPost.getId()}) > 0;
    }

    public List<MediaPost> getAllMediaPosts() {
        Cursor cursor = mDatabase.query(DataBaseHelper.TABLE_MEDIA_POSTS, new String[]{
                        BaseColumns._ID,
                        DataBaseHelper.ID_POST_COLUMN,
                        DataBaseHelper.AUTHOR_COLUMN,
                        DataBaseHelper.URL_ADDRESS_COLUMN,
                        DataBaseHelper.COUNT_LIKES_COLUMN},
                null, null, null, null, null);

        return cursorToMediaPosts(cursor);
    }

    //пока что хз как лучше сделать((
    public List<MediaPost> getMediaPostsById(String ID) {
        Cursor cursor = mDatabase.query(DataBaseHelper.TABLE_MEDIA_POSTS, new String[]{
                        BaseColumns._ID,
                        DataBaseHelper.ID_POST_COLUMN,
                        DataBaseHelper.AUTHOR_COLUMN,
                        DataBaseHelper.URL_ADDRESS_COLUMN,
                        DataBaseHelper.COUNT_LIKES_COLUMN},
                BaseColumns._ID + " <= ? ", new String[] {ID},
                null, null, null);

        return cursorToMediaPosts(cursor);
    }

    private List<MediaPost> cursorToMediaPosts(Cursor cursor) {
        List<MediaPost> mediaPosts = new ArrayList<>();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    mediaPosts.add(restore(cursor));

                } while (cursor.moveToNext());
            } else {
                Log.d(TAG, "0 rows");
            }
            cursor.close();
        }
        return mediaPosts;
    }

    private MediaPost restore(Cursor cursor) throws SQLException {

        String idMediaPost = SQLiteUtils.getString(cursor, DataBaseHelper.ID_POST_COLUMN);
        String author = SQLiteUtils.getString(cursor, DataBaseHelper.AUTHOR_COLUMN);
        String urlAddress = SQLiteUtils.getString(cursor, DataBaseHelper.URL_ADDRESS_COLUMN);
        int countLikes = SQLiteUtils.getInt(cursor, DataBaseHelper.COUNT_LIKES_COLUMN);

        return new MediaPost(idMediaPost, author, urlAddress, countLikes);
    }

    private ContentValues createContentValues(MediaPost mediaPost) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.ID_POST_COLUMN, mediaPost.getId());
        values.put(DataBaseHelper.AUTHOR_COLUMN, mediaPost.getAuthor());
        values.put(DataBaseHelper.URL_ADDRESS_COLUMN, mediaPost.getUrlAddress());
        values.put(DataBaseHelper.COUNT_LIKES_COLUMN, mediaPost.getCountLikes());

        return values;
    }

    private ContentValues updateContentValues(MediaPost mediaPost) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.AUTHOR_COLUMN, mediaPost.getAuthor());
        values.put(DataBaseHelper.COUNT_LIKES_COLUMN, mediaPost.getCountLikes());

        return values;
    }
}
