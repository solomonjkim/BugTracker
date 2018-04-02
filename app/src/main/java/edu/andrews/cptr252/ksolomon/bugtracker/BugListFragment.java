package edu.andrews.cptr252.ksolomon.bugtracker;


import android.drm.DrmStore;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import android.content.Intent;

import static java.util.Collections.addAll;

/**
 * A simple {@link Fragment} subclass.
 */
public class BugListFragment extends ListFragment {

    private BugAdapter mAdapter;

    private void updateUI(){
        BugList bugList = BugList.getInstance(getActivity());
        ArrayList<Bug> bugs = bugList.getBugs();

        if(mAdapter == null){
            mAdapter = new BugAdapter(bugs);
            setListAdapter(mAdapter);
        } else {
            mAdapter.setBugs(bugs);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View v = super.onCreateView(inflater, parent, savedInstanceState);

        updateUI();

        ListView listView = v.findViewById(android.R.id.list);

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

            }
            public boolean onCreateActionMode(ActionMode mode, Menu menu){
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.bug_list_item_context, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_item_delete_bug:

                        BugList bugList = BugList.getInstance(getActivity());


                        for(int i = mAdapter.getCount() - 1; i >= 0; i--){
                            if(getListView().isItemChecked(i)){
                                bugList.deleteBug(mAdapter.getItem(i));
                            }
                        }

                        mode.finish();
                        updateUI();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });
        return v;
    }


    private class BugAdapter extends ArrayAdapter<Bug> {

        public void setBugs(ArrayList<Bug> bugs){
            clear();
            addAll(bugs);
        }

        public BugAdapter(ArrayList<Bug> bugs) {
            super(getActivity(), 0, bugs);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){

            if(null == convertView){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_bug, null);
            }

            Bug bug = getItem(position);

            TextView titleTextView = convertView.findViewById(R.id.bug_list_item_titleTextView);
            titleTextView.setText(bug.getTitle());

            TextView dateTextView = convertView.findViewById(R.id.bug_list_item_dateTextView);
            dateTextView.setText(bug.getDate().toString());

            CheckBox solvedCheckBox = convertView.findViewById(R.id.bug_list_item_solvedCheckBox);
            solvedCheckBox.setChecked(bug.isSolved());

            return convertView;
        }
    }

    private static final String TAG = "BugListFragment";

    private ArrayList<Bug> mBugs;

    public BugListFragment() {
        // Required empty public constructor
    }

private void addBug(){
        Bug bug = new Bug();
        BugList.getInstance(getActivity()).addBug(bug);

        Intent i = new Intent(getActivity(), BugDetailsActivity.class);
        i.putExtra(BugDetailsFragment.EXTRA_BUG_ID, bug.getID());

        startActivityForResult(i, 0);
}

@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);

            inflater.inflate(R.menu.menu_bug_list, menu);
}

@Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_item_add_bug: addBug();  return true;

            default: return super.onOptionsItemSelected(item);
        }
}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.bug_list_label);
        mBugs = BugList.getInstance(getActivity()).getBugs();

        mAdapter = new BugAdapter(mBugs);
        setListAdapter(mAdapter);
    }


    public void onListItemClick(ListView l, View v, int position, long id){
        Bug bug = (Bug)(getListAdapter()).getItem(position);

        Intent i = new Intent(getActivity(), BugDetailsActivity.class);

        i.putExtra(BugDetailsFragment.EXTRA_BUG_ID, bug.getID());
        startActivity(i);
    }

    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }
}
