package com.example.todoapp;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class EventPool {

    private static EventPool sEventPool;
    private  static List<Event> mEvents;


    public static EventPool get(Context context){
        if (sEventPool==null){
            sEventPool=new EventPool(context);
        }return sEventPool;
    }

    public static void del(Event e){
        mEvents.remove(e);
    }

    public EventPool(Context context) {
       mEvents = new ArrayList<>();

        /**for (int i = 0; i < 20; i++) {/creates random events
            mEvents.add(new Event("event #"+i, new Date(System.currentTimeMillis())));
        }**/
    }

    public void add(Event e){
        mEvents.add(0,e);
    }

    public Event getEvent(UUID id) {
        for (Event e : mEvents) {
            if (e.getmId().equals(id)) {
                return e;
            }
        }
        return null;
    }
    public List<Event> getEvents(){
        return mEvents;
    }


}
