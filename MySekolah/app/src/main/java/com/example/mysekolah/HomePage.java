package com.example.mysekolah;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomePage extends Fragment implements View.OnClickListener {


    public CardView enrollment, childperform;


    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);
        enrollment= (CardView) v.findViewById(R.id.parentCard);
        childperform= (CardView) v.findViewById(R.id.CheckChildPerfCard);

        enrollment.setOnClickListener(this);
        childperform.setOnClickListener(this);

        return v;

    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()){
            case R.id.parentCard:
                i= new Intent(getActivity(), SchoolEnroll.class);
                startActivity(i);
                break;
            case R.id.CheckChildPerfCard:
                i = new Intent(getActivity(), child_performance.class);
                startActivity(i);
                break;

        }
    }
}