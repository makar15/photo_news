package com.example.makarov.photonews.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.makarov.photonews.models.Tag;

import java.util.ArrayList;
import java.util.List;

public class TagDbAdapter {

    private final static String TAG = TagDbAdapter.class.getSimpleName();

    private final Context mContext;

    private SQLiteDatabase mDatabase;
    private DataBaseHelper mDbHelper;

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

    public long add(Tag tag) {
        ContentValues initialValues = createContentValues(tag);

        return mDatabase.insert(DataBaseHelper.TABLE_TAGS, null, initialValues);
    }

    public boolean update(Tag tag) {
        ContentValues updateValues = createContentValues(tag);

        return mDatabase.update(DataBaseHelper.TABLE_TAGS, updateValues,
                DataBaseHelper.DATE_ADD_TAG_COLUMN + " = ? ",
                new String[]{Long.toString(tag.getDate())}) > 0;
    }

    public boolean delete(Tag tag) {
        return mDatabase.delete(DataBaseHelper.TABLE_TAGS,
                DataBaseHelper.DATE_ADD_TAG_COLUMN + " = ? ",
                new String[]{Long.toString(tag.getDate())}) > 0;
    }

    public List<Tag> getAllTags() {
        Cursor cursor = mDatabase.query(DataBaseHelper.TABLE_TAGS, new String[]{
                        BaseColumns._ID,
                        DataBaseHelper.TAG_NAME_COLUMN,
                        DataBaseHelper.DATE_ADD_TAG_COLUMN},
                null, null, null, null, null);

        return cursorToTags(cursor);
    }

    private List<Tag> cursorToTags(Cursor cursor) {
        List<Tag> tags = new ArrayList<>();

        if (cursor == null) {
            return tags;
        }
        if (cursor.moveToFirst()) {
            Log.d(TAG, "0 rows");
            cursor.close();
            return tags;
        }
        do {
            tags.add(restore(cursor));
        } while (cursor.moveToNext());

        cursor.close();
        return tags;
    }

    private Tag restore(Cursor cursor) throws SQLException {
        String tagName = SQLiteUtils.getString(cursor, DataBaseHelper.TAG_NAME_COLUMN);
        long date = SQLiteUtils.getLong(cursor, DataBaseHelper.DATE_ADD_TAG_COLUMN);

        return new Tag(tagName, date);
    }

    private ContentValues createContentValues(Tag tag) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.TAG_NAME_COLUMN, tag.getName());
        values.put(DataBaseHelper.DATE_ADD_TAG_COLUMN, tag.getDate());

        return values;
    }
}
