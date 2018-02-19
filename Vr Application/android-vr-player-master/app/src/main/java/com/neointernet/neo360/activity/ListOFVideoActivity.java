package com.neointernet.neo360.activity;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.vrtoolkit.cardboard.CardboardActivity;
import com.neointernet.neo360.R;
import com.neointernet.neo360.Videos;
import com.neointernet.neo360.adapter.MyRecyclerViewAdapter;
import com.neointernet.neo360.listener.CardboardEventListener;
import com.neointernet.neo360.listener.VideoTimeListener;
import com.neointernet.neo360.renderer.TeacherVideoRenderer;
import com.neointernet.neo360.renderer.VideoRenderer;
import com.neointernet.neo360.view.MyCardboardView;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ListOFVideoActivity extends CardboardActivity implements MyRecyclerViewAdapter.OnItemClickListener, VideoTimeListener, CardboardEventListener {

    private static final String TAG = "ListOFVideoActivity";
    private final int portNum = 3238;
    private InetAddress ip = null;
    private NetworkInterface networkInterface = null;
    private MulticastSocket socket;
    private InetAddress group;
    private MyCardboardView vrVideoView;
    private TeacherVideoRenderer renderer;
    private List<Videos> mList;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter mRecyclerAdapter;
    private boolean isPaused = false;

    private RelativeLayout main;

    private TextView error;

    private LinearLayout second;

    private ImageButton vrButton, playButton;
    private SeekBar videoSeekBar;
    private View barLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_videos);

        vrVideoView = (MyCardboardView) findViewById(R.id.video_view);
        vrVideoView.setSettingsButtonEnabled(false);
        vrVideoView.setVRModeEnabled(false);
        vrVideoView.setDistortionCorrectionEnabled(false);
        vrVideoView.setAlignmentMarkerEnabled(false);
        setCardboardView(vrVideoView);
