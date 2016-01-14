package com.example.makarov.photonews.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DataBaseHelper extends SQLiteOpenHelper implements BaseColumns {

    private static final String DATABASE_NAME = "data_base_photo_news";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_TAGS = "tags";

    public static final String TAG_NAME_COLUMN = "tag_name";
    public static final String DATE_ADD_TAG_COLUMN = "date_add_tag";

    public static final String TABLE_LOCATIONS = "locations";

    public static final String COUNTRY_NAME_COLUMN = "country_name";
    public static final String LOCALITY_COLUMN = "locality";
    public static final String THOROUGHFARE_COLUMN = "thoroughfare";
    public static final String DATE_ADD_LOCATION_COLUMN = "date_add_location";
    public static final String LATITUDE_COLUMN = "latitude";
    public static final String LONGITUDE_COLUMN = "longitude";

    private static final String DATABASE_CREATE_SCRIPT_TAGS = "create table "
            + TABLE_TAGS + " (" + BaseColumns._ID
            + " integer primary key autoincrement, " + TAG_NAME_COLUMN
            + " text not null unique, " + DATE_ADD_TAG_COLUMN + " integer" + ");";

    private static final String DATABASE_CREATE_SCRIPT_LOCATIONS = "create table "
            + TABLE_LOCATIONS + " (" + BaseColumns._ID
            + " integer primary key autoincrement, " + LOCALITY_COLUMN
            + " text, " + COUNTRY_NAME_COLUMN + " text, "
            + THOROUGHFARE_COLUMN + " text, " + DATE_ADD_LOCATION_COLUMN + " integer,"
            + LATITUDE_COLUMN + " integer," + LONGITUDE_COLUMN + " integer" + ");";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_SCRIPT_TAGS);
        db.execSQL(DATABASE_CREATE_SCRIPT_LOCATIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF IT EXISTS " + TABLE_TAGS);
        db.execSQL("DROP TABLE IF IT EXISTS " + TABLE_LOCATIONS);
        onCreate(db);
    }
}
