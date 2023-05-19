package com.example.brightify;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    ImageButton button;

    boolean hascameraflash = false;
    boolean hasflashon = false;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        hascameraflash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (hascameraflash) {
                    if (hasflashon) {
                        hasflashon = false;
                        button.setImageResource(R.drawable.power_off);
                        try {
                            flashlightoff();
                        } catch (CameraAccessException e) {
                            throw new RuntimeException(e);
                        }

                    } else {
                        hasflashon = true;
                        button.setImageResource(R.drawable.power_on);
                        try {
                            flashlightOn();
                        } catch (CameraAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } else {
                    Toast.makeText(MainActivity.this, "no torch in your device", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void flashlightOn() throws CameraAccessException {
        @SuppressLint("ServiceCast") CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        String cameraId = cameraManager.getCameraIdList()[0];
        cameraManager.setTorchMode(cameraId, true);
        Toast.makeText(MainActivity.this, "Flashlight is on ", Toast.LENGTH_LONG).show();


    }

    private void flashlightoff() throws CameraAccessException {
        @SuppressLint("ServiceCast") CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        String cameraId = cameraManager.getCameraIdList()[0];
        cameraManager.setTorchMode(cameraId, false);
        Toast.makeText(MainActivity.this, "Flashlight is off ", Toast.LENGTH_LONG).show();

    }
}