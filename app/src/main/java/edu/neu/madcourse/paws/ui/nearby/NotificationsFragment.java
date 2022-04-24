package edu.neu.madcourse.paws.ui.nearby;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import edu.neu.madcourse.paws.MapsActivity;
import edu.neu.madcourse.paws.R;
import edu.neu.madcourse.paws.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {

    private static final int MY_PERMISSION_REQUEST_LOCATION = 99;
    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;
    double longitude;
    double latitude;
    public LocationListener locationListener;
    public LocationManager locationManager;
    public String provider;
    public Location location;

   /* private ActivityResultLauncher<String> location_permission_res = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted ->{
        Log.e("Permission result:",isGranted.toString());
        if(isGranted){
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    longitude = location.getLongitude();
                    latitude = location.getLatitude();
                    Log.e("longitude is:",String.valueOf(longitude));
                    Log.e("latitude is:", String.valueOf(latitude));
                }
            };
            locationManager = (LocationManager)  getActivity().getSystemService(Context.LOCATION_SERVICE);
            provider = locationManager.getBestProvider(new Criteria(),false);

        }else{

        }
    });*/

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        ActivityResultLauncher<String> location_permission_res = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            Log.e("Permission result:", isGranted.toString());
            if (isGranted) {
                locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
               // provider = locationManager.getBestProvider(new Criteria(), false);
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return ;
                }
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(@NonNull Location location) {
                        longitude = location.getLongitude();
                        latitude = location.getLatitude();
                        Log.e("longitude is:",String.valueOf(longitude));
                        Log.e("latitude is:", String.valueOf(latitude));
                    }
                };
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,2000,10,locationListener);

            }else{

            }
        });
        location_permission_res.launch(Manifest.permission.ACCESS_FINE_LOCATION);

        //checkLocationPermission();
        FloatingActionButton add_button = view.findViewById(R.id.floatingActionButton2);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMaps();
            }
        });



/*
        if(checkLocationPermission()){
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    longitude = location.getLongitude();
                    latitude = location.getLatitude();
                    Log.e("longitude is:",String.valueOf(longitude));
                    Log.e("latitude is:", String.valueOf(latitude));
                }
            };
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,2000,10,locationListener);
        }*/



        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void openMaps(){
        Intent intent = new Intent(getActivity(), MapsActivity.class);
        startActivity(intent);
    }

    public boolean checkLocationPermission(ActivityResultLauncher<String> location_permission_res){
        if(ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION)){
                new AlertDialog.Builder(getActivity())
                        .setTitle("Allow app to access location?")
                        .setMessage("This app requires user permission to display location")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                               location_permission_res.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                            }
                        })
                        .create()
                        .show();

            }else{
               location_permission_res.launch(Manifest.permission.ACCESS_FINE_LOCATION);
               Log.e("location request","sent");
            }
            return false;
        }else{
            Log.e("user_granted","location");
            return true;
        }
    }
    /*

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case MY_PERMISSION_REQUEST_LOCATION:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Log.e("location_permission","granted");
                    Toast.makeText(getActivity().getApplicationContext(),"You are now sharing location",Toast.LENGTH_SHORT).show();
                    if(ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                        locationManager.requestLocationUpdates(provider,400,1, (LocationListener) this);
                    }
                }else{

                }
            }
        }
        return;

    }*/




}