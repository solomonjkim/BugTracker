package edu.andrews.cptr252.ksolomon.bugtracker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import java.util.ArrayList;
import android.content.Intent;

/**
 * A simple {@link Fragment} subclass.
 */
public class BugListFragment extends ListFragment {


    private class BugAdapter extends ArrayAdapter<Bug> {

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

        BugAdapter adapter = new BugAdapter(mBugs);
        setListAdapter(adapter);
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
        ((BugAdapter)getListAdapter()).notifyDataSetChanged();
    }

}
