package com.example.mysekolah;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.mysekolah.SchoolEnrollment.SchoolEnroll;

import java.util.Objects;

//Parent Home Page
public class HomePage extends Fragment implements View.OnClickListener {


    private static final String TAG = HomePage.class.getSimpleName();
    public CardView enrollment, childperform;
    private User currentUser;
    VideoView videoView;
    private boolean mVolumePlaying = false;
    AppCompatImageView volume;

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);
        TextView nameText = v.findViewById(R.id.user_name);
        Bundle bundle = getArguments();

        currentUser = (User) bundle.getSerializable("user");
        nameText.setText("Hi, " + currentUser.getName());
        enrollment = (CardView) v.findViewById(R.id.enrollmentCard);
        childperform = (CardView) v.findViewById(R.id.CheckChildPerfCard);
        volume = (AppCompatImageView) v.findViewById(R.id.volume);

        //set the video
        videoView = (VideoView) v.findViewById(R.id.news);
        //set the video path that in the raw folder
        String videoPath =
                "android.resource://" +
                        Objects.requireNonNull(getActivity()).getPackageName() +
                        "/" +
                        R.raw.usaha_selenggara_encoded;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        //Mediacontroller contains the buttons like "Play/Pause"that we need
        MediaController mediaController = new MediaController(getActivity());
        //set the video to the media controller
        videoView.setMediaController(mediaController);
        //set the video to start automatically
        videoView.start();
        //set the video a callback to be invoked, take a listener as parameter.
        videoView.setOnPreparedListener(this::PreparedListener);

        //on click listener on card view
        enrollment.setOnClickListener(this);
        childperform.setOnClickListener(this);


        return v;

    }

    //the listener parameter of video
    private void PreparedListener(MediaPlayer mp) {
        try {
            //mute the volume of the video when the user enter the page
            mp.setVolume(0f, 0f);
            //if mute volume is true, the mute icon is shown
            if (!mVolumePlaying) {
                volume.setImageResource(R.drawable.ic_baseline_volume_off_24);
            }

            //set the looping of video on
            mp.setLooping(true);
            mp.start();

            //user can click on the image to change the volume (mute or unmute)
            volume.setOnClickListener(v -> {

                //if mute is false, and the icon is clicked
                // means the user want to mute the volume, so mute icon is shown
                if (mVolumePlaying) {
                    Log.d(TAG, "setVolume OFF"); //text show in console to double check
                    volume.setImageResource(R.drawable.ic_baseline_volume_off_24);
                    mp.setVolume(0F, 0F);
                }
                //else if mute is true, and the icon is clicked
                // means the user want to unmute the volume, so unmute icon is shown
                else {
                    Log.d(TAG, "setVolume ON"); //text show in console to double check
                    volume.setImageResource(R.drawable.ic_baseline_volume_up_24);
                    mp.setVolume(1F, 1F);
                }
                //when the clicking is finished, set the mVolumePlaying to opposite (due to the if else above)
                //so the the difference can be observed the click is functional
                mVolumePlaying = !mVolumePlaying;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //start intent to navigate to respective page
    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()) {
            case R.id.enrollmentCard:
                i = new Intent(getActivity(), SchoolEnroll.class);
                i.putExtra("user", currentUser);
                startActivity(i);
                break;
            case R.id.CheckChildPerfCard:
                i = new Intent(getActivity(), child_performance.class);
                i.putExtra("user", currentUser);
                startActivity(i);
                break;

        }
    }


}