package com.example.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.Date;
import java.util.UUID;

public class FragmentEvent extends Fragment {
    private static final String ARG_CRIME_ID = "event_id";

    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;

    private Event mEvent;
    private EditText mTitle;
    private Button mDateButton;
    private Button mDeleteButton;

    public static FragmentEvent newInstance(UUID eventId){
        Bundle args =new Bundle();
        args.putSerializable(ARG_CRIME_ID,eventId);

        FragmentEvent fragment=new FragmentEvent();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID eventId=(UUID) getArguments().getSerializable(ARG_CRIME_ID);
        mEvent=EventPool.get(getActivity()).getEvent(eventId);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.event_activity,container,false);

        mTitle=(EditText) v.findViewById(R.id.event_title);
        mTitle.setText(mEvent.getmTitle());
        mTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEvent.setmTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDateButton =(Button) v.findViewById(R.id.event_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                FragmentManager manager=getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mEvent.getmDate());

                dialog.setTargetFragment(FragmentEvent.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        mDeleteButton=(Button) v.findViewById(R.id.event_delete);
        mDeleteButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                EventPool.del(mEvent);
            }
        });



    return v;
    }
/***/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mEvent.setmDate(date);
            System.out.println("aaa:"+mEvent.getmDate());
            updateDate();

        }
    }
    /*Button date'ini günceller*/
    private void updateDate() {
        mDateButton.setText(mEvent.getmDate().toString());

    }
}
