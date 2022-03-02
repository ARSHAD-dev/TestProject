package com.example.testproject;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Arshad khan......
 */

public class HardwareActivity  extends Activity{

    private Camera camera;
    private Camera.Parameters parameters;
    private ToggleButton tb_torch;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hardware);
        checkRunTimePermissions();
        initComponents();
        settingUpListeners();
    }

    private void checkRunTimePermissions() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(AppConstant.PERMISSIONS[0]) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(AppConstant.PERMISSIONS, AppConstant.PERMISSION_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case AppConstant.PERMISSION_CODE:{
                if(grantResults[0] != PackageManager.PERMISSION_GRANTED)
                    finish();
                break;
            }
        }
    }

    private void settingUpListeners() {
        tb_torch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tb_torch.isChecked()){
                    //torch on code
                    torchOn();
                } else{
                    //torch off

                    torchOff();
                }
            }
        });
    }

    private void initComponents() {
        tb_torch = findViewById(R.id.tb1_torch);
    }

    @Override
    protected void onStart() {
        super.onStart();
        openCamera();
    }

    @Override
    protected void onStop() {
        super.onStop();
        closeCamera();
    }

    private void openCamera(){
        camera = Camera.open();
        parameters = camera.getParameters();
    }
    private void closeCamera(){
        camera.release();
    }
    private void torchOff(){
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(parameters);
        camera.stopPreview();
    }
    private void torchOn(){
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(parameters);
        camera.startPreview();
    }
}