package com.jmahnesmith.random_restaurant_picker;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity
{
    private Button restButton;

    //PERMISSIONS
    private static final String[] LOCATION_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.INTERNET
    };
    private static final int LOCATION_REQUEST=1340;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermissions(LOCATION_PERMS, LOCATION_REQUEST);

        restButton = findViewById(R.id.goToRestrauntButton);
        restButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                openFindRest();
            }
        });




    }
    public void openFindRest()
    {
        Intent intent = new Intent(this, activityrest.class );
        startActivity(intent);
    }

}
