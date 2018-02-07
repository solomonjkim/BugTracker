package edu.andrews.cptr252.ksolomon.bugtracker;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * A placeholder fragment containing a simple view.
 */
public class BugDetailsFragment extends Fragment {

    public static final String TAG = "BugDetailsFragment";
    private Bug mBug;
    private EditText mTitleField;

    public BugDetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mBug = new Bug();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bug_tracker, container, false);

        mTitleField = (EditText) v.findViewById(R.id.bug_title);
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mBug.setTitle(s.toString());

                Log.d(TAG, "Title changed to " + mBug.getTitle());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return v;
    }
}