//        String path = "android.resource://" + getPackageName() + "/" + R.raw.ajourneytovenus;
//        renderer = new TeacherVideoRenderer(this, path);
//        renderer = new VideoRenderer(VideoActivity.this, intent.getStringExtra("videopath"));
        vrVideoView.setRenderer(renderer);
        vrVideoView.setSurfaceRenderer(renderer);
        renderer.setVideoTimeListener(this);

        vrVideoView.addCardboardEventListener(renderer);
        vrVideoView.addCardboardEventListener(this);

        vrVideoView.setVisibility(View.VISIBLE);
        LayoutInflater layoutInflater = getLayoutInflater();
        barLayout = (View) layoutInflater.inflate(R.layout.video_controller1, null);
        addContentView(barLayout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        playButton = (ImageButton) findViewById(R.id.playButton);

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
        main = (RelativeLayout) findViewById(R.id.main);
        second = (LinearLayout) findViewById(R.id.second);
        error = (TextView) findViewById(R.id.error);

        Retry();


    }


    private void Retry() {
        if (getWifiManager().isWifiEnabled()) {

            //wifi is enabled
            main.setVisibility(View.VISIBLE);
            second.setVisibility(View.GONE);
            enableVideoList();

        } else {

            main.setVisibility(View.GONE);
            second.setVisibility(View.VISIBLE);
            Toast.makeText(this, "There is no wifi please enabled ", Toast.LENGTH_SHORT).show();

        }
    }


    private void enableVideoList() {
        String networkSSID = "TeacherStudent";
        if (getWifiManager() != null) {
            WifiManager.MulticastLock lock =
                    getWifiManager().createMulticastLock(networkSSID);
            lock.setReferenceCounted(true);
            lock.acquire();

            try {
                if (socket == null) {

                    Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface.getNetworkInterfaces();
                    while (enumNetworkInterfaces.hasMoreElements()) {

                        networkInterface = enumNetworkInterfaces.nextElement();
                        Enumeration<InetAddress> enumInetAddress = networkInterface.getInetAddresses();

                        while (enumInetAddress.hasMoreElements()) {
                            InetAddress inetAddress = enumInetAddress.nextElement();

                            if (inetAddress.isSiteLocalAddress()) {
                                ip = inetAddress;
                                break;
                            }
                        }
                        if (ip != null) {
                            break;
                        }


                    }
                    socket = new MulticastSocket(portNum);
                    Log.e(TAG, "onCreate: port " + socket.getPort() + "   local post " + socket.getLocalPort());
                    socket.setInterface(ip);
                    socket.setBroadcast(true);

                    group = InetAddress.getByName("224.0.0.1");
                    socket.joinGroup(new InetSocketAddress(group, portNum), networkInterface);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Log.e(networkSSID, "Unable to acquire multicast lock");
            Toast.makeText(getApplicationContext(), "Unable to acquire multicast lock", Toast.LENGTH_SHORT).show();

//                finish();
        }

        mList = new ArrayList<>();

        mList.add(new Videos("ajourneytovenus"));
        mList.add(new Videos("atourtothecell"));
        mList.add(new Videos("thepyramids"));
        mList.add(new Videos("tourtojapan"));
        mList.add(new Videos("digestivesystem"));
        mList.add(new Videos("skeletalsystem"));

        mRecyclerAdapter = new MyRecyclerViewAdapter(this, mList);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mRecyclerAdapter);
        recyclerView.setHasFixedSize(true);


    }

    private void playVideos(String videoName) {
        String path = "android.resource://" + getPackageName() + "/" + "R.raw." + videoName;
        renderer = new TeacherVideoRenderer(this, path);
//        renderer = new VideoRenderer(VideoActivity.this, intent.getStringExtra("videopath"));
//        vrVideoView.setRenderer(renderer);
//        vrVideoView.setSurfaceRenderer(renderer);
//        renderer.setVideoTimeListener(this);
//
//        vrVideoView.addCardboardEventListener(renderer);
//        vrVideoView.addCardboardEventListener(this);
//
//        LayoutInflater layoutInflater = getLayoutInflater();
//        barLayout = (View) layoutInflater.inflate(R.layout.video_controller1, null);
//        addContentView(barLayout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//
//        playButton = (ImageButton) findViewById(R.id.playButton);
//
//        playButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (renderer.isPlaying()) {
//                    playButton.setImageResource(R.drawable.stop);
//                    renderer.onPause();
//                } else {
//                    playButton.setImageResource(R.drawable.play);
//                    renderer.onResume();
//                }
//            }
//        });


    }

    @Override
    protected void onPause() {
        super.onPause();

        if (getWifiManager().isWifiEnabled()) {

            new sendMessage("playpause").execute();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Resume the 3D rendering.


        if (getWifiManager().isWifiEnabled()) {

            try {
                socket = new MulticastSocket(portNum);
                socket.setInterface(ip);
                socket.setBroadcast(true);

                group = InetAddress.getByName("224.0.0.1");
                socket.joinGroup(new InetSocketAddress(group, portNum), networkInterface);
            } catch (IOException e) {
                e.printStackTrace();
            }

//            vrVideoView.playVideo();
//            new sendMessage("play").execute();
        } else {

            Toast.makeText(this, "There is no wifi please enabled ", Toast.LENGTH_SHORT).show();

        }

    }

    public WifiManager getWifiManager() {
        return (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    public void onRetry(View view) {

        Retry();

    }

    @Override
    public void onVideoInit(int length) {

    }

    @Override
    public void listenTime(int time) {

    }

    @Override
    public void onCardboardTouch(MotionEvent e) {

    }

    private class sendMessage extends AsyncTask<Void, Void, Boolean> {

        String textMsg;

        sendMessage(String message) {

            textMsg = message;
        }

        @Override
        protected Boolean doInBackground(Void... params) {


            byte[] data = textMsg.getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length, group, portNum);

            try {
                if (socket != null) {

                    socket.send(packet);
                    return true;
                }
                return false;

            } catch (IOException e) {
                return false;
            } catch (RuntimeException e) {
                return false;
            }

        }
    }


    @Override
    public void OnItem(View v, int position) {

//        Toast.makeText(this, "called ", Toast.LENGTH_SHORT).show();


        final String videoName = mList.get(position).getVideoname();

        new sendMessage(videoName).execute();


        new Handler().post(new Runnable() {
            @Override
            public void run() {

                playVideos(videoName);
                vrVideoView.setVisibility(View.VISIBLE);
            }
        });

    }


}
