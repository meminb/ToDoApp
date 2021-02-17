package com.example.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.List;

public class FragmentEventList extends Fragment {
    private RecyclerView mEventRecycler;
    private EventAdapter mAdapter;

    private boolean mSubtitleVisible;
    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_event_list,container,false);
        mEventRecycler=(RecyclerView) view.findViewById(R.id.event_recycler_view);
        mEventRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));


        if (savedInstanceState != null) {//susbtitle görünür halde mi?
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }
        updateUI();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);

        MenuItem subtitleItem = menu.findItem(R.id.show_subtitle);//show_subtitle menu butonunu alır
        if (mSubtitleVisible) {//subtitle görünmeli mi?
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    private void updateSubtitle() {//kaç tane crime olduğunu günceller
        EventPool crimeLab = EventPool.get(getActivity());
        int crimeCount = crimeLab.getEvents().size();
        String subtitle = getString(R.string.subtitle_format, crimeCount);
        if (!mSubtitleVisible) {
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//seçilen menu itemine göre action
        switch (item.getItemId()) {
            case R.id.new_crime://yeni crime oluştur ve bunu güncellemek üzere crimeActivity'e yönlendirir
                Event event = new Event("",new Date());
                EventPool.get(getActivity()).add(event);//yeni kaydı CrimeLab'a ekler
                Intent intent = PagerEventActivity.newIntent(getActivity(), event.getmId());//sayfa değiştirme
                startActivity(intent);
                return true;
            case R.id.show_subtitle://subtitle on-of
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle  ();
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onResume() {
        super.onResume();updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);//sayfa değşimleri için önceki verilerin saklanmasını sağlar
    }
    private void updateUI(){
        EventPool ep=  EventPool.get(getActivity());
        List<Event> events=ep.getEvents();

        if (mAdapter==null){
            mAdapter=new EventAdapter(events);
            mEventRecycler.setAdapter(mAdapter);
        }else{
            mAdapter.notifyDataSetChanged();
        }

    }


    private class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Event mEvent;
        private TextView mTitleOfEvent;
        private TextView mDateOfEvent;
        private CheckBox mDone;




        public EventHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_list,parent,false));
            itemView.setOnClickListener(this);

            mTitleOfEvent=(TextView) itemView.findViewById(R.id.textView_title);
            mDateOfEvent=(TextView) itemView.findViewById(R.id.textView_date);
            mDone =(CheckBox) itemView.findViewById(R.id.checkbox_done);

        }

        public void bind(Event event){
            mEvent=event;
            mTitleOfEvent.setText(mEvent.getmTitle());
            mDateOfEvent.setText(mEvent.getmDate().toString());
            mDone.setChecked(mEvent.isDone());


        }


        @Override
        public void onClick(View v) {
            Intent intent= PagerEventActivity.newIntent(getActivity(),mEvent.getmId());
            startActivity(intent);
        }
    }

    private class EventAdapter extends RecyclerView.Adapter<EventHolder>{
        private List<Event> mEvents;
        public EventAdapter(List<Event> l){
            this.mEvents=l;
        }


        @NonNull
        @Override
        public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater li = LayoutInflater.from(getActivity());
            return new EventHolder(li,parent);
        }

        @Override
        public void onBindViewHolder(@NonNull EventHolder holder, int position) {
            Event e=mEvents.get(position);
            holder.bind(e);
        }

        @Override
        public int getItemCount() {
            return mEvents.size();
        }
    }



}
