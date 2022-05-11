package com.example.news_app43.ui.secondProfile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.transition.ChangeBounds;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.news_app43.R;

public class SecondProfileFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setSharedElementEnterTransition(new ChangeBounds());
        return inflater.inflate(R.layout.fragment_second_profile, container, false);
    }
}