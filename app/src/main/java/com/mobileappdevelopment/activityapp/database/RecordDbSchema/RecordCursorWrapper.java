package com.mobileappdevelopment.activityapp.database.RecordDbSchema;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.mobileappdevelopment.activityapp.Record;

import java.util.Date;
import java.util.UUID;

/**
 * Created by joshua on 10/26/2017.
 */

public class RecordCursorWrapper extends CursorWrapper {
    public RecordCursorWrapper(Cursor cursor) {
        super (cursor);
    }

    public Record getRecord() {
        String uuidString = getString(getColumnIndex(RecordDbSchema.RecordTable.Cols.UUID));
        String title = getString(getColumnIndex(RecordDbSchema.RecordTable.Cols.TITLE));
        String comments = getString(getColumnIndex(RecordDbSchema.RecordTable.Cols.COMMENTS));
        long date = getLong(getColumnIndex(RecordDbSchema.RecordTable.Cols.DATE));
        int isStudy = getInt(getColumnIndex(RecordDbSchema.RecordTable.Cols.STUDY));
        int isSport = getInt(getColumnIndex(RecordDbSchema.RecordTable.Cols.SPORT));
        int isLeisure = getInt(getColumnIndex(RecordDbSchema.RecordTable.Cols.LEISURE));
        int isWork = getInt(getColumnIndex(RecordDbSchema.RecordTable.Cols.WORK));

        Record record = new Record(UUID.fromString(uuidString));
        record.setTitle(title);
        record.setComments(comments);
        record.setDate(new Date(date));
        record.setStudy(isStudy != 0);
        record.setSport(isSport !=0);
        record.setLeisure(isLeisure !=0);
        record.setWork(isWork !=0);

        return record;
    }
}
