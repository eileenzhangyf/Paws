package edu.neu.madcourse.paws;

import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class FetchFirebaseData extends AsyncTask<Object, String, String> {

    String googleNearByPlacesData;
    GoogleMap googleMap;
    String url;

    @Override
    protected void onPostExecute(String s) {
//        userLocaiton = FirebaseDatabase.getInstance().getReference("user_location");
//        userLocaiton.child().get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (!task.isSuccessful()) {
//                    Log.e("firebase", "Error getting data", task.getException());
//                }
//                else {
//                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
//                }
//            }
//        });



        try {
            JSONObject jsonObject = new JSONObject(s);
            String lat = jsonObject.getString("La");
            String lng = jsonObject.getString("Lon");
            LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            googleMap.addMarker(markerOptions);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));

//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                JSONObject getLocation = jsonObject1.getJSONObject("geometry").getJSONObject("location");
//
//                String lat = getLocation.getString("lat");
//                String lng = getLocation.getString("lng");
//
//                JSONObject getName = jsonArray.getJSONObject(i);
//                String name = getName.getString("name");
//
//                LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
//                MarkerOptions markerOptions = new MarkerOptions();
//                markerOptions.title(name);
//                markerOptions.position(latLng);
//                googleMap.addMarker(markerOptions);
//                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
//            }

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
