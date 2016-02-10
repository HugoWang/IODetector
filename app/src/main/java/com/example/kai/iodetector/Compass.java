package com.example.kai.iodetector;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class Compass extends AppCompatActivity implements SensorEventListener {

    ImageView compassImage;
    float currentDegree = 0f;
    SensorManager mSensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);

        compassImage = (ImageView) findViewById(R.id.compassImage);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause()
    {
        mSensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    protected void onStop()
    {
        mSensorManager.unregisterListener(this);
        super.onStop();
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        int sensorType = event.sensor.getType();
        switch (sensorType)
        {
            case Sensor.TYPE_ORIENTATION:
                float degree = event.values[0];

                RotateAnimation ra = new RotateAnimation(currentDegree,
                        -degree, Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                ra.setDuration(200);

                compassImage.startAnimation(ra);
                currentDegree = -degree;
                break;
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
