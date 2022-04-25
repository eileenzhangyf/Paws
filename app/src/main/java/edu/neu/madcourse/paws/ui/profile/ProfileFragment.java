package edu.neu.madcourse.paws.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.neu.madcourse.paws.ImageUploadInfo;
import edu.neu.madcourse.paws.R;
import edu.neu.madcourse.paws.databinding.FragmentProfileBinding;

import com.google.common.net.InternetDomainName;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLOutput;


public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private FragmentProfileBinding binding;
    private TextView breedText, genderText, ageText, locationText, userNameText;
    private CircleImageView profileImage;
    DatabaseReference user_db;
    DatabaseReference pet_db;
    FirebaseUser firebaseUser;
    String nickname, breed, gender, age, city, profileUrl;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void init(View view) {
        userNameText = (TextView) view.findViewById(R.id.user_name);
        locationText = (TextView) view.findViewById(R.id.user_location);
        breedText = (TextView) view.findViewById(R.id.breedTV);
        genderText = (TextView) view.findViewById(R.id.petGenderTV);
        ageText = (TextView) view.findViewById(R.id.petAgeTV);
        profileImage = (CircleImageView) view.findViewById(R.id.profile_image);

        user_db = FirebaseDatabase.getInstance().getReference("user_info");
        pet_db = FirebaseDatabase.getInstance().getReference("pet_info");


        pet_db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                    breed = datasnapshot.child("breed").getValue(String.class);
                    age = datasnapshot.child("age").getValue(String.class);
                    gender = datasnapshot.child("gender").getValue(String.class);
                    breedText.setText(breed);
                    ageText.setText(age);
                    genderText.setText(gender);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        user_db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nickname = snapshot.child("nick_name").getValue(String.class);
                userNameText.setText(nickname);
                profileImage = snapshot.child("profile_url").getValue(CircleImageView.class);
                city = snapshot.child("city").getValue(String.class);
                locationText.setText(city);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        }






    }