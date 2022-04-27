package edu.neu.madcourse.paws;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class PostAdapter extends ArrayAdapter<PostUnitActivity> {

    public PostAdapter(@NonNull Context context, int resource, List<PostUnitActivity> post_list) {
        super(context, resource,post_list);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listView = convertView;
        if(listView == null){
            listView = LayoutInflater.from(getContext()).inflate(R.layout.activity_post_unit,parent,false);
        }
        PostUnitActivity postUnit = getItem(position);
        ImageView default_post_image = (ImageView) listView.findViewById(R.id.default_post);
        ImageView default_profile_image = (ImageView) listView.findViewById(R.id.profile_default);

        String post_url = postUnit.getPost_image_uri();
        Log.e("url is",post_url);
        Picasso.get().load(post_url).into(default_post_image);

        String person_url = postUnit.get_user_image();
       // Picasso.get().load(person_url).into(default_profile_image);



        TextView userName_tv = (TextView) listView.findViewById(R.id.username_tv);
        String userName = postUnit.getUser_name();
       // userName_tv.setText(userName);

        TextView post_tv = (TextView) listView.findViewById(R.id.post_tv);
        String post_content = postUnit.getPost_content();
        post_tv.setText(post_content);

        TextView time_tv = listView.findViewById(R.id.time_tv);
        String post_data = postUnit.getDate();
        time_tv.setText(post_data);

        FirebaseDatabase.getInstance().getReference().child("user_info")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        User user = snapshot.getValue(User.class);
                        if(user.getUser_name().equals(userName)){
                            Log.e("post_test6",user.getUser_name());
                            String profile_url = user.getProfile_url();
                            String nickName = user.getNick_name();
                            userName_tv.setText(nickName);
                            Log.e("post_test5",profile_url);
                            Picasso.get().load(profile_url).into(default_profile_image);
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

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



        return listView;
    }
}
