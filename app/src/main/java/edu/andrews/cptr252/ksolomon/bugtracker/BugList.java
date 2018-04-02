package edu.andrews.cptr252.ksolomon.bugtracker;
import java.util.ArrayList;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.content.ContentValues;
import android.database.Cursor;
import java.util.UUID;
import edu.andrews.cptr252.ksolomon.bugtracker.database.BugCursorWrapper;
import edu.andrews.cptr252.ksolomon.bugtracker.database.BugDbSchema.BugTable;
import edu.andrews.cptr252.ksolomon.bugtracker.database.BugDbHelper;

/**
 * Created by solomonjkim on 2/14/18.
 */

public class BugList {
    private static BugList sOurInstance;

    private static final String TAG = "BugList";

    private static final String FILENAME = "bugs.json";

    private BugJSONSerializer mSerializer;

    private SQLiteDatabase mDatabase;
    private Context mAppContext;

    public void addBug(Bug bug){
        ContentValues values = getContentValues(bug);
        mDatabase.insert(BugTable.NAME, null, values);
    }

    public void updateBug(Bug bug){
        String uuidString = bug.getID().toString();
        ContentValues values = getContentValues(bug);

        mDatabase.update(BugTable.NAME, values, BugTable.Cols.UUID + " =? ", new String[]{uuidString});
    }
    private BugList(Context appContext){

        mAppContext = appContext.getApplicationContext();
        mDatabase = new BugDbHelper(mAppContext).getWritableDatabase();

    }

    public static BugList getInstance(Context c) {
        if (sOurInstance == null){
            sOurInstance = new BugList(c.getApplicationContext());
        }
        return sOurInstance;
    }

    public ArrayList<Bug> getBugs() {
        ArrayList<Bug> bugs = new ArrayList<>();
        BugCursorWrapper cursor = queryBugs(null, null);

        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                bugs.add(cursor.getBug());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return bugs;
    }


    private BugList() {
    }

    public Bug getBug(UUID id){
        BugCursorWrapper cursor = queryBugs(BugTable.Cols.UUID + " =? ", new String[] { id.toString()});

        try{
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getBug();
        } finally {
            cursor.close();
        }
    }

    public void deleteBug(Bug bug){

        String uuidString = bug.getID().toString();
        mDatabase.delete(BugTable.NAME, BugTable.Cols.UUID + " =? ", new String[] {uuidString});
    }

    public static ContentValues getContentValues(Bug bug){
        ContentValues values = new ContentValues();
        values.put(BugTable.Cols.UUID, bug.getID().toString());
        values.put(BugTable.Cols.TITLE, bug.getTitle());
        values.put(BugTable.Cols.DESCRIPTION, bug.getDescription());
        values.put(BugTable.Cols.DATE, bug.getDate().getTime());
        values.put(BugTable.Cols.SOLVED, bug.isSolved() ? 1: 0);

        return values;
    }

    private BugCursorWrapper queryBugs(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                BugTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new BugCursorWrapper(cursor);
    }
}
