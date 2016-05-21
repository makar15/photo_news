package com.example.makarov.photonews.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DataBaseHelper extends SQLiteOpenHelper implements BaseColumns {

    private static final String DATABASE_NAME = "data_base_photo_news";
    private static final int DATABASE_VERSION = 1;

    protected static final String TABLE_TAGS = "tags";
    protected static final String TAG_NAME_COLUMN = "tag_name";
    protected static final String DATE_ADD_TAG_COLUMN = "date_add_tag";

    protected static final String TABLE_LOCATIONS = "locations";
    protected static final String NAME_LOCATION_COLUMN = "name_location";
    protected static final String COUNTRY_NAME_COLUMN = "country_name";
    protected static final String LOCALITY_COLUMN = "locality";
    protected static final String THOROUGHFARE_COLUMN = "thoroughfare";
    protected static final String DATE_ADD_LOCATION_COLUMN = "date_add_location";
    protected static final String LATITUDE_COLUMN = "latitude";
    protected static final String LONGITUDE_COLUMN = "longitude";
    protected static final String RADIUS_SEARCH_COLUMN = "radius_search";

    protected static final String TABLE_MEDIA_POSTS = "media_posts";
    protected static final String ID_POST_COLUMN = "id_post";
    protected static final String AUTHOR_COLUMN = "author";
    protected static final String URL_PROFILE_PICTURE_COLUMN = "profile_picture";
    protected static final String URL_ADDRESS_COLUMN = "url_address";
    protected static final String COUNT_LIKES_COLUMN = "count_likes";

    private static final String DATABASE_CREATE_SCRIPT_TAGS = "create table "
            + TABLE_TAGS + " (" + BaseColumns._ID + " integer primary key autoincrement, "
            + TAG_NAME_COLUMN + " text not null unique, "
            + DATE_ADD_TAG_COLUMN + " integer" + ");";

    private static final String DATABASE_CREATE_SCRIPT_LOCATIONS = "create table "
            + TABLE_LOCATIONS + " (" + BaseColumns._ID + " integer primary key autoincrement, "
            + NAME_LOCATION_COLUMN + " text, "
            + LOCALITY_COLUMN + " text, "
            + COUNTRY_NAME_COLUMN + " text, "
            + THOROUGHFARE_COLUMN + " text, "
            + DATE_ADD_LOCATION_COLUMN + " integer,"
            + LATITUDE_COLUMN + " integer not null unique,"
            + LONGITUDE_COLUMN + " integer not null unique,"
            + RADIUS_SEARCH_COLUMN + " integer not null" + ");";

    private static final String DATABASE_CREATE_SCRIPT_MEDIA_POSTS = "create table "
            + TABLE_MEDIA_POSTS + " (" + BaseColumns._ID + " integer primary key autoincrement, "
            + ID_POST_COLUMN + " text not null unique, "
            + AUTHOR_COLUMN + " text not null, "
            + URL_PROFILE_PICTURE_COLUMN + " text not null unique, "
            + URL_ADDRESS_COLUMN + " text not null unique, "
            + COUNT_LIKES_COLUMN + " integer" + ");";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_SCRIPT_TAGS);
        db.execSQL(DATABASE_CREATE_SCRIPT_LOCATIONS);
        db.execSQL(DATABASE_CREATE_SCRIPT_MEDIA_POSTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF IT EXISTS " + TABLE_TAGS);
        db.execSQL("DROP TABLE IF IT EXISTS " + TABLE_LOCATIONS);
        db.execSQL("DROP TABLE IF IT EXISTS " + TABLE_MEDIA_POSTS);
        onCreate(db);
    }
}
