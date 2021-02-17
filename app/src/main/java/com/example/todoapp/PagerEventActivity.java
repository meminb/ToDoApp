package com.example.todoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;
import java.util.UUID;

public class PagerEventActivity extends AppCompatActivity {

    private static final String EXTRA_EVENT_ID ="crime_id";
    private ViewPager mViewPager;
    private List<Event> mEvents;

    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, PagerEventActivity.class);
        intent.putExtra(EXTRA_EVENT_ID, crimeId);
        return intent;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_pager);

        UUID eventId=(UUID) getIntent().getSerializableExtra(EXTRA_EVENT_ID);
        mViewPager =(ViewPager) findViewById(R.id.event_view_pager);

        mEvents=EventPool.get(this).getEvents();
        FragmentManager fm=getSupportFragmentManager();

        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                Event ev=mEvents.get(position);
                return FragmentEvent.newInstance(ev.getmId());
            }

            @Override
            public int getCount() {
                return mEvents.size();
            }
        });
        //mevcut sayfanın güncellenmesi (swipe eventler için)
        for (int i = 0; i < mEvents.size(); i++) {
            if (mEvents.get(i).getmId().equals(eventId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }


    }
}
