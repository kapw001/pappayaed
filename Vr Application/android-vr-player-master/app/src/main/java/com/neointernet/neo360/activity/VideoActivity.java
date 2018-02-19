package com.neointernet.neo360.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.vrtoolkit.cardboard.CardboardActivity;
import com.neointernet.neo360.R;
import com.neointernet.neo360.RuntimePermissionRequest;
import com.neointernet.neo360.listener.CardboardEventListener;
import com.neointernet.neo360.listener.VideoTimeListener;
import com.neointernet.neo360.renderer.VideoRenderer;
import com.neointernet.neo360.view.MyCardboardView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by neo-202 on 2016-03-22.
 */
public class VideoActivity extends CardboardActivity implements VideoTimeListener, CardboardEventListener {

    private MyCardboardView view;
    private VideoRenderer renderer;
    private View barLayout;
    private ImageButton vrButton, playButton;
    private SeekBar videoSeekBar;

    private static final String[] READ_AND_WRITE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int RC_READ_AND_WRITE = 123;

    private static final String TAG = "VideoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = new MyCardboardView(VideoActivity.this);
        view.setSettingsButtonEnabled(false);
        view.setVRModeEnabled(false);
        view.setDistortionCorrectionEnabled(false);
        view.setAlignmentMarkerEnabled(false);
        setContentView(view);
        setCardboardView(view);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("pass"));

        methodRequiresTwoPermission();
    }

    private void methodRequiresTwoPermission() {


        if (RuntimePermissionRequest.checkMultiplePermission(this, READ_AND_WRITE)) {

            RuntimePermissionRequest.requestPermissionMultiple(this, READ_AND_WRITE, RC_READ_AND_WRITE, "Permission", "Please accept read and write permission to read internal storage ");

        } else {
            Intent intent = getIntent();


            String name = intent.getStringExtra("videoname");
            Log.e(TAG, "methodRequiresTwoPermission: " + name);

//        String path = "file:///android_asset/tourtojapan.mp4";
//        String path = "android.resource://" + getPackageName() + "/" + "R.raw." + name;
//            String path = "android.resource://" + getPackageName() + "/raw/" + name;

            String path = Environment.getExternalStorageDirectory().toString() + "/vrvideos/" + name;
//
//            Toast.makeText(this, "" + path, Toast.LENGTH_SHORT).show();

            renderer = new VideoRenderer(VideoActivity.this, path);
//        renderer = new VideoRenderer(VideoActivity.this, intent.getStringExtra("videopath"));
            view.setRenderer(renderer);
            view.setSurfaceRenderer(renderer);
            renderer.setVideoTimeListener(this);

            view.addCardboardEventListener(renderer);
            view.addCardboardEventListener(this);

//            renderer.setOnCompleteListener(new MediaPlayer.OnCompletionListener() {
//                @Override
//                public void onCompletion(MediaPlayer mp) {
//                    Toast.makeText(getApplicationContext(), "Completed", Toast.LENGTH_SHORT).show();
//
//                    mp.stop();
//                    mp.release();
//                    finish();
//                }
//            });


        }


    }

    @Override
    protected void onPause() {
        super.onPause();

        renderer.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        renderer.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == RC_READ_AND_WRITE) {

            if (RuntimePermissionRequest.verifyPermissions(grantResults)) {

                methodRequiresTwoPermission();

            } else {

                Toast.makeText(this, "Please accept read and write permission to read internal storage", Toast.LENGTH_SHORT).show();


            }

        }
    }

    @Override
    public void onVideoInit(int length) {
//        videoSeekBar.setMax(length);
    }

    @Override
    public void listenTime(int time) {
//        videoSeekBar.setProgress(time);
    }

    @Override
    public void onCardboardTouch(MotionEvent e) {
//        if (e.getAction() == MotionEvent.ACTION_DOWN) {
//            int visibility = barLayout.getVisibility();
//            if (visibility == View.INVISIBLE) {
//                barLayout.setVisibility(View.VISIBLE);
//            } else if (visibility == View.VISIBLE) {
//                barLayout.setVisibility(View.INVISIBLE);
//            }
//        }
    }


    // Our handler for received Intents. This will be called whenever an Intent
// with an action named "custom-event-name" is broadcasted.
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("message");
            Log.e("receiver", "Got message: " + message);

//            Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();

            if (message.equalsIgnoreCase("playpause")) {

                if (renderer != null) {
                    if (renderer.isPlaying()) {
                        Log.e(TAG, "onReceive: " + renderer.isPlaying());
//                        playButton.setImageResource(R.drawable.stop);
                        renderer.onPause();
                    } else {
//                        playButton.setImageResource(R.drawable.play);
                        Log.e(TAG, "onReceive: " + renderer.isPlaying());
                        renderer.onResume();
                    }
                }
            } else if (message.equalsIgnoreCase("stop")) {

                VideoActivity.this.finish();

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