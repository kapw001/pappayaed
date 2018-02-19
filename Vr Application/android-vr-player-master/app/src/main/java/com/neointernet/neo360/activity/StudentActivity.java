package com.neointernet.neo360.activity;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.vrtoolkit.cardboard.CardboardActivity;
import com.neointernet.neo360.R;
import com.neointernet.neo360.listener.CardboardEventListener;
import com.neointernet.neo360.listener.VideoTimeListener;
import com.neointernet.neo360.renderer.StudentVideoRenderer;
import com.neointernet.neo360.renderer.VideoRenderer;
import com.neointernet.neo360.view.MyCardboardView;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.util.Enumeration;

import static org.rajawali3d.util.RajLog.TAG;

public class StudentActivity extends CardboardActivity implements VideoTimeListener, CardboardEventListener {


    private MyCardboardView view;
    private StudentVideoRenderer renderer;

    private String videoname;

    final int portNum = 3238;
    InetAddress ip = null;
    NetworkInterface networkInterface = null;
    ServerSocket serverSocket;

    private MulticastSocket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_student);

        WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        String networkSSID = "TeacherStudent";
        if (wifi != null) {

            WifiManager.MulticastLock lock =
                    wifi.createMulticastLock(networkSSID);
            lock.setReferenceCounted(true);
            lock.acquire();
        } else {
            Log.e(networkSSID, "Unable to acquire multicast lock");
            Toast.makeText(getApplicationContext(), "Unable to acquire multicast lock", Toast.LENGTH_SHORT).show();

//            finish();
        }

        if (wifi.isWifiEnabled()) {

            view = new MyCardboardView(this);
            view.setSettingsButtonEnabled(false);
            view.setVRModeEnabled(false);
            view.setDistortionCorrectionEnabled(false);
            view.setAlignmentMarkerEnabled(false);
            setContentView(view);
            setCardboardView(view);
//        String path = "file:///android_asset/tourtojapan.mp4";


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

                    InetAddress group = InetAddress.getByName("224.0.0.1");
                    socket.joinGroup(new InetSocketAddress(group, portNum), networkInterface);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.getMessage();
            }


            ReceiverMessage recvMsgThread = new ReceiverMessage();
            recvMsgThread.execute((Void) null);


        } else {

            Toast.makeText(this, "There is no wifi", Toast.LENGTH_SHORT).show();
        }

    }

    private void playVideos(String videoname) {
        String path = "android.resource://" + getPackageName() + "/" + "R.raw." + videoname;
        renderer = new StudentVideoRenderer(StudentActivity.this, path);
//        renderer = new VideoRenderer(VideoActivity.this, intent.getStringExtra("videopath"));
        view.setRenderer(renderer);
        view.setSurfaceRenderer(renderer);
        renderer.setVideoTimeListener(this);

        view.addCardboardEventListener(renderer);
        view.addCardboardEventListener(this);
    }

    private class ReceiverMessage extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {

            Thread newThread = new Thread() {

                public void run() {
                    while (true) {
                        byte[] recvPkt = new byte[1024];
                        DatagramPacket recv = new DatagramPacket(recvPkt, recvPkt.length);
                        try {
                            socket.receive(recv);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        final String medd = new String(recvPkt, 0, recv.getLength());

                        if (medd.equalsIgnoreCase("playpause")) {
                            playpause();
                        } else {
                            playVideos(medd);
                        }

                    }
                }
            };
            newThread.start();
            return null;
        }
    }

    private void playpause() {

        if (renderer != null) {
            if (renderer.isPlaying()) {
                renderer.onPause();
            } else {
                renderer.onResume();
            }
        } else {
            Toast.makeText(this, "There is no render view", Toast.LENGTH_SHORT).show();
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
}
