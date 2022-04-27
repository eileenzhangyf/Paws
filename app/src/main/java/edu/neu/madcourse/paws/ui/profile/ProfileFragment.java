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








    }