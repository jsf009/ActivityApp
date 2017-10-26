package com.mobileappdevelopment.activityapp;

import android.support.v4.app.Fragment;

/**
 * Created by joshua on 10/18/2017.
 */

public class RecordListActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragement() {
        return new RecordListFragment();
    }
}


