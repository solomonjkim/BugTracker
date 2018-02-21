package edu.andrews.cptr252.ksolomon.bugtracker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import java.util.ArrayList;

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

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.bug_list_label);
        mBugs = BugList.getInstance(getActivity()).getBugs();

        BugAdapter adapter = new BugAdapter(mBugs);
        setListAdapter(adapter);
    }


    public void onListItemClick(ListView i, View v, int position, long id){
        Bug bug = (Bug)(getListAdapter()).getItem(position);
        Log.d(TAG, bug.getTitle() + " was clicked");
}

}
