package com.example.appfinalproject_09163104;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLite extends SQLiteOpenHelper {
    private final static String DB = "music_datas.DB";
    private final static String TB = "music";
    private final static int VS = 2;
    public SQLite(@Nullable Context context) {
        super(context, DB, null, VS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { ///CREATE TABLE IF NOT EXISTS
        String SQL = "CREATE TABLE "+ TB + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, _title TEXT, _time TEXT, _location TEXT, _locationName TEXT, _onSales TEXT, _price TEXT, _endTime TEXT, _descriptionFilterHtml TEXT, _masterUnit TEXT, _webSales TEXT, _sourceWebPromote TEXT, _hitRate INTEGER, _photo BLOB);";
        db.execSQL(SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL = "DROP TABLE IF EXISTS "+ TB;
        db.execSQL(SQL);
        onCreate(db);
    }
}

