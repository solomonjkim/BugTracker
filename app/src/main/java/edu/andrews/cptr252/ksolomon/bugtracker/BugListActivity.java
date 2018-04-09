package edu.andrews.cptr252.ksolomon.bugtracker;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by solomonjkim on 2/14/18.
 */

public class BugListActivity extends SingleFragmentActivity
        implements BugListFragment.Callbacks, BugDetailsFragment.Callbacks {

    public void onBugSelected(Bug bug){
        if(findViewById(R.id.detailFragmentContainer) == null) {
            Intent i = new Intent(this, BugDetailsActivity.class);
            i.putExtra(BugDetailsFragment.EXTRA_BUG_ID, bug.getID());
            startActivityForResult(i, 0);
        } else {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            Fragment oldDetail = fm.findFragmentById(R.id.detailFragmentContainer);
            Fragment newDetail = BugDetailsFragment.newInstance(bug.getID());

            if (oldDetail != null){
                ft.remove(oldDetail);
            }
            ft.add(R.id.detailFragmentContainer, newDetail);
            ft.commit();
        }
    }

    public void onBugUpdated(Bug bug){
        FragmentManager fm = getSupportFragmentManager();
        BugListFragment listFragment = (BugListFragment) fm.findFragmentById(R.id.fragment_container);
        listFragment.updateUI();
    }

    @Override
    protected Fragment createFragment(){
        return new BugListFragment();
    }
}
