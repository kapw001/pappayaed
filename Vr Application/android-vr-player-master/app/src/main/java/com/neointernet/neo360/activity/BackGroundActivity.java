package com.neointernet.neo360.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.neointernet.neo360.R;
import com.neointernet.neo360.RuntimePermissionRequest;

public class BackGroundActivity extends AppCompatActivity {

    private static final String[] READ_AND_WRITE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int RC_READ_AND_WRITE = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_ground);
        methodRequiresTwoPermission();
    }

    private void methodRequiresTwoPermission() {


        if (RuntimePermissionRequest.checkMultiplePermission(this, READ_AND_WRITE)) {

            RuntimePermissionRequest.requestPermissionMultiple(this, READ_AND_WRITE, RC_READ_AND_WRITE, "Permission", "Please accept read and write permission to read internal storage ");

        } else {
            stopService(new Intent(this, Myservice.class));
            startService(new Intent(this, Myservice.class));

        }


//        startService(new Intent(this, Myservice.class));

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onPause() {
        super.onPause();

        stopService(new Intent(this, Myservice.class));
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
}
