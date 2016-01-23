package com.example.makarov.photonews.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.makarov.photonews.models.PhotoNewsPost;

import java.util.ArrayList;
import java.util.List;

public class PhotoNewsDbAdapter {
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private DataBaseHelper mDbHelper;

    final String TAG = "myLogs";

    public PhotoNewsDbAdapter(Context context) {
        mContext = context;
    }

    public PhotoNewsDbAdapter open() throws SQLException {
        mDbHelper = new DataBaseHelper(mContext);
        mDatabase = mDbHelper.getReadableDatabase();

        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public long add(PhotoNewsPost photoNews) {
        ContentValues initialValues = createContentValues(photoNews);

        return mDatabase.insert(DataBaseHelper.TABLE_PHOTO_NEWS, null, initialValues);
    }

    public boolean update(PhotoNewsPost photoNews) {
        ContentValues updateValues = createContentValues(photoNews);

        return mDatabase.update(DataBaseHelper.TABLE_PHOTO_NEWS, updateValues,
                DataBaseHelper.URL_ADDRESS_COLUMN + " = ? ",
                new String[]{photoNews.getUrlAddress()}) > 0;
    }

    public boolean delete(PhotoNewsPost photoNews) {
        return mDatabase.delete(DataBaseHelper.TABLE_PHOTO_NEWS,
                DataBaseHelper.URL_ADDRESS_COLUMN + " = ? ",
                new String[]{photoNews.getUrlAddress()}) > 0;
    }

    public List<PhotoNewsPost> getAllPhotoNews() {
        Cursor cursor = mDatabase.query(DataBaseHelper.TABLE_PHOTO_NEWS, new String[]{BaseColumns._ID,
                        DataBaseHelper.AUTHOR_COLUMN,
                        DataBaseHelper.URL_ADDRESS_COLUMN,
                        DataBaseHelper.COUNT_LIKES_COLUMN},
                null, null, null, null, null);

        List<PhotoNewsPost> photoNewsPosts = new ArrayList<>();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    photoNewsPosts.add(restore(cursor));

                } while (cursor.moveToNext());
            } else {
                Log.d(TAG, "0 rows");
            }
            cursor.close();
        }
        return photoNewsPosts;
    }

    private String getString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    private int getInt(Cursor cursor, String fieldName) {
        return cursor.getInt(cursor.getColumnIndex(fieldName));
    }

    private PhotoNewsPost restore(Cursor cursor) throws SQLException {

        String author = getString(cursor, DataBaseHelper.AUTHOR_COLUMN);
        String urlAddress = getString(cursor, DataBaseHelper.URL_ADDRESS_COLUMN);
        int countLikes = getInt(cursor, DataBaseHelper.COUNT_LIKES_COLUMN);

        return new PhotoNewsPost(author, urlAddress, countLikes);
    }

    private ContentValues createContentValues(PhotoNewsPost photoNews) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.AUTHOR_COLUMN, photoNews.getAuthor());
        values.put(DataBaseHelper.URL_ADDRESS_COLUMN, photoNews.getUrlAddress());
        values.put(DataBaseHelper.COUNT_LIKES_COLUMN, photoNews.getCountLikes());

        return values;
    }
}
