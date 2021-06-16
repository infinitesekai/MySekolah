package com.example.mysekolah;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mysekolah.adapter.ChildRecyclerAdapter;
import com.example.mysekolah.util.MyApplication;

public class ProfilePage extends Fragment {
    private Button editBtn,editChildBtn,btnLogout;
    private User currentUser;

    private TextView icNoText;
    private TextView nameText;
    private TextView genderText;
    private TextView racesText;
    private TextView nationText;
    private TextView jobText;
    private TextView salaryText;
    private TextView addressText;
    private TextView phoneText;
    private RecyclerView recyclerView;
    private ChildRecyclerAdapter adapter;
    private DatabaseAccess DB;

//    @Override
//    public void onResume() {
//        super.onResume();
//
//        Bundle bundle = getArguments();
//        currentUser = (User) bundle.getSerializable("user");
//        DB.open();
//        adapter.setChildren(DB.getPChilds(currentUser.getICNo()));
//        adapter.notifyDataSetChanged();
//        currentUser = MyApplication.currentUser;
//        icNoText.setText(currentUser.getICNo());
//        nameText.setText(currentUser.getName());
//        genderText.setText(currentUser.getGender());
//        jobText.setText(currentUser.getJob());
//        salaryText.setText(currentUser.getSalary());
//        addressText.setText(currentUser.getAddress());
//        phoneText.setText(currentUser.getPhoneNo());
//        racesText.setText(currentUser.getRace());
//        nationText.setText(currentUser.getNation());
//    }

   @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        //currentUser = MyApplication.currentUser;
        Bundle bundle = getArguments();
        currentUser = (User) bundle.getSerializable("user");
        DB = DatabaseAccess.getInstance(getContext());
        DB.open();
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        icNoText = view.findViewById(R.id.tvIC);
        nameText = view.findViewById(R.id.tvName);
        genderText = view.findViewById(R.id.tvgender);
        racesText = view.findViewById(R.id.tvraces);
        nationText = view.findViewById(R.id.tvnationality);
        jobText = view.findViewById(R.id.tvjob);
        salaryText = view.findViewById(R.id.tvsalary);
        addressText = view.findViewById(R.id.tvaddress);
        phoneText = view.findViewById(R.id.tvphoneno);
        //currentUser = MyApplication.currentUser;

        Bundle bundle = getArguments();
        currentUser = (User) bundle.getSerializable("user");

        icNoText.setText(currentUser.getICNo());
        nameText.setText(currentUser.getName());
        genderText.setText(currentUser.getGender());
        jobText.setText(currentUser.getJob());
        salaryText.setText(currentUser.getSalary());
        addressText.setText(currentUser.getAddress());
        phoneText.setText(currentUser.getPhoneNo());
        racesText.setText(currentUser.getRace());
        nationText.setText(currentUser.getNation());

        btnLogout = view.findViewById(R.id.btn_logout);
        editChildBtn = view.findViewById(R.id.btneditchild);

        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(),Login_page.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            getActivity().finish();
        });

        if(currentUser.getRole() == 2){
            editChildBtn.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);

        }

//        editChildBtn.setOnClickListener(v -> {
//            Intent intent = new Intent(getActivity(),EditChildActivity.class);
//            intent.putExtra("user", currentUser);
//            startActivity(intent);
//        });

        editChildBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),EditChildActivity.class);
                intent.putExtra("user", currentUser);
                startActivity(intent);
            }
        });

        adapter = new ChildRecyclerAdapter(DB.getPChilds(currentUser.getICNo()),getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        adapter.notifyDataSetChanged();

        editBtn = view.findViewById(R.id.btneditprofile);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),EditProfile_Activity.class);
                intent.putExtra("user", currentUser);
                //Log.i("user",currentUser.toString());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}