package com.example.steps2;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.IBinder;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import static com.example.steps2.App.CHANNEL_ID;

public class StepsService extends Service {

    SensorManager sensorManager;
    Sensor sensor;
    private double MagnitudePrevious = 0;
    private Integer stepCount = 0;
    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    @Override
    public void onCreate() {

       Thread thread = new Thread(new Sp());
        thread.start();

    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        return super.onStartCommand(intent, flags, startId);

     Intent notificationIntent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0);

        Notification notification = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setContentTitle("StepsService")
                .setContentText(""+stepCount)
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1,notification);
        //startForegroundService(intent);



        return START_STICKY;
    }




    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public class Sp extends Thread {
        public void run() {
            sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

            SensorEventListener stepDetector = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent sensorEvent) {


                    if (sensorEvent != null) {
                        float x_acceleration = sensorEvent.values[0];
                        float y_acceleration = sensorEvent.values[1];
                        float z_acceleration = sensorEvent.values[2];

                        double Magnitude = Math.sqrt(x_acceleration * x_acceleration + y_acceleration * y_acceleration + z_acceleration * z_acceleration);
                        double MagnitudeDelta = Magnitude - MagnitudePrevious;
                        MagnitudePrevious = Magnitude;

                        if (MagnitudeDelta > 4) {
                            stepCount++;
                            Context context = getBaseContext();
                            SharedPreferences sharedPreferences = context.getSharedPreferences("Steps2", context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.clear();
                            editor.putInt("stepCount", stepCount);
                            editor.commit();

                        }

                    }

                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int i) {
                }
            };
            String st = String.format("%d", stepCount);
          //  Toast.makeText(this, st, Toast.LENGTH_LONG).show();

            sensorManager.registerListener(stepDetector, sensor, SensorManager.SENSOR_DELAY_NORMAL);

        }
    }

}
