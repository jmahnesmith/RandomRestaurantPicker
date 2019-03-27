package com.jmahnesmith.random_restaurant_picker;



import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class getYelpBuisnessesTask extends AsyncTask<Object,Void,ArrayList<Buisness>>
{

    private Activity activity;

    String latitude;
    String longitude;



    public getYelpBuisnessesTask(Activity a)
    {
        this.activity = a;
    }



    protected void onPreExecute()
    {
        //SHOW THE SPINNER WHILE LOADING FEEDS
        LinearLayout linlaHeaderProgress = activity.findViewById(R.id.linlaHeaderProgress);
        linlaHeaderProgress.setVisibility(View.VISIBLE);
        //GETS CURRENT LOCATION FROM USER
        GPSTracker tracker = new GPSTracker(activity);
        Log.d("Latitude", String.valueOf(tracker.isGPSEnabled));
        Log.d("Latitude", String.valueOf(tracker.isNetworkEnabled));
        latitude = Double.toString(tracker.getLatitude());
        longitude = Double.toString(tracker.getLongitude());
        Log.d("Latitude", longitude);
        Log.d("Latitude", latitude);
    }

    protected ArrayList<Buisness> doInBackground(Object... params)
    {


        OkHttpClient client = new OkHttpClient();

        //Creates list of buisnesses
        ArrayList<Buisness> buisnesses = new ArrayList<Buisness>();




        Request request = new Request.Builder()
                .url("https://api.yelp.com/v3/businesses/search?categories=Restaurants&latitude=" + latitude + "&longitude=" + longitude)
                .addHeader("Authorization", "Bearer 0-WoLcstf08d_rXLPO1zSIe-VnBlDwLt0pQzhdu3eFqSBQNQoDHqTrrMcqfB--gRsENuLIAunA1Dsahd_6drFDEogDkyMo_ifi5JM3_PDAkJL_0kLIsCWKoxM7iWW3Yx")
                .get()
                .build();

        try
        {
            Response response2 = client.newCall(request).execute();

            JSONObject jsonObject = new JSONObject(response2.body().string().trim());       // parser
            JSONArray myResponse = (JSONArray)jsonObject.get("businesses");

            Random rand = new Random();
            int randomNumber = rand.nextInt(myResponse.length());


            Buisness buisness = new Buisness();
            buisness.name = myResponse.getJSONObject(randomNumber).getString("name");
            buisness.image_url = myResponse.getJSONObject(randomNumber).getString("image_url");
            buisness.latitude = myResponse.getJSONObject(randomNumber).getJSONObject("coordinates").getString("latitude");
            buisness.longitude = myResponse.getJSONObject(randomNumber).getJSONObject("coordinates").getString("longitude");
            buisness.is_closed = myResponse.getJSONObject(randomNumber).getBoolean("is_closed");
            buisnesses.add(buisness);

            //Log.d("Name", myResponse.getJSONObject(i).getJSONObject("location").getString("address1"));



        } catch (IOException e) {

            e.printStackTrace();
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }




        return buisnesses;
    }
    protected void onPostExecute(ArrayList<Buisness> result)
    {
        //INITIALIZES DATA INTO VARIABLES
        String buisnessName = result.get(0).name;
        String buisnessImage = result.get(0).image_url;
        longitude = result.get(0).longitude;
        latitude = result.get(0).latitude;

        TextView restaurantName = activity.findViewById(R.id.restaurantName);

        //SETS BUISNESS NAME AND IMAGE.
        restaurantName.setText(buisnessName);
        new DownloadImageTask((ImageView) activity.findViewById(R.id.restaurantImage)).execute(buisnessImage);

        //TURN SPINNER OFF

        LinearLayout linlaHeaderProgress = activity.findViewById(R.id.linlaHeaderProgress);
        linlaHeaderProgress.setVisibility(View.GONE);

        //TURN UI ON
        ImageView imageView = activity.findViewById(R.id.restaurantImage);
        imageView.setVisibility(View.VISIBLE);
        Button button = activity.findViewById(R.id.goToRestrauntButton);
        button.setVisibility(View.VISIBLE);
    }
    public String getLongitude()
    {
        String _longitude = longitude;

        return _longitude;
    }
    public String getLatitude()
    {
        String _latitude = latitude;

        return _latitude;
    }


}

