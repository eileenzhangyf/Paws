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
import edu.neu.madcourse.paws.R;
import edu.neu.madcourse.paws.databinding.FragmentProfileBinding;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private FragmentProfileBinding binding;
    private TextView breedText, genderText, ageText, locationText, userNameText;
    private CircleImageView profileImage;

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
        userNameText = view.findViewById(R.id.user_name);
        locationText = view.findViewById(R.id.user_location);
        breedText = view.findViewById(R.id.breedTV);
        genderText = view.findViewById(R.id.petGenderTV);
        ageText = view.findViewById(R.id.petAgeTV);

//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        user = auth.getCurrentUser();



    }


}
