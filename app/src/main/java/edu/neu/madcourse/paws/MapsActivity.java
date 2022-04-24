package edu.neu.madcourse.paws;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import edu.neu.madcourse.paws.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int Request_code = 101;
    private double lat, lng;

    //import image button for show up data
    ImageButton dogpark, hospital, market;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //init imageview from activit_maps
        dogpark = findViewById(R.id.dogpark);
        hospital = findViewById(R.id.hospital);
        market = findViewById(R.id.market);


        fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(this.getApplicationContext());


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        dogpark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder stringBuilder = new StringBuilder
                        ("https://maps.googleapis.com/maps/api/place/textsearch/json");
                stringBuilder.append("?location=" + lat + "%2C" + lng);
                stringBuilder.append("&query=pet%20park");
                stringBuilder.append("&radius=2000");
                stringBuilder.append("&key=AIzaSyC1GSDJbnDJQ8rqVC7UpgV3_GOvRFbN5o0");


                String url = stringBuilder.toString();
                Object dataFetch[] = new Object[2];
                dataFetch[0] = mMap;
                dataFetch[1] = url;

                FetchData fetchData = new FetchData();
                fetchData.execute(dataFetch);
            }
        });

        market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder stringBuilder = new StringBuilder
                        ("https://maps.googleapis.com/maps/api/place/textsearch/json");
                stringBuilder.append("?location=" + lat + "%2C" + lng);
                stringBuilder.append("&query=pet%20supply%20store");
                stringBuilder.append("&radius=2000");
                stringBuilder.append("&key=AIzaSyC1GSDJbnDJQ8rqVC7UpgV3_GOvRFbN5o0");


                String url = stringBuilder.toString();
                Object dataFetch[] = new Object[2];
                dataFetch[0] = mMap;
                dataFetch[1] = url;

                FetchData fetchData = new FetchData();
                fetchData.execute(dataFetch);
            }
        });

        hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder stringBuilder = new StringBuilder
                        ("https://maps.googleapis.com/maps/api/place/textsearch/json");
                stringBuilder.append("?location=" + lat + "%2C" + lng);
                stringBuilder.append("&query=pet%20hospital");
                stringBuilder.append("&radius=2000");
                stringBuilder.append("&key=AIzaSyC1GSDJbnDJQ8rqVC7UpgV3_GOvRFbN5o0");

                String url = stringBuilder.toString();
                Object dataFetch[] = new Object[2];
                dataFetch[0] = mMap;
                dataFetch[1] = url;

                FetchData fetchData = new FetchData();
                fetchData.execute(dataFetch);
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        mMap = googleMap;
        getCurrentLocation();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
    }

    private void getCurrentLocation() {

        if(ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},Request_code);
            return;
        }

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(60000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setFastestInterval(5000);

        LocationCallback locationCallback = new LocationCallback(){

            @Override
            public void onLocationResult(LocationResult locationResult) {
                Toast.makeText(getApplicationContext(), "location result is = " + locationResult, Toast.LENGTH_LONG).show();

                if(locationResult == null) {
                    Toast.makeText(getApplicationContext(), "Current location is null", Toast.LENGTH_LONG).show();
                    return;
                }

                for(Location location: locationResult.getLocations()) {
                    if(location != null) {
                        Toast.makeText(getApplicationContext(), "Current location is" + location.getLongitude(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        };
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,null);
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if(location != null) {
                    lat = location.getLatitude();
                    lng = location.getLongitude();

                    LatLng latLng = new LatLng(lat,lng);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                }

            }
        });
    }
}
