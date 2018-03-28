package edu.andrews.cptr252.ksolomon.bugtracker.database;

/**
 * Created by solomonjkim on 3/28/18.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.andrews.cptr252.ksolomon.bugtracker.database.BugDbSchema.BugTable;

public class BugDbHelper extends SQLiteOpenHelper{

    private static final int VERSION = 1;

    private static final String DATABASE_NAME = "bugDb.db";

    public BugDbHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + BugTable.NAME + "(" + "_id integer primary key autoincrement, " + BugTable.Cols.UUID + ", " +
                BugTable.Cols.TITLE + ", " + BugTable.Cols.DESCRIPTION + ", " + BugTable.Cols.DATE + ", " + BugTable.Cols.SOLVED + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
