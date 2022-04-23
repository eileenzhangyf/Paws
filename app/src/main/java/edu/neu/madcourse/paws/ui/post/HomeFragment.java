package edu.neu.madcourse.paws.ui.post;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import edu.neu.madcourse.paws.ComposeActivity;
import edu.neu.madcourse.paws.ImageUploadInfo;
import edu.neu.madcourse.paws.PostAdapter;
import edu.neu.madcourse.paws.PostUnitActivity;
import edu.neu.madcourse.paws.R;
import edu.neu.madcourse.paws.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    ListView listView;
    private List<PostUnitActivity> post_list;
    private PostAdapter postAdapter;
    DatabaseReference post_db;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        View view = inflater.inflate(R.layout.fragment_home,container,false);


        post_db = FirebaseDatabase.getInstance().getReference("All_images_db");

        FloatingActionButton add_button = view.findViewById(R.id.floatingActionButton);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCompose();
            }
        });

        listView = (ListView) view.findViewById(R.id.listview);
        post_list = new ArrayList<>();
        postAdapter = new PostAdapter(getActivity(),0,post_list);
        listView.setAdapter(postAdapter);


        post_db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ImageUploadInfo imageUploadInfo = snapshot.getValue(ImageUploadInfo.class);
                String user = imageUploadInfo.getPersonName();
                Log.e("Person name",user);
                String post_content = imageUploadInfo.getImageName();
                String post_url = imageUploadInfo.getImageURL();
                try {
                    addPost(user,new URL(post_url),post_content,new URL(post_url));
                    Log.e("post added","success");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ImageUploadInfo imageUploadInfo = snapshot.getValue(ImageUploadInfo.class);
                String user = imageUploadInfo.getPersonName();
                Log.e("Person name",user);
                String post_content = imageUploadInfo.getImageName();
                String post_url = imageUploadInfo.getImageURL();
                try {
                    addPost(user,new URL(post_url),post_content,new URL(post_url));
                    Log.e("post added","success");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
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


        /*
        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void openCompose() {
        Intent intent = new Intent(getActivity(), ComposeActivity.class);
        startActivity(intent);
    }

    public void addPost(String user_name, URL user_image, String post, URL post_img){
        PostUnitActivity postUnit = new PostUnitActivity(user_name,post_img,post,user_image);
        post_list.add(postUnit);
        postAdapter.notifyDataSetChanged();

    }
}