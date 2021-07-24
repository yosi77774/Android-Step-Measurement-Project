package com.example.steps2;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.SENSOR_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class StepsCont implements Runnable{


    SensorManager sensorManager;
    Sensor sensor;
    private double MagnitudePrevious = 0;
    private Integer stepCount = 0;
    private TextView textView;
    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    Context context;

    @Override
    public void run() {

     //  sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
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

                    if (MagnitudeDelta > 5) {
                        stepCount++;
                      //  Context context=get;
                        SharedPreferences sharedPreferences = context.getSharedPreferences("Steps2",context.MODE_PRIVATE);
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
        //Toast.makeText(this, st, Toast.LENGTH_LONG).show();

        sensorManager.registerListener(stepDetector, sensor, SensorManager.SENSOR_DELAY_NORMAL);

    }




}
