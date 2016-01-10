package com.example.makarov.photonews.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class TagDataBaseHelper extends SQLiteOpenHelper implements BaseColumns {

    private static final String DATABASE_NAME = "data_base_photo_news";
    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_TABLE = "tags";
    public static final String TAG_NAME_COLUMN = "tag_name";

    private static final String DATABASE_CREATE_SCRIPT = "create table "
            + DATABASE_TABLE + " (" + BaseColumns._ID
            + " integer primary key autoincrement, " + TAG_NAME_COLUMN
            + " text not null" + ");";

    public TagDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF IT EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }
}
