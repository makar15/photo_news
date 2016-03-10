package com.example.makarov.photonews.database;

import android.database.Cursor;

public class SQLiteUtils {

    protected static String getString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    protected static Long getLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

    protected static Double getDouble(Cursor cursor, String columnName) {
        return cursor.getDouble(cursor.getColumnIndex(columnName));
    }

    protected static int getInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }
}
