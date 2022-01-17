package com.example.runwith.background;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.runwith.R;
import com.example.runwith.activity.HomeActivity;

public class StepService extends Service implements SensorEventListener {
    private MyBinder mMyBinder = new MyBinder();
    public class MyBinder extends Binder {
        public StepService getService() {
            return StepService.this;
        }
    }

    SensorManager sensorManager;
    Sensor stepDetectorSensor;

    private int count;
    private StepCallback callback;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        stepDetectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if(stepDetectorSensor != null)
            sensorManager.registerListener(this, stepDetectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        stepDetectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if(stepDetectorSensor != null)
            sensorManager.registerListener(this, stepDetectorSensor, SensorManager.SENSOR_DELAY_NORMAL);

        Intent openIntent = new Intent(getApplicationContext(), HomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, openIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("channel", "noti", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager notificationManager = ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE));
            notificationManager.createNotificationChannel(channel);

            NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), "channel")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("runWith")
                    .setContentText(String.valueOf(count))
                    .setContentIntent(pendingIntent)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

            notificationManager.notify(1, notification.build());
            startForeground(1, notification.build());
        }

        return START_STICKY;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        unRegisterManager();
        if(callback != null)
            callback.onUnbindService();

        return super.onUnbind(intent);
    }

    public void unRegisterManager() {
        try{
            sensorManager.unregisterListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.values[0] == 1.0f) {
            count += event.values[0];
            if (callback != null)
                callback.onStepCallback(count);
        }
     }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void setCallback(StepCallback callback) {
        this.callback = callback;
    }

    public void Notification() {
    }
}
