package com.neointernet.neo360.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.google.vrtoolkit.cardboard.CardboardActivity;
import com.neointernet.neo360.R;
import com.neointernet.neo360.listener.CardboardEventListener;
import com.neointernet.neo360.listener.VideoTimeListener;
import com.neointernet.neo360.renderer.VideoRenderer;
import com.neointernet.neo360.view.MyCardboardView;

/**
 * Created by neo-202 on 2016-03-22.
 */
public class VideoActivityBackup extends CardboardActivity implements VideoTimeListener, CardboardEventListener {

    private MyCardboardView view;
    private VideoRenderer renderer;
    private View barLayout;
    private ImageButton vrButton, playButton;
    private SeekBar videoSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = new MyCardboardView(VideoActivityBackup.this);
        view.setSettingsButtonEnabled(false);
        view.setVRModeEnabled(false);
        view.setDistortionCorrectionEnabled(false);
        view.setAlignmentMarkerEnabled(false);
        setContentView(view);
        setCardboardView(view);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("pass"));

        Intent intent = getIntent();


        String name = intent.getStringExtra("videoname");

//        String path = "file:///android_asset/tourtojapan.mp4";
//        String path = "android.resource://" + getPackageName() + "/" + "R.raw." + name;
        String path = "android.resource://" + getPackageName() + "/raw/" + name;

        renderer = new VideoRenderer(VideoActivityBackup.this, path);
//        renderer = new VideoRenderer(VideoActivity.this, intent.getStringExtra("videopath"));
        view.setRenderer(renderer);
        view.setSurfaceRenderer(renderer);
        renderer.setVideoTimeListener(this);

        view.addCardboardEventListener(renderer);
        view.addCardboardEventListener(this);

        LayoutInflater layoutInflater = getLayoutInflater();
        barLayout = (View) layoutInflater.inflate(R.layout.video_controller, null);
        addContentView(barLayout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        vrButton = (ImageButton) findViewById(R.id.vrButton);
        playButton = (ImageButton) findViewById(R.id.playButton);
        videoSeekBar = (SeekBar) findViewById(R.id.videoSeekBar);

        vrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.changeVRMode();
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (renderer.isPlaying()) {
                    playButton.setImageResource(R.drawable.stop);
                    renderer.onPause();
                } else {
                    playButton.setImageResource(R.drawable.play);
                    renderer.onResume();
                }
            }
        });

        videoSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    renderer.setMediaPlayerSeekTo(progress);
                    seekBar.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onVideoInit(int length) {
        videoSeekBar.setMax(length);
    }

    @Override
    public void listenTime(int time) {
        videoSeekBar.setProgress(time);
    }

    @Override
    public void onCardboardTouch(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            int visibility = barLayout.getVisibility();
            if (visibility == View.INVISIBLE) {
                barLayout.setVisibility(View.VISIBLE);
            } else if (visibility == View.VISIBLE) {
                barLayout.setVisibility(View.INVISIBLE);
            }
        }
    }


    // Our handler for received Intents. This will be called whenever an Intent
// with an action named "custom-event-name" is broadcasted.
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("message");
            Log.d("receiver", "Got message: " + message);

            if (message.equalsIgnoreCase("playpause")) {

                if (renderer != null) {
                    if (renderer.isPlaying()) {
                        playButton.setImageResource(R.drawable.stop);
                        renderer.onPause();
                    } else {
                        playButton.setImageResource(R.drawable.play);
                        renderer.onResume();
                    }
                }
            }

        }
    };

    @Override
    protected void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

}