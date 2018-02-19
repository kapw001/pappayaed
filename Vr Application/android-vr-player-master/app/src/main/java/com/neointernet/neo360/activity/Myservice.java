package com.neointernet.neo360.activity;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.util.Enumeration;

import static org.rajawali3d.util.RajLog.TAG;

/**
 * Created by yasar on 1/2/18.
 */

public class Myservice extends Service {
    final int portNum = 45238;
    InetAddress ip = null;
    NetworkInterface networkInterface = null;
    ServerSocket serverSocket;

    private MulticastSocket socket;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        Log.e(TAG, "onStart: ");

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

//        if (wifi.isWifiEnabled()) {

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

                Log.e(TAG, "onStart: ccc");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.getMessage();
        }


        ReceiverMessage recvMsgThread = new ReceiverMessage();
        recvMsgThread.execute((Void) null);


//        } else {
//
//        Toast.makeText(this, "There is nTest", Toast.LENGTH_SHORT).show();
//        }


        return START_STICKY;
    }

    private class ReceiverMessage extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {

            Thread newThread = new Thread() {

                public void run() {
                    while (true) {

                        Log.e(TAG, "run: ReceiverMessage ReceiverMessage ReceiverMessage ReceiverMessage ReceiverMessage ReceiverMessage");


                        byte[] recvPkt = new byte[1024];
                        DatagramPacket recv = new DatagramPacket(recvPkt, recvPkt.length);
                        try {
                            socket.receive(recv);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        final String medd = new String(recvPkt, 0, recv.getLength());
//                        Toast.makeText(Myservice.this, "" + medd, Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "run: " + medd);
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

    private void playVideos(String s) {

        Log.e(TAG, "playVideos: " + s);
//        Toast.makeText(this, "" + s, Toast.LENGTH_SHORT).show();

        startActivity(new Intent(getApplicationContext(), VideoActivity.class).putExtra("videoname", s).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));


    }

    private void playpause() {

        Intent intent = new Intent("pass");
        // You can also include some extra data.
        intent.putExtra("message", "playpause");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

    }

    @Override
    public void onDestroy() {

    }


}
