package com.example.mysekolah;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class SearchPage_Admin extends Fragment implements SearchView.OnQueryTextListener{

    SearchView searchView;
    ListView listView;
    ListViewAdapter adapter;
    String [] functionList;
    ArrayList<FunctionName> arraylist= new ArrayList<FunctionName>();

    private User currentUser;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_searchpage_admin, container, false);

        Bundle bundle = getArguments();
        currentUser = (User) bundle.getSerializable("user");

        searchView= (SearchView) v.findViewById(R.id.searchView);
        listView= (ListView) v.findViewById(R.id.listview);

        functionList= new String[] {"Review Application"};

        for(int i=0; i< functionList.length; i++){
            FunctionName functionName= new FunctionName(functionList[i]);
            arraylist.add(functionName);
        }


        // Pass results to ListViewAdapter Class
        adapter = new ListViewAdapter(getContext(), arraylist);

        // Binds the Adapter to the ListView
        listView.setAdapter(adapter);

        searchView.setOnQueryTextListener(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FunctionName function= adapter.getItem(position);
                String functionName= function.getFunctions();

                Log.d("function", functionName);
                Intent i;
                switch (functionName) {
                    case ("Review Application"):
                        i = new Intent(getActivity(), All_Enrolment.class);
                        i.putExtra("user", currentUser);
                        startActivity(i);
                        break;
                }
            }
        });
        return v;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        adapter.filter(text);
        return false;
    }
}