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
import android.widget.TextView;

import com.example.mysekolah.SchoolEnrollment.SchoolEnroll;

public class HomePage extends Fragment implements View.OnClickListener {


    public CardView enrollment, childperform;
    private User currentUser;

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);
        TextView nameText = v.findViewById(R.id.user_name);
        Bundle bundle = getArguments();
        currentUser = (User) bundle.getSerializable("user");
        nameText.setText("Hi," + currentUser.getName());
        enrollment= (CardView) v.findViewById(R.id.enrollmentCard);
        childperform= (CardView) v.findViewById(R.id.CheckChildPerfCard);

        enrollment.setOnClickListener(this);
        childperform.setOnClickListener(this);

        return v;

    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()){
            case R.id.enrollmentCard:
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