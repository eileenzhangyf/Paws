package edu.neu.madcourse.paws.ui.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.neu.madcourse.paws.DownloadUrl;
import edu.neu.madcourse.paws.ImageUploadInfo;
import edu.neu.madcourse.paws.Pet;
import edu.neu.madcourse.paws.R;
import edu.neu.madcourse.paws.User;
import edu.neu.madcourse.paws.databinding.FragmentProfileBinding;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.net.InternetDomainName;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private FragmentProfileBinding binding;
    TextView breedText, genderText, ageText, locationText, userNameText, petType;
    private CircleImageView profileImage;
    DatabaseReference user_db;
    DatabaseReference pet_db;
    FirebaseUser firebaseUser;
    String nickname, breed, gender, age, city, profileUrl;

    CircleImageView profile;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);


        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        breedText = view.findViewById(R.id.myPetBreedTV);
        genderText = view.findViewById(R.id.my_pet_gender_tv);
        ageText = view.findViewById(R.id.myPetAge);
        locationText = view.findViewById(R.id.user_city_tv);
        userNameText = view.findViewById(R.id.nick_name_tv);
        petType = view.findViewById(R.id.myPetTypeTV);
        profile = view.findViewById(R.id.profile_image);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseUser.getIdToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    @Override
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if(task.isSuccessful()){
                            String curr_user_email = firebaseUser.getEmail();
                            set_user_info(curr_user_email);
                            set_pet_info(curr_user_email);
                            Log.e("curr_user_profile",curr_user_email);
                        }else{
                            Log.e("get user","failed");
                        }
                    }
                });







       // String url = "https://paws-846aa-default-rtdb.firebaseio.com/pet_info.json";
      //  getDataFromURL(url);


        return view;
    }

    public void set_user_info(String curr_user){
        FirebaseDatabase.getInstance().getReference().child("user_info")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        User user = snapshot.getValue(User.class);
                        Log.e("profile_test3",String.valueOf(user));
                        Log.e("profile_test4",curr_user);
                        if(user.getUser_name().equals(curr_user)){
                            Log.e("profile_test",curr_user);
                            String img_url = user.getProfile_url();
                            Picasso.get().load(img_url).into(profile);
                            userNameText.setText(user.getNick_name());
                            Log.e("profile_test2",user.getNick_name());
                            locationText.setText(user.getCity());
                        }

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        User user = snapshot.getValue(User.class);
                        Log.e("profile_test3",user.getUser_name());
                        Log.e("profile_test4",curr_user);
                        if(user.getUser_name().equals(curr_user)){
                            Log.e("profile_test",curr_user);
                            String img_url = user.getProfile_url();
                            Picasso.get().load(img_url).into(profile);
                            userNameText.setText(user.getNick_name());
                            Log.e("profile_test2",user.getNick_name());
                            locationText.setText(user.getCity());
                        }
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public void set_pet_info(String curr_user_email){
        FirebaseDatabase.getInstance().getReference().child("pet_info")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        Pet pet = snapshot.getValue(Pet.class);
                        if(pet.getUser_name().equals(curr_user_email)){
                            breedText.setText(pet.getBreed());
                            genderText.setText(pet.getGender());
                            ageText.setText(pet.getAge());
                            petType.setText(pet.getPet_type());

                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        Pet pet = snapshot.getValue(Pet.class);
                        if(pet.getUser_name().equals(curr_user_email)){
                            breedText.setText(pet.getBreed());
                            genderText.setText(pet.getGender());
                            ageText.setText(pet.getAge());
                            petType.setText(pet.getPet_type());

                        }
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }




    //Get data from pet_info database api
    private String getDataFromURL(String url) {
        DownloadUrl downloadUrl = new DownloadUrl();
        String allData = "";
        try {
            allData = downloadUrl.retirveveUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allData;
    }

    //
    private void cleanDataFromURL(String s) {
        HashMap<String,Pet> pets = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(s);
            Iterator iterator = jsonObject.keys();

            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                JSONObject value = (JSONObject) jsonObject.get(key);
                String age = value.getString("age");
                String breed = value.getString("breed");
                String gender = value.getString("gender");
                String pet_type = value.getString("pet_type");
                String user_name = value.getString("user_name");
                pets.put(user_name, new Pet(user_name,pet_type,age, breed,gender));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}