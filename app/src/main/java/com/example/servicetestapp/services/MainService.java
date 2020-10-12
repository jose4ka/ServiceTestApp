package com.example.servicetestapp.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.servicetestapp.receivers.MainReceiver;

import java.util.concurrent.TimeUnit;

public class MainService extends Service {

    boolean isRunning;

    IBinder mBinder = new LocalBinder();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(), "Run service!", Toast.LENGTH_SHORT).show();
        isRunning = true;
        run();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
        Toast.makeText(getApplicationContext(), "Stop service!", Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private void run(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i<=5; i++) {
                    Log.d("MY TAG", "i = " + i);
                    try {
                        TimeUnit.SECONDS.sleep(1);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                stopSelf(); //Останавливает сам себя
            }
        }).start();

    }


    public class LocalBinder extends Binder {
        public MainService getServerInstance() {
            return MainService.this;
        }
    }


}
