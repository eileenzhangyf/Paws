package edu.neu.madcourse.paws.ui.nearby;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import edu.neu.madcourse.paws.AddressInfo;
import edu.neu.madcourse.paws.CountryActivity;
import edu.neu.madcourse.paws.LocationInfo;
import edu.neu.madcourse.paws.MapsActivity;
import edu.neu.madcourse.paws.R;
import edu.neu.madcourse.paws.SameCityActivity;
import edu.neu.madcourse.paws.StateActivity;
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
    DatabaseReference loc_db;
    FirebaseUser firebaseUser;
    String curr_user_email;
    DatabaseReference city_db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        /*
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseUser.getIdToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    @Override
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if(task.isSuccessful()){
                            curr_user_email = firebaseUser.getEmail();
                            Log.e("curr_user_map",curr_user_email);
                        }else{
                            Log.e("get user","failed");
                        }
                    }
                });*/

        loc_db = FirebaseDatabase.getInstance().getReference("user_location");
        city_db = FirebaseDatabase.getInstance().getReference("city_info");

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

                        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        firebaseUser.getIdToken(true)
                                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                                        if(task.isSuccessful()){
                                            curr_user_email = firebaseUser.getEmail();
                                            Log.e("curr_user_map",curr_user_email);
                                            longitude = location.getLongitude();
                                            latitude = location.getLatitude();
                                            Log.e("longitude is:",String.valueOf(longitude));
                                            Log.e("latitude is:", String.valueOf(latitude));

                                            uploadToFirebase();
                                            Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                                            try {
                                                List<Address> addresses = geocoder.getFromLocation(latitude,longitude,1);
                                                uploadToAddressDb(addresses, curr_user_email);
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }else{
                                            Log.e("get user","failed");
                                        }
                                    }
                                });
                        /*

                        longitude = location.getLongitude();
                        latitude = location.getLatitude();
                        Log.e("longitude_2 is:",String.valueOf(longitude));
                        Log.e("latitude_2 is:", String.valueOf(latitude));
                        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                        try {
                            List<Address> addresses = geocoder.getFromLocation(longitude,latitude,1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/


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


    public void uploadToFirebase(){
        LocationInfo locationInfo = new LocationInfo(curr_user_email,String.valueOf(longitude),String.valueOf(latitude));
        String uploadId = loc_db.push().getKey();
        loc_db.child(uploadId).setValue(locationInfo);
    }

    public void openSameCity(){
        Intent intent = new Intent(getActivity(), SameCityActivity.class);
        startActivity(intent);
    }

    public void openSameState(){
        Intent intent = new Intent(getActivity(), StateActivity.class);
        startActivity(intent);
    }

    public void openCountry(){
        Intent intent = new Intent(getActivity(), CountryActivity.class);
        startActivity(intent);
    }

    public void uploadToAddressDb(List<Address> list, String curr_user_email){
        String city = list.get(0).getLocality();
        String state = list.get(0).getAdminArea();
        String country = list.get(0).getCountryName();
        AddressInfo addressInfo = new AddressInfo(curr_user_email,city,state,country);
        String uploadId = city_db.push().getKey();
        city_db.child(uploadId).setValue(addressInfo);

    }





}