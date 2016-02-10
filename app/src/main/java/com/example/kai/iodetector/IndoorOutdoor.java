package com.example.kai.iodetector;

import android.content.Context;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.widget.TextView;
import android.widget.Toast;

public class IndoorOutdoor extends AppCompatActivity implements SensorEventListener {

    TextView txt1, txt2, txt4, txt5;
    TextView light, mag, acc, result;
    public double light_val, mag_val,acc_val;
    SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indoor_outdoor);

        Typeface tf = Typeface.createFromAsset(getAssets(),"RobotoCondensed-Regular.ttf");

        txt1 = (TextView)findViewById(R.id.txt1);
        txt1.setTypeface(tf);
        txt2 = (TextView)findViewById(R.id.txt2);
        txt2.setTypeface(tf);
        txt4 = (TextView)findViewById(R.id.txt4);
        txt4.setTypeface(tf);
        txt5 = (TextView)findViewById(R.id.txt5);
        txt5.setTypeface(tf);

        light = (TextView)findViewById(R.id.light_value);
        mag = (TextView)findViewById(R.id.mag_value);
        acc = (TextView)findViewById(R.id.acc_value);
        result = (TextView)findViewById(R.id.result);

        light.setTypeface(tf);
        acc.setTypeface(tf);
        result.setTypeface(tf);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

    }

    @Override
    protected void onResume()
    {
        super.onResume();

        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
                SensorManager.SENSOR_DELAY_GAME);

        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_GAME);

        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onStop()
    {
        sensorManager.unregisterListener(this);
        super.onStop();
    }



    @Override
    public void onSensorChanged(SensorEvent event) {

        float[] values = event.values;
        int sensorType = event.sensor.getType();
        StringBuilder sb = null;

        switch (sensorType)
        {
            case Sensor.TYPE_MAGNETIC_FIELD:
                sb = new StringBuilder();
                mag_val = Math.sqrt(values[0]*values[0]+values[1]*values[1]+values[2]*values[2]);
                sb.append(mag_val);
                mag.setText(sb.toString()+" uT");
                break;

            case Sensor.TYPE_LIGHT:
                sb = new StringBuilder();
                light_val = values[0];
                sb.append(light_val);
                light.setText(sb.toString()+" Lux");
                break;

            case Sensor.TYPE_ACCELEROMETER:
                sb = new StringBuilder();
                acc_val = Math.sqrt(values[0]*values[0]+values[1]*values[1]+values[2]*values[2])/10;
                sb.append(acc_val);
                acc.setText(sb.toString()+" m/s2");
                break;
        }

        if(light_val>900){
            result.setText("OUTDOOR");
        }
        else{
            if(mag_val>78){
                if(acc_val>1.3){
                    result.setText("OUTDOOR_NIGHT");
                }
                else{
                    result.setText("OUTDOOR_BUS");
                }
            }
            else{
                result.setText("INDOOR");
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
