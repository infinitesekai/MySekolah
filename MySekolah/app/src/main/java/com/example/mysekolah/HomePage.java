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
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.mysekolah.SchoolEnrollment.SchoolEnroll;

import java.util.Objects;

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

        //set the video announcement
        videoView = (VideoView) v.findViewById(R.id.news);
        String videoPath =
                "android.resource://" +
                        Objects.requireNonNull(getActivity()).getPackageName() +
                        "/" +
                        R.raw.pencapaian_spm2020;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        MediaController mediaController = new MediaController(getActivity());
        videoView.setMediaController(mediaController);
        videoView.start();

        videoView.setOnPreparedListener(this::PreparedListener);

        enrollment.setOnClickListener(this);
        childperform.setOnClickListener(this);


        return v;

    }

    private void PreparedListener(MediaPlayer mp) {
        try {
            mp.setVolume(0f, 0f);
            if (!mVolumePlaying) {
                volume.setImageResource(R.drawable.ic_baseline_volume_off_24);
            }
            mp.setLooping(true);
            mp.start();
            volume.setOnClickListener(v -> {
                if (mVolumePlaying) {
                    Log.d(TAG, "setVolume OFF");
                    volume.setImageResource(R.drawable.ic_baseline_volume_off_24);
                    mp.setVolume(0F, 0F);
                } else {
                    Log.d(TAG, "setVolume ON");
                    volume.setImageResource(R.drawable.ic_baseline_volume_up_24);
                    mp.setVolume(1F, 1F);
                }
                mVolumePlaying = !mVolumePlaying;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


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