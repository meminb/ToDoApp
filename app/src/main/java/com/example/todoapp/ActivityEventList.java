package com.example.todoapp;

import androidx.fragment.app.Fragment;

public class ActivityEventList extends  SingleFragment {
    @Override
    protected Fragment createFragment() {
        return new FragmentEventList();
    }
}
