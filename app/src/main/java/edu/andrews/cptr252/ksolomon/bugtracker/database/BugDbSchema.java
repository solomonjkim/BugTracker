package edu.andrews.cptr252.ksolomon.bugtracker.database;

/**
 * Created by solomonjkim on 3/28/18.
 */

public class BugDbSchema {
    public static final class BugTable {
        public static final String NAME = "bugs";


        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DESCRIPTION = "description";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
        }
    }
}

