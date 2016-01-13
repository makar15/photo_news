package com.example.makarov.photonews.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.makarov.photonews.models.Address;

import java.util.ArrayList;
import java.util.List;

public class LocationDbAdapter {

    private Context mContext;
    private SQLiteDatabase mDatabase;
    private DataBaseHelper mDbHelper;

    final String LOG_TAG = "myLogs";

    public LocationDbAdapter(Context context) {
        mContext = context;
    }

    public LocationDbAdapter open() throws SQLException {
        mDbHelper = new DataBaseHelper(mContext);
        mDatabase = mDbHelper.getReadableDatabase();

        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public long createLocation(Address address) {
        ContentValues initialValues = createContentValues(address);

        return mDatabase.insert(DataBaseHelper.TABLE_LOCATIONS, null, initialValues);
    }

    public boolean updateLocation(long rowId, Address address) {
        ContentValues updateValues = createContentValues(address);

        return mDatabase.update(DataBaseHelper.TABLE_LOCATIONS, updateValues, BaseColumns._ID + "="
                + rowId, null) > 0;
    }

    public boolean deleteLocation(long rowId) {
        return mDatabase.
                delete(DataBaseHelper.TABLE_LOCATIONS, BaseColumns._ID + "=" + rowId, null) > 0;
    }

    public List<String> getAllLocations() {
        Cursor cursor = mDatabase.query(DataBaseHelper.TABLE_LOCATIONS, new String[]{BaseColumns._ID,
                DataBaseHelper.LOCATION_NAME_COLUMN}, null, null, null, null, null);

        List<String> locations = new ArrayList<>();

        if (cursor != null) {
            if (cursor.moveToFirst()) {

                int nameLocationColumnIndex = cursor.getColumnIndex(DataBaseHelper.LOCATION_NAME_COLUMN);
                do {
                    locations.add(cursor.getString(nameLocationColumnIndex));
                } while (cursor.moveToNext());
            } else {
                Log.d(LOG_TAG, "0 rows");
            }
            cursor.close();
        }

        return locations;

    }

    public String getLocation(long rowId) throws SQLException {
        Cursor cursor = mDatabase.query(true, DataBaseHelper.TABLE_LOCATIONS,
                new String[]{BaseColumns._ID}, BaseColumns._ID + "=" + rowId,
                null, null, null, null, null);

        String location = null;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                location = cursor.getString(cursor.getColumnIndex(DataBaseHelper.LOCATION_NAME_COLUMN));
            } else {
                Log.d(LOG_TAG, "0 rows");
            }
        }

        return location;
    }

    private ContentValues createContentValues(Address address) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.LOCATION_NAME_COLUMN, address.getLatitude());

        return values;
    }
}
