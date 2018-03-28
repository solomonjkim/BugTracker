package edu.andrews.cptr252.ksolomon.bugtracker.database;

/**
 * Created by solomonjkim on 3/28/18.
 */

import android.database.Cursor;
import android.database.CursorWrapper;
import java.util.Date;
import java.util.UUID;
import edu.andrews.cptr252.ksolomon.bugtracker.Bug;
import edu.andrews.cptr252.ksolomon.bugtracker.database.BugDbSchema.BugTable;


public class BugCursorWrapper extends CursorWrapper{
    public BugCursorWrapper(Cursor cursor) {
        super (cursor);
    }

    public Bug getBug(){
        String uuidString = getString(getColumnIndex(BugTable.Cols.UUID));
        String title = getString(getColumnIndex(BugTable.Cols.TITLE));
        String description = getString(getColumnIndex(BugTable.Cols.DESCRIPTION));
        Long date = getLong(getColumnIndex(BugTable.Cols.DATE));
        int isSolved = getInt(getColumnIndex(BugTable.Cols.SOLVED));

        Bug bug = new Bug(UUID.fromString(uuidString));
        bug.setTitle(title);
        bug.setDescription(description);
        bug.setDate(new Date(date));
        bug.setSolved(isSolved != 0);
        return bug;

    }
}
