package edu.neu.madcourse.paws;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class FetchLocationInfo extends AsyncTask<Object, String, String> {

    String googleNearByPlacesData;
    GoogleMap googleMap;
    String url;


    @Override
    protected void onPostExecute(String s) {
        List<String> latitudes = new ArrayList<>();
        List<String> longitudes = new ArrayList<>();
        List<String> users = new ArrayList<>();
        Set<String> lastUsers = new HashSet<>();

        List<String> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(s);
            Iterator iterator = jsonObject.keys();

            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                JSONObject value = (JSONObject) jsonObject.get(key);
                String lat = value.getString("latitude");
                String lng = value.getString("longitude");
                String user = value.getString("user_name");
                latitudes.add(lat);
                longitudes.add(lng);
                users.add(user);
            }
            for(int i = users.size() - 1; i >= 0; i--){
                String lat = latitudes.get(i);
                String lng = longitudes.get(i);
                String user = users.get(i);
                if(lastUsers.contains(user)) {
                    continue;
                }
                LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.title(user);
                markerOptions.position(latLng);
                googleMap.addMarker(markerOptions);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected String doInBackground(Object... objects) {
        try{
            googleMap = (GoogleMap) objects[0];
            url = (String) objects[1];
            DownloadUrl downloadUrl = new DownloadUrl();
            googleNearByPlacesData = downloadUrl.retirveveUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return googleNearByPlacesData;
    }
}
