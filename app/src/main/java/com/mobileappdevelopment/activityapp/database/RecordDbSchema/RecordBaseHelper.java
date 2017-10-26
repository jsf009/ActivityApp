package com.mobileappdevelopment.activityapp.database.RecordDbSchema;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mobileappdevelopment.activityapp.database.RecordDbSchema.RecordDbSchema.RecordTable;

/**
 * Created by joshua on 10/24/2017.
 */

public class RecordBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "RecordBase.db";

    public RecordBaseHelper (Context context) {
        super (context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + RecordTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                RecordTable.Cols.UUID + "," +
                RecordTable.Cols.TITLE + "," +
                RecordTable.Cols.COMMENTS + "," +
                RecordTable.Cols.DATE + "," +
                RecordTable.Cols.STUDY + "," +
                RecordTable.Cols.SPORT + "," +
                RecordTable.Cols.LEISURE + "," +
                RecordTable.Cols.WORK +
                ")"
        );

    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
