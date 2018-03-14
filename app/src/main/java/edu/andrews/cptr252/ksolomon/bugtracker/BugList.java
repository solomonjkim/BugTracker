package edu.andrews.cptr252.ksolomon.bugtracker;
import java.util.ArrayList;
import android.content.Context;
import android.util.Log;

import java.util.UUID;

/**
 * Created by solomonjkim on 2/14/18.
 */

public class BugList {
    private static BugList sOurInstance;

    private static final String TAG = "BugList";

    private static final String FILENAME = "bugs.json";

    private BugJSONSerializer mSerializer;

    private ArrayList<Bug> mBugs;
    private Context mAppContext;

    public boolean saveBugs() {
        try{
            mSerializer.saveBugs(mBugs);
            Log.d(TAG, "Bugs saved to file");
            return true;
        } catch (Exception e){
            Log.e(TAG, "Error saving bugs: " + e);
            return false;
        }
    }

    public void addBug(Bug bug){
        mBugs.add(bug);
        saveBugs();
    }
    private BugList(Context appContext){
        mAppContext = appContext;
        mSerializer = new BugJSONSerializer(mAppContext, FILENAME);

        try{
            mBugs = mSerializer.loadBugs();
        } catch (Exception e){

            mBugs = new ArrayList<>();
            Log.e(TAG, "Error loading bugs: " + e);
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

    public Bug getBug(UUID id){
        for (Bug bug : mBugs){
            if(bug.getID().equals(id))
                    return bug;
        }
        return null;
    }

    public void deleteBug(Bug bug){
        mBugs.remove(bug);
        saveBugs();
    }
}
