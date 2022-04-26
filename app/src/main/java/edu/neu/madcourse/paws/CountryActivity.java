package edu.neu.madcourse.paws;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CountryActivity extends AppCompatActivity {
    ListView listView;
    public List<UserUnitActivity> user_list;
    public UserAdapter userAdapter;
    FirebaseUser firebaseUser;
    String curr_user_email;
    String curr_city;
    List<AddressInfo> list;
    List<User> same_country_users;
    Set<String> added_users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        listView = findViewById(R.id.country_listview);
        user_list = new ArrayList<>();
        userAdapter = new UserAdapter(this,R.layout.activity_user_unit,user_list);
        listView.setAdapter(userAdapter);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseUser.getIdToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    @Override
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if(task.isSuccessful()){
                            curr_user_email = firebaseUser.getEmail();
                            Log.e("curr_user_test",curr_user_email);
                        }else{
                            Log.e("get user","failed");
                        }
                    }
                });

        FirebaseDatabase.getInstance().getReference().child("user_info")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        User user = snapshot.getValue(User.class);
                        addUser(user.getUser_name(),user.getNick_name(),user.getProfile_url(),user.getCity());
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        User user = snapshot.getValue(User.class);
                        addUser(user.getUser_name(),user.getNick_name(),user.getProfile_url(),user.getCity());
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

    public void addUser(String email, String nick_name, String user_image, String city){

        UserUnitActivity userUnitActivity = new UserUnitActivity(nick_name,user_image,city);
        user_list.add(userUnitActivity);
        Log.e("city_test7",String.valueOf(user_list));
        userAdapter.notifyDataSetChanged();


    }
}