package com.mobileappdevelopment.activityapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Date;
import java.util.UUID;

/**
 * Created by joshua on 10/13/2017.
 */

public class RecordFragment extends Fragment {
    private static final String ARG_RECORD_ID= "record_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;

    private Record mRecord;
    private EditText mTitleField;
    private EditText mCommentsField;
    private Button mDateButton;
    private Button mCancelButton;
    private Button mSaveButton;
    private CheckBox mStudyCheckBox;
    private CheckBox mSportCheckBox;
    private CheckBox mLeisureCheckBox;
    private CheckBox mWorkCheckBox;

    public static RecordFragment newInstance (UUID recordid) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_RECORD_ID, recordid);

        RecordFragment fragment = new RecordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate (Bundle savedInstanceState){
        super. onCreate(savedInstanceState);
        UUID recordId = (UUID)  getArguments().getSerializable(ARG_RECORD_ID);
        mRecord = RecordStorage.get(getActivity()).getRecord(recordId);

    }

    @Override
    public void onPause() {
        super.onPause();

        RecordStorage.get(getActivity())
                .updateRecord(mRecord);
    }




    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_record, container, false);

        mTitleField = (EditText) v.findViewById(R.id.record_title);
        mTitleField.setText(mRecord.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                mRecord.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mCommentsField = (EditText) v.findViewById(R.id.record_comment);
        mCommentsField.setText(mRecord.getComments());
        mCommentsField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                mRecord.setComments(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mSportCheckBox = (CheckBox)v.findViewById(R.id.record_sport);
        mSportCheckBox.setChecked(mRecord.isSport());
        mSportCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                mRecord.setSport(isChecked);
            }
        });

        mStudyCheckBox = (CheckBox)v.findViewById(R.id.record_study);
        mStudyCheckBox.setChecked(mRecord.isStudy());
        mStudyCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                mRecord.setStudy(isChecked);
            }
        });

        mLeisureCheckBox = (CheckBox)v.findViewById(R.id.record_leisure);
        mLeisureCheckBox.setChecked(mRecord.isLeisure());
        mLeisureCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                mRecord.setLeisure(isChecked);
            }
        });

        mWorkCheckBox = (CheckBox)v.findViewById(R.id.record_work);
        mWorkCheckBox.setChecked(mRecord.isWork());
        mWorkCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                mRecord.setWork(isChecked);
            }
        });

        mCancelButton =(Button) v.findViewById(R.id.record_cancel);
        mCancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
            }
        });

        mCancelButton =(Button) v.findViewById(R.id.record_save);
        mCancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
            }
        });

        mDateButton= (Button) v.findViewById(R.id.record_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment
                        .newInstance(mRecord.getDate());
                dialog.setTargetFragment(RecordFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }

        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mRecord.setDate(date);
            updateDate();
        }
    }

    private void updateDate() {
        mDateButton.setText(mRecord.getDate().toString());
    }

}
