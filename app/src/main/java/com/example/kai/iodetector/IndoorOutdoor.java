package com.example.kai.iodetector;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class IndoorOutdoor extends AppCompatActivity implements SensorEventListener {

    TextView txt1, txt2, txt4, txt5;
    TextView light, mag, acc, result;
    public double light_val, mag_val,acc_val;
    SensorManager sensorManager;
    private final int CODE_PERMISSIONS = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indoor_outdoor);
        String[] neededPermissions = new String[]{
            Manifest.permission.CHANGE_WIFI_STATE,
                    Manifest.permission.ACCESS_WIFI_STATE,
                    Manifest.permission.ACCESS_COARSE_LOCATION
        };
        ActivityCompat.requestPermissions(this, neededPermissions, CODE_PERMISSIONS);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent map_intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(map_intent);
            }
        });

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_indoor_outdoor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.compass) {
            Intent compass_intent = new Intent(getApplicationContext(), Compass.class);
            startActivity(compass_intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //Handle if any of the permissions are denied, in grantResults
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
    protected void onStop() {
        sensorManager.unregisterListener(this);
        super.onStop();
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        float[] values = event.values;
        int sensorType = event.sensor.getType();
        //StringBuilder sb = null;

        switch (sensorType)
        {
            case Sensor.TYPE_MAGNETIC_FIELD:
                //sb = new StringBuilder();
                mag_val = Math.sqrt(values[0]*values[0]+values[1]*values[1]+values[2]*values[2]);
                //sb.append(mag_val);
                String mag_result = String.format("%.1f", mag_val);
                mag.setText(mag_result+" uT");
                break;

            case Sensor.TYPE_LIGHT:
                //sb = new StringBuilder();
                light_val = values[0];
                //sb.append(light_val);
                String light_result = String.format("%.1f", light_val);
                light.setText(light_result+" Lux");
                break;

            case Sensor.TYPE_ACCELEROMETER:
                //sb = new StringBuilder();
                acc_val = Math.sqrt(values[0]*values[0]+values[1]*values[1]+values[2]*values[2])/10;
                //sb.append(acc_val);
                String acc_result = String.format("%.1f", acc_val);
                acc.setText(acc_result+" m/s2");
                break;
        }

        if(light_val>900){
            result.setText("OUTDOOR");
        }
        else{
            if(mag_val>80){
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
