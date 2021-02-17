package com.example.todoapp;

import java.util.Date;
import java.util.UUID;

public class Event {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean Done;

    public Event(String mTitle, Date mDate) {
        this.mId= UUID.randomUUID();
        this.mTitle = mTitle;
        this.mDate = mDate;
        Done = false;
    }

    public void setmId(UUID mId) {
        this.mId = mId;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    public void setDone(boolean done) {
        Done = done;
    }

    public UUID getmId() {
        return mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public Date getmDate() {
        return mDate;
    }

    public boolean isDone() {
        return Done;
    }
}
