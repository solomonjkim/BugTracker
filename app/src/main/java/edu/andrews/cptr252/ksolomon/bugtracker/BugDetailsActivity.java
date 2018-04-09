package edu.andrews.cptr252.ksolomon.bugtracker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import java.util.UUID;
import java.util.ArrayList;

public class BugDetailsActivity extends FragmentActivity implements BugDetailsFragment.Callbacks{

    private ViewPager mViewPager;

    private ArrayList<Bug> mBugs;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        mViewPager = new ViewPager(this);

        mViewPager.setId(R.id.viewPager);

        setContentView(mViewPager);

        mBugs = BugList.getInstance(this).getBugs();

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                Bug bug = mBugs.get(position);
                return BugDetailsFragment.newInstance(bug.getID());
            }

            @Override
            public int getCount() {
                return mBugs.size();
            }
        });

        UUID bugId = (UUID) getIntent().getSerializableExtra(BugDetailsFragment.EXTRA_BUG_ID);


        for (int i = 0; i < mBugs.size(); i++){
            if(mBugs.get(i).getID().equals(bugId)){
                mViewPager.setCurrentItem(i);
                break;
            }

        }

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){

            @Override
            public void onPageScrolled(int i, float v, int i2){

            }

            @Override
            public void onPageSelected(int position){
                Bug bug = mBugs.get(position);
                if(bug.getTitle()!= null){
                    setTitle(bug.getTitle());
                }
            }

            @Override
            public void onPageScrollStateChanged(int i){

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bug_tracker, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onBugUpdated(Bug bug){

    }
}
