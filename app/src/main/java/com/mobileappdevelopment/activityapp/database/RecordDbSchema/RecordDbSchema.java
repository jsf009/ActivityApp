package com.mobileappdevelopment.activityapp.database.RecordDbSchema;

/**
 * Created by joshua on 10/24/2017.
 */

public class RecordDbSchema {
    public static final class RecordTable {
        public static final String NAME = "records";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String COMMENTS = "comments";
            public static final String DATE = "date";
            public static final String STUDY = "study";
            public static final String SPORT = "sport";
            public static final String LEISURE = "leisure";
            public static final String WORK = "work";
        }
    }
}
