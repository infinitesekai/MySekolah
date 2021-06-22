package com.example.mysekolah.PersonalityCareerTest;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.mysekolah.R;

public class LoadingDialog {

    Activity activity;
    AlertDialog dialog;

    //constructor of loading dialog
    LoadingDialog(Activity myActivity) {
        activity = myActivity;
    }

    //start to loading dialog
    void startLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_progress, null));
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }

    //dismiss dialog
    void dismissDialog(){
        dialog.dismiss();
    }
}

