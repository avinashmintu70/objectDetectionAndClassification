package com.example.objectdetection;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button;
    protected int openchooser = 0;
    protected int opencam = 0;
    private static final int PERMISSION_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        handlePermission();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button Camera = findViewById(R.id.button);
        Button Upload = findViewById(R.id.button2);

        Camera.setOnClickListener(this);
        Upload.setOnClickListener(this);
    }

    private void handlePermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            // Don't need the permission less than M
            openchooser = 1;
            opencam = 1;
            return;
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PermissionChecker.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PermissionChecker.PERMISSION_GRANTED) {
            // its fine..
            openchooser = 1;
            opencam = 1;
        }
        else{
            String [] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA};
            //show popup for runtime permission.
            requestPermissions(permissions,PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // permission was granted.
                    openchooser = 1;
                    opencam = 1;
                }
                else{
                    //permission was denied
                    Toast.makeText(this,"PERMISSSION DENIED!!",Toast.LENGTH_SHORT).show();
                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.button:
                if(opencam == 0){
                    handlePermission();
                }
                else{
                    openCameraActivity();
                }
                break;
            case R.id.button2:
                if(openchooser == 0){
                    handlePermission();
                }
                else{
                    openUploadImage();
                }
                break;
        }
    }

    private void openCameraActivity() {
        Intent intent = new Intent(this, DetectorActivity.class);
        startActivity(intent);
    }

    private void openUploadImage() {
        Intent intent = new Intent(this,UploadImage.class);
        startActivity(intent);
    }
}
