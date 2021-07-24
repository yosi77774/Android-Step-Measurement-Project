package com.example.steps2;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    SensorManager sensorManager;
    Sensor sensor;
    private TextView textView;
    private double MagnitudePrevious = 0;
    private Integer stepCount = 0;
    Intent intent;
    Intent svc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       svc=new Intent(this,StepsService.class);

       textView=(TextView)findViewById(R.id.textView);
       // Context context=MyApp.getContext();

        SharedPreferences sharedPreferences = this.getSharedPreferences("Steps2",this.MODE_PRIVATE);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
 }



    @Override
    protected void onResume() {
        super.onResume();
  //     Toast.makeText(this,"onResume",Toast.LENGTH_LONG).show();

      /*  intent = getIntent();
        textView.setText(intent.getStringExtra(MainActivity.EXTRA_MESSAGE));
    */}

    public void stop(View view) {
        stopService(svc);

        stepCount=0;

       // Context context = MyApp.getContext();

        SharedPreferences sharedPreferences = this.getSharedPreferences("Steps2",this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putInt("stepCount", stepCount);
        editor.commit();
    }

  //  @RequiresApi(api = Build.VERSION_CODES.O)
    public void started(View view) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            startForegroundService(svc);
//        }
//       else

        startService(svc);
    }


    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
     //   Context context=MyApp.getContext();
         sharedPreferences = this.getSharedPreferences("Steps2",this.MODE_PRIVATE);
        stepCount = sharedPreferences.getInt("stepCount", 0);
        textView.setText(stepCount.toString());

    }

    @Override
    protected void onDestroy() {
        //stopService(svc);
        super.onDestroy();
    }
}