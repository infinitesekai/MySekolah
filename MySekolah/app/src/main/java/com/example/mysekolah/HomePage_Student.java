package com.example.mysekolah;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.mysekolah.PersonalityCareerTest.PersonalityTestHome;

public class HomePage_Student extends Fragment implements View.OnClickListener {

    public CardView academic_qualification, exam_check, personality_check;

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home_student, container, false);

        academic_qualification = (CardView)v.findViewById(R.id.academic_qualification);
        exam_check = (CardView)v.findViewById(R.id.exam_check);
        personality_check = (CardView)v.findViewById(R.id.personality_test);

        academic_qualification.setOnClickListener(this);
        exam_check.setOnClickListener(this);
        personality_check.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()){
            case R.id.academic_qualification:
                i= new Intent(getActivity(), Academic_Qualification.class);
                startActivity(i);
                break;
            case R.id.exam_check:
                i = new Intent(getActivity(), ExamResultTable.class);
                startActivity(i);
                break;
            case R.id.personality_test:
                i=new Intent(getActivity(), PersonalityTestHome.class);
                startActivity(i);

        }
    }
}