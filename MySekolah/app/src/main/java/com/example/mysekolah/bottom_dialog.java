package com.example.mysekolah;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class bottom_dialog extends BottomSheetDialogFragment {

    private TextView title,link,button_visit;
    private ImageView close;
    private String fetchedurl;
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view= inflater.inflate (R.layout.scan_bottom_dialog,container,false);

        title=view.findViewById(R.id.title);
        link=view.findViewById(R.id.link);
        button_visit=view.findViewById(R.id.visit);
        close=view.findViewById(R.id.close);

        title.setText(fetchedurl);

        button_visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i=new Intent("android.intent.action.view");
                Intent i=new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(fetchedurl));
                startActivity(i);
            }
        });


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
        }

        public void fetch(String url){
            ExecutorService executorService= Executors.newSingleThreadExecutor();
            Handler handler=new Handler(Looper.getMainLooper());
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    fetchedurl=url;
                }
            });

    }
}
