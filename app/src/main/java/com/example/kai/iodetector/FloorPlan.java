package com.example.kai.iodetector;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.indooratlas.android.sdk.IALocationManager;
import com.indooratlas.android.sdk.resources.IAResourceManager;

public class FloorPlan extends AppCompatActivity {

    private IALocationManager mLocationManager;
    private IAResourceManager mResourceManager;
    private ImageView mFloorPlanImage;
    private IAResourceManager mFloorPlanManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor_plan);

        mFloorPlanImage = (ImageView) findViewById(R.id.university);
        // ...
        // Create instance of IAFloorPlanManager class
        mFloorPlanManager = IAResourceManager.create(this);

    }

}
