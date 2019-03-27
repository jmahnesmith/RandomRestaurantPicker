package com.jmahnesmith.random_restaurant_picker;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;
import java.util.zip.Inflater;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;

import static okhttp3.Protocol.HTTP_2;


public class activityrest extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activityrest);


        AsyncTask yelp = new getYelpBuisnessesTask(this);
        yelp.execute();



        Button goToRestrauntButton = findViewById(R.id.goToRestrauntButton);
        goToRestrauntButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                openGoogleMaps(yelp);

            }
        });

    }

    protected void openGoogleMaps(AsyncTask yelp)
    {
        String Latitude = ((getYelpBuisnessesTask) yelp).getLatitude();
        String Longitude = ((getYelpBuisnessesTask) yelp).getLongitude();

        String uri = String.format(Locale.ENGLISH, "google.navigation:q=%s,%s", Latitude, Longitude);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));

        this.startActivity(intent);
    }


}
