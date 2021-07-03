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
    private Button editBtn,editChildBtn,btnLogout;//edit child button,edit profile button,logout button
    private User currentUser;//current user

    private TextView icNoText;//ic number text
    private TextView nameText;//name text
    private TextView genderText;//gender text
    private TextView racesText;//races text
    private TextView nationText;//nation text
    private TextView jobText;//job text
    private TextView salaryText;//salary text
    private TextView addressText;//address text
    private TextView phoneText;//phone text
    private TextView scroll_h;//scroll message
    private RecyclerView recyclerView;//recycler view for children card
    private ChildRecyclerAdapter adapter;//child recycler adapter
    private DatabaseAccess DB;//database


   @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //get bundle for current user
        Bundle bundle = getArguments();
        currentUser = (User) bundle.getSerializable("user");

       //initiate database access and open database
        DB = DatabaseAccess.getInstance(getContext());
        DB.open();
        //init view
        initViews(view);
        return view;
    }
    //init views
    private void initViews(View view) {

        //reference to view by id
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
        scroll_h = view.findViewById(R.id.scroll_h);

        //get intent for current user
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

        //on click listener for logout button
        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(),Login_page.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            getActivity().finish();
        });
        //if the user role is student,hind two components
        if(currentUser.getRole() != 1){
            editChildBtn.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            scroll_h.setVisibility(View.GONE);
        }
        //on click listener for edit child button
        editChildBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),EditChildActivity.class);
                intent.putExtra("user", currentUser);
                startActivity(intent);
            }
        });

        //children recycler adapter
        adapter = new ChildRecyclerAdapter(DB.getPChilds(currentUser.getICNo()),getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        adapter.notifyDataSetChanged();

        editBtn = view.findViewById(R.id.btneditprofile);

        //on click listener for edit button
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),EditProfile_Activity.class);
                intent.putExtra("user", currentUser);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}