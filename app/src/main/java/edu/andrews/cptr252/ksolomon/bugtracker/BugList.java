package edu.andrews.cptr252.ksolomon.bugtracker;
import java.util.ArrayList;
import android.content.Context;
import java.util.UUID;

/**
 * Created by solomonjkim on 2/14/18.
 */

public class BugList {
    private static BugList sOurInstance;

    private ArrayList<Bug> mBugs;
    private Context mAppContext;

    public void addBug(Bug bug){
        mBugs.add(bug);
    }
    private BugList(Context appContext){
        mAppContext = appContext;
        mBugs = new ArrayList<>();

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

    public Bug getBug(UUID id){
        for (Bug bug : mBugs){
            if(bug.getID().equals(id))
                    return bug;
        }
        return null;
    }
}
