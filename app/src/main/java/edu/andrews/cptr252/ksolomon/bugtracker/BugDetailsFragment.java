package edu.andrews.cptr252.ksolomon.bugtracker;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import java.util.UUID;
import java.util.Date;
import android.support.v4.app.FragmentManager;
/**
 * A placeholder fragment containing a simple view.
 */
public class BugDetailsFragment extends Fragment {

    private static final String DIALOG_DATE = "date";
    private static final int REQUEST_DATE = 0;

    public static final String EXTRA_BUG_ID = "edu.andrews.cptr252.ksolomon.bugtracker.bug_id";
    public static final String TAG = "BugDetailsFragment";

    private EditText mDescriptionField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;
    private Bug mBug;
    private EditText mTitleField;

    public static BugDetailsFragment newInstance(UUID bugId){
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_BUG_ID, bugId);
        BugDetailsFragment fragment = new BugDetailsFragment();

        fragment.setArguments(args);
        return fragment;
    }

    public BugDetailsFragment() {
    }

    public void updateDate(){
        mDateButton.setText(mBug.getDate().toString());
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        UUID bugId = (UUID)getArguments().getSerializable(EXTRA_BUG_ID);

        mBug = BugList.getInstance(getActivity()).getBug(bugId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bug_tracker, container, false);

        mTitleField = (EditText) v.findViewById(R.id.bug_title);
        mTitleField.setText(mBug.getTitle());
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
        mDescriptionField = v.findViewById(R.id.bug_description);
        mDescriptionField.setText(mBug.getDescription());
        mDescriptionField.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){

                mBug.setDescription(s.toString());
                Log.d(TAG, "Set description to " + s.toString());
            }

            @Override
            public void afterTextChanged(Editable s){

            }
        });

        mDateButton = v.findViewById(R.id.bug_date);
        mDateButton.setText(mBug.getDate().toString());

        updateDate();

        mDateButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                FragmentManager fm = getActivity().getSupportFragmentManager();

                DatePickerFragment dialog = DatePickerFragment.newInstance(mBug.getDate());

                dialog.setTargetFragment(BugDetailsFragment.this, REQUEST_DATE);

                dialog.show(fm, DIALOG_DATE);
            }
        });

        mSolvedCheckBox = v.findViewById(R.id.bug_solved);
        mSolvedCheckBox.setChecked(mBug.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mBug.setSolved(isChecked);
                Log.d(TAG, "Set solved status to " + isChecked);
            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode != Activity.RESULT_OK){
            return;
        }
        if(requestCode == REQUEST_DATE){

            Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);

            mBug.setDate(date);

            updateDate();
        }
    }
}
