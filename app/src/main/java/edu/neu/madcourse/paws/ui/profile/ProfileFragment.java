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
import edu.neu.madcourse.paws.DownloadUrl;
import edu.neu.madcourse.paws.ImageUploadInfo;
import edu.neu.madcourse.paws.Pet;
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


        String url = "https://paws-846aa-default-rtdb.firebaseio.com/pet_info.json";
        getDataFromURL(url);


        return root;
    }

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