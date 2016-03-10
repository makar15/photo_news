package com.example.makarov.photonews.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.makarov.photonews.models.Location;

import java.util.ArrayList;
import java.util.List;

public class LocationDbAdapter {

    private final static String TAG = LocationDbAdapter.class.getSimpleName();

    private final Context mContext;

    private SQLiteDatabase mDatabase;
    private DataBaseHelper mDbHelper;

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

    public long add(Location location) {
        ContentValues initialValues = createContentValues(location);

        return mDatabase.insert(DataBaseHelper.TABLE_LOCATIONS, null, initialValues);
    }

    public boolean update(Location location) {
        ContentValues updateValues = updateContentValues(location);

        return mDatabase.update(DataBaseHelper.TABLE_LOCATIONS, updateValues,
                DataBaseHelper.DATE_ADD_LOCATION_COLUMN + " = ? ",
                new String[]{Long.toString(location.getDate())}) > 0;
    }

    public boolean delete(Location location) {
        return mDatabase.delete(DataBaseHelper.TABLE_LOCATIONS,
                DataBaseHelper.DATE_ADD_LOCATION_COLUMN + " = ? ",
                new String[]{Long.toString(location.getDate())}) > 0;
    }

    public List<Location> getAllLocations() {
        Cursor cursor = mDatabase.query(DataBaseHelper.TABLE_LOCATIONS, new String[]{
                        BaseColumns._ID,
                        DataBaseHelper.NAME_LOCATION_COLUMN,
                        DataBaseHelper.COUNTRY_NAME_COLUMN,
                        DataBaseHelper.LOCALITY_COLUMN,
                        DataBaseHelper.THOROUGHFARE_COLUMN,
                        DataBaseHelper.DATE_ADD_LOCATION_COLUMN,
                        DataBaseHelper.LATITUDE_COLUMN,
                        DataBaseHelper.LONGITUDE_COLUMN,
                        DataBaseHelper.RADIUS_SEARCH_COLUMN},
                null, null, null, null, null);

        return cursorToLocations(cursor);
    }

    private List<Location> cursorToLocations(Cursor cursor) {
        List<Location> locations = new ArrayList<>();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    locations.add(restore(cursor));

                } while (cursor.moveToNext());
            } else {
                Log.d(TAG, "0 rows");
            }
            cursor.close();
        }
        return locations;
    }

    private Location restore(Cursor cursor) {

        String nameLocation = SQLiteUtils.getString(cursor, DataBaseHelper.NAME_LOCATION_COLUMN);
        String countryName = SQLiteUtils.getString(cursor, DataBaseHelper.COUNTRY_NAME_COLUMN);
        String locality = SQLiteUtils.getString(cursor, DataBaseHelper.LOCALITY_COLUMN);
        String thoroughfare = SQLiteUtils.getString(cursor, DataBaseHelper.THOROUGHFARE_COLUMN);
        long date = SQLiteUtils.getLong(cursor, DataBaseHelper.DATE_ADD_LOCATION_COLUMN);
        double latitude = SQLiteUtils.getDouble(cursor, DataBaseHelper.LATITUDE_COLUMN);
        double longitude = SQLiteUtils.getDouble(cursor, DataBaseHelper.LONGITUDE_COLUMN);
        int radiusSearch = SQLiteUtils.getInt(cursor, DataBaseHelper.RADIUS_SEARCH_COLUMN);

        return new Location(nameLocation, latitude, longitude,
                countryName, locality, thoroughfare, date, radiusSearch);
    }

    private ContentValues createContentValues(Location location) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.NAME_LOCATION_COLUMN, location.getName());
        values.put(DataBaseHelper.COUNTRY_NAME_COLUMN, location.getCountryName());
        values.put(DataBaseHelper.LOCALITY_COLUMN, location.getLocality());
        values.put(DataBaseHelper.THOROUGHFARE_COLUMN, location.getThoroughfare());
        values.put(DataBaseHelper.DATE_ADD_LOCATION_COLUMN, location.getDate());
        values.put(DataBaseHelper.LATITUDE_COLUMN, location.getLatitude());
        values.put(DataBaseHelper.LONGITUDE_COLUMN, location.getLongitude());
        values.put(DataBaseHelper.RADIUS_SEARCH_COLUMN, location.getRadiusSearch());

        return values;
    }

    private ContentValues updateContentValues(Location location) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.NAME_LOCATION_COLUMN, location.getName());

        return values;
    }
}
