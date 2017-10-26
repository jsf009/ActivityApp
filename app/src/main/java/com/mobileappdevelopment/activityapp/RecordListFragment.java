package com.mobileappdevelopment.activityapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by joshua on 10/18/2017.
 */

public class RecordListFragment extends Fragment {
    private RecyclerView mRecordRecyclerView;
    private RecordAdapter mAdapter;
    private boolean mSubtitleVisible;
    private static final String SAVED_SUBTITLE_VISIBLE= "subtitle";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record_list, container, false);

        mRecordRecyclerView = (RecyclerView) view
                .findViewById(R.id.record_recycler_view);
        mRecordRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (savedInstanceState !=null) {
            mSubtitleVisible=savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_record_list, menu);

        MenuItem subtitleItem = menu.findItem(R.id.show_subtitle);
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_record:
                Record record = new Record();
                RecordStorage.get(getActivity()).addRecord(record);
                Intent intent = RecordPagerActivity
                        .newIntent(getActivity(), record.getId());
                startActivity(intent);
                return true;
            case R.id.show_subtitle:
                mSubtitleVisible =!mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateSubtitle() {
        RecordStorage recordStorage = RecordStorage.get(getActivity());
        int recordCount = recordStorage.getRecord().size();
        String subtitle = getString(R.string.subtitle_format, recordCount);

        if (!mSubtitleVisible) {
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }


    private void updateUI() {
        RecordStorage recordStorage = RecordStorage.get(getActivity());
        List<Record> records = recordStorage.getRecord();

        if (mAdapter == null) {
            mAdapter = new RecordAdapter(records);
            mRecordRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setRecords(records);
            mAdapter.notifyDataSetChanged();
        }

        updateSubtitle();
    }





    private class RecordHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {


        private TextView mTitleTextView;
        private TextView mDateTextView;
        private ImageView mStudyImageView;
        private Record mRecord;

        public RecordHolder (LayoutInflater inflater, ViewGroup parent) {
            super (inflater.inflate(R.layout.list_item_record, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.record_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.record_date);
            mStudyImageView = (ImageView) itemView.findViewById(R.id.study_checked);
        }
        public void bind (Record record) {
            mRecord = record;
            mTitleTextView.setText(mRecord.getTitle());
            mDateTextView.setText(mRecord.getDate().toString());
            mStudyImageView.setVisibility(record.isStudy() ? View.VISIBLE: View.GONE);
        }

        @Override
        public void onClick(View view) {
            Intent intent = RecordPagerActivity.newIntent(getActivity(), mRecord.getId());
            startActivity(intent);
        }
    }



    private  class RecordAdapter extends RecyclerView.Adapter<RecordHolder> {
        private List<Record> mRecords;
        public RecordAdapter(List<Record> records) {
            mRecords = records;
        }

        @Override
        public RecordHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new RecordHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(RecordHolder holder, int position) {
            Record record = mRecords.get(position);
            holder.bind(record);

        }

        @Override
        public int getItemCount() {
            return mRecords.size();
        }

        public void setRecords(List<Record> records) {
            mRecords = records;
        }
    }
}
