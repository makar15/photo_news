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

    public long addLocation(Address address) {
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

    public List<Address> getAllLocations() {
        Cursor cursor = mDatabase.query(DataBaseHelper.TABLE_LOCATIONS, new String[]{BaseColumns._ID,
                        DataBaseHelper.COUNTRY_NAME_COLUMN, DataBaseHelper.LOCALITY_COLUMN,
                        DataBaseHelper.THOROUGHFARE_COLUMN, DataBaseHelper.DATE_ADD_LOCATION_COLUMN,
                        DataBaseHelper.LATITUDE_COLUMN, DataBaseHelper.LONGITUDE_COLUMN},
                null, null, null, null, null);

        List<Address> locations = new ArrayList<>();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    locations.add(getLocation(cursor));

                } while (cursor.moveToNext());
            } else {
                Log.d(LOG_TAG, "0 rows");
            }
            cursor.close();
        }
        return locations;
    }

    public Address getLocation(Cursor cursor) {

        String countryName = cursor.getString
                (cursor.getColumnIndex(DataBaseHelper.COUNTRY_NAME_COLUMN));
        String locality = cursor.getString
                (cursor.getColumnIndex(DataBaseHelper.LOCALITY_COLUMN));
        String thoroughfare = cursor.getString
                (cursor.getColumnIndex(DataBaseHelper.THOROUGHFARE_COLUMN));
        long date = cursor.getLong
                (cursor.getColumnIndex(DataBaseHelper.DATE_ADD_LOCATION_COLUMN));
        double latitude = cursor.getDouble
                (cursor.getColumnIndex(DataBaseHelper.LATITUDE_COLUMN));
        double longitude = cursor.getDouble
                (cursor.getColumnIndex(DataBaseHelper.LONGITUDE_COLUMN));

        return new Address(latitude, longitude,
                countryName, locality, thoroughfare, date);
    }

    private ContentValues createContentValues(Address address) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.COUNTRY_NAME_COLUMN, address.getCountryName());
        values.put(DataBaseHelper.LOCALITY_COLUMN, address.getLocality());
        values.put(DataBaseHelper.THOROUGHFARE_COLUMN, address.getThoroughfare());
        values.put(DataBaseHelper.DATE_ADD_LOCATION_COLUMN, address.getDate());
        values.put(DataBaseHelper.LATITUDE_COLUMN, address.getLatitude());
        values.put(DataBaseHelper.LONGITUDE_COLUMN, address.getLongitude());

        return values;
    }
}
