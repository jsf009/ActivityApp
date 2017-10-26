package com.mobileappdevelopment.activityapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

/**
 * Created by joshua on 10/20/2017.
 */

public class RecordPagerActivity extends AppCompatActivity{

    private static final String EXTRA_RECORD_ID=
            "com.mobileappdevelopment.activityapp.record_id";

    private ViewPager mViewPager;
    private List<Record> mRecord;

    public static Intent newIntent (Context packageContext, UUID recordId) {
        Intent intent = new Intent (packageContext, RecordPagerActivity.class);
        intent.putExtra(EXTRA_RECORD_ID, recordId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_pager);

        UUID recordID =(UUID) getIntent()
                .getSerializableExtra(EXTRA_RECORD_ID);

        mViewPager = (ViewPager) findViewById(R.id.record_view_pager);

        mRecord = RecordStorage.get(this).getRecord();
        FragmentManager fragmentManger = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManger) {

            @Override
            public Fragment getItem(int position) {
                Record record = mRecord.get(position);
                return RecordFragment.newInstance(record.getId());
            }

            @Override
            public int getCount() {
                return mRecord.size();
            }
        });

        for (int i =0; i<mRecord.size(); i++) {
            if (mRecord.get(i).getId().equals(recordID)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }


}
