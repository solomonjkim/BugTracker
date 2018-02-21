package edu.andrews.cptr252.ksolomon.bugtracker;
import java.util.ArrayList;
import android.content.Context;

/**
 * Created by solomonjkim on 2/14/18.
 */

public class BugList {
    private static BugList sOurInstance;

    private ArrayList<Bug> mBugs;
    private Context mAppContext;

    private BugList(Context appContext){
        mAppContext = appContext;
        mBugs = new ArrayList<>();
        for (int i = 0; i < 100; i++){
            Bug bug = new Bug();
            bug.setTitle("Bug #" + i);
            bug.setSolved(i % 2 == 0);

            mBugs.add(bug);
        }
    }

    public static BugList getInstance(Context c) {
        if (sOurInstance == null){
            sOurInstance = new BugList(c.getApplicationContext());
        }
        return sOurInstance;
    }

    public ArrayList<Bug> getBugs() {return mBugs;}


    private BugList() {
    }
}
