package com.mobileappdevelopment.activityapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mobileappdevelopment.activityapp.database.RecordDbSchema.RecordBaseHelper;
import com.mobileappdevelopment.activityapp.database.RecordDbSchema.RecordCursorWrapper;
import com.mobileappdevelopment.activityapp.database.RecordDbSchema.RecordDbSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by joshua on 10/16/2017.
 */

public class RecordStorage {
    private static RecordStorage sRecordStorage;
    private Context mContext;
    private SQLiteDatabase mdatabase;

    public static RecordStorage get(Context context) {
        if (sRecordStorage == null){
            sRecordStorage = new RecordStorage(context);
        }
        return sRecordStorage;
    }
    private RecordStorage(Context context){
        mContext = context.getApplicationContext();
        mdatabase = new RecordBaseHelper(mContext)
                .getWritableDatabase();
    }

    public void addRecord (Record r) {
        ContentValues values = getContentValues(r);

        mdatabase.insert(RecordDbSchema.RecordTable.NAME, null, values);

    }

    public List<Record> getRecord() {
        List <Record> records = new ArrayList<>();

        RecordCursorWrapper cursor = queryRecords(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                records.add(cursor.getRecord());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return records;
    }

    public Record getRecord (UUID id) {
        RecordCursorWrapper cursor = queryRecords(
                RecordDbSchema.RecordTable.Cols.UUID + " = ?",
                new String[] { id.toString()}
        );
        try {
            if (cursor.getCount() ==0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getRecord();
        } finally {
            cursor.close();
        }
    }

    public void updateRecord (Record record) {
        String uuidString = record.getId().toString();
        ContentValues values = getContentValues(record);

        mdatabase.update(RecordDbSchema.RecordTable.NAME, values,
                RecordDbSchema.RecordTable.Cols.UUID + " =? " ,
                new String[] { uuidString});
    }

    private RecordCursorWrapper queryRecords (String whereClause, String[] whereArgs){
        Cursor cursor = mdatabase.query(
                RecordDbSchema.RecordTable.NAME,
                null,
                whereClause,
                whereArgs,
                null, //groupby
                null, //having
                null//orderby
        );
        return new RecordCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Record record) {
        ContentValues values = new ContentValues();
        values.put (RecordDbSchema.RecordTable.Cols.UUID, record.getId().toString());
        values.put (RecordDbSchema.RecordTable.Cols.TITLE, record.getTitle());
        values.put (RecordDbSchema.RecordTable.Cols.COMMENTS, record.getComments());
        values.put (RecordDbSchema.RecordTable.Cols.DATE, record.getDate().getTime());
        values.put (RecordDbSchema.RecordTable.Cols.STUDY, record.isStudy() ? 1 : 0);
        values.put (RecordDbSchema.RecordTable.Cols.SPORT, record.isSport() ? 1 : 0);
        values.put (RecordDbSchema.RecordTable.Cols.LEISURE, record.isLeisure() ? 1 : 0);
        values.put (RecordDbSchema.RecordTable.Cols.WORK, record.isWork() ? 1 : 0);

        return values;

    }
}
