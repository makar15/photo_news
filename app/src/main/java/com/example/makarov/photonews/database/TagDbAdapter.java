package com.example.makarov.photonews.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class TagDbAdapter {

    private Context mContext;
    private SQLiteDatabase mDatabase;
    private DataBaseHelper mDbHelper;

    final String LOG_TAG = "myLogs";

    public TagDbAdapter(Context context) {
        mContext = context;
    }

    public TagDbAdapter open() throws SQLException {
        mDbHelper = new DataBaseHelper(mContext);
        mDatabase = mDbHelper.getReadableDatabase();

        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public long createTag(String tagName) {
        ContentValues initialValues = createContentValues(tagName);

        return mDatabase.insert(DataBaseHelper.TABLE_TAGS, null, initialValues);
    }

    public boolean updateTag(long rowId, String tagName) {
        ContentValues updateValues = createContentValues(tagName);

        return mDatabase.update(DataBaseHelper.TABLE_TAGS, updateValues, BaseColumns._ID + "="
                + rowId, null) > 0;
    }

    public boolean deleteTag(long rowId) {
        return mDatabase.
                delete(DataBaseHelper.TABLE_TAGS, BaseColumns._ID + "=" + rowId, null) > 0;
    }

    public List<String> getAllTags() {
        Cursor cursor = mDatabase.query(DataBaseHelper.TABLE_TAGS, new String[]{BaseColumns._ID,
                DataBaseHelper.TAG_NAME_COLUMN}, null, null, null, null, null);

        List<String> tags = new ArrayList<>();

        if (cursor != null) {
            if (cursor.moveToFirst()) {

                int nameTagColumnIndex = cursor.getColumnIndex(DataBaseHelper.TAG_NAME_COLUMN);
                do {
                    tags.add(cursor.getString(nameTagColumnIndex));
                } while (cursor.moveToNext());
            } else {
                Log.d(LOG_TAG, "0 rows");
            }
            cursor.close();
        }

        return tags;

    }

    public String getTag(long rowId) throws SQLException {
        Cursor cursor = mDatabase.query(true, DataBaseHelper.TABLE_TAGS,
                new String[]{BaseColumns._ID}, BaseColumns._ID + "=" + rowId,
                null, null, null, null, null);

        String tag = null;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                tag = cursor.getString(cursor.getColumnIndex(DataBaseHelper.TAG_NAME_COLUMN));
            } else {
                Log.d(LOG_TAG, "0 rows");
            }
        }

        return tag;
    }

    private ContentValues createContentValues(String tagName) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.TAG_NAME_COLUMN, tagName);

        return values;
    }
}
