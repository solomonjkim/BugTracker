package edu.andrews.cptr252.ksolomon.bugtracker;
import android.support.v4.app.Fragment;

/**
 * Created by solomonjkim on 2/14/18.
 */

public class BugListActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment(){
        return new BugListFragment();
    }
}
