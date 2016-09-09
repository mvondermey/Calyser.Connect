package com.connect.calyser.calyserconnect;

import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PhotoActivity extends AppCompatActivity {

    private Camera camera;
    private int cameraId = 0;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        //
        Button TakePhotoButton = (Button) findViewById(R.id.captureFront);
        //
        // do we have a camera?
        if (!getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Toast.makeText(this, "No camera on this device", Toast.LENGTH_LONG)
                    .show();
            TakePhotoButton.setVisibility(View.GONE);
        } else {
            cameraId = findFrontFacingCamera();
            if (cameraId < 0) {
                Toast.makeText(this, "No front facing camera found.",
                        Toast.LENGTH_LONG).show();
                TakePhotoButton.setVisibility(View.GONE);
            } else {
                camera = Camera.open(cameraId);
            }
        }
    }
        //
        private int findFrontFacingCamera() {
            int cameraId = -1;
            // Search for the front facing camera
            int numberOfCameras = Camera.getNumberOfCameras();
            for (int i = 0; i < numberOfCameras; i++) {
                Camera.CameraInfo info = new Camera.CameraInfo();
                Camera.getCameraInfo(i, info);
                if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    cameraId = i;
                    break;
                }
            }
            return cameraId;
        }
        //
        public void onClick(View view) {
            camera.takePicture(null, null,
                    new PhotoHandler(getApplicationContext()));
        }
    //
    }

