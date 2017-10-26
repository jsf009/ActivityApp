package com.mobileappdevelopment.activityapp;

import java.util.Date;
import java.util.UUID;

/**
 * Created by joshua on 10/12/2017.
 */

public class Record {
    private UUID mId;
    private String mTitle;
    private String mComments;
    private Date mDate;
    private boolean mStudy;
    private boolean mSport;
    private boolean mLeisure;
    private boolean mWork;

    public Record() {
        this(UUID.randomUUID());
    }
    public Record(UUID id){
        mId = id;
        mDate = new Date();
    }
    public UUID getId(){
        return mId;
    }
    public String getTitle() {
        return mTitle;
    }
    public void setTitle (String title) {
        mTitle = title;
    }

    public String getComments() {
        return mComments;
    }

    public void setComments(String mComments) {
        this.mComments = mComments;
    }

    public Date getDate() {
        return mDate;
    }
    public void setDate (Date date) {
        mDate = date;
    }

    public boolean isStudy() {
        return mStudy;
    }
    public void setStudy(boolean solved) {
        mStudy = solved;
    }

    public boolean isSport() {
        return mSport;
    }

    public void setSport(boolean mSport) {
        this.mSport = mSport;
    }

    public boolean isLeisure() {
        return mLeisure;
    }

    public void setLeisure(boolean mLeisure) {
        this.mLeisure = mLeisure;
    }

    public boolean isWork() {
        return mWork;
    }

    public void setWork(boolean mWork) {
        this.mWork = mWork;
    }
}
