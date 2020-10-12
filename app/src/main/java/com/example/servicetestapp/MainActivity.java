package com.example.servicetestapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.servicetestapp.receivers.MainReceiver;
import com.example.servicetestapp.services.MainService;

public class MainActivity extends AppCompatActivity {

    private Button btnStart, btnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService();
            }
        });

        if (!checkPermissions()){
            requestPerms();
        }


    }

    private void startService(){
        Intent intent = new Intent(this, MainService.class);

        if (!isMainServiceRunning(MainService.class)){
            MainReceiver mainReceiver = new MainReceiver();
            startService(intent);
        }
    }

    private void stopService(){
        Intent intent = new Intent(this, MainService.class);

        if (isMainServiceRunning(MainService.class)){
            stopService(intent);
        }
    }




    private boolean isMainServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    //Проверяем все интересующие нас разрешения
    //Если не получены - получаем их
    private boolean checkPermissions(){

        if((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECEIVE_BOOT_COMPLETED) == PackageManager.PERMISSION_DENIED)){
            return false;
        }
        else {
            return true;
        }


    }

    //Сразу запрашиваем несколько разрешений
    private void requestPerms(){
        String[] perm = new String[]{Manifest.permission.RECEIVE_BOOT_COMPLETED};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            ActivityCompat.requestPermissions(this,perm,123);
        }
    }

}