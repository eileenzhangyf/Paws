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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SameCityActivity extends AppCompatActivity {
    ListView listView;
    public List<UserUnitActivity> user_list;
    public UserAdapter userAdapter;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    String curr_user_email;
    String curr_city;
    List<AddressInfo> list;
    Set<String> same_city_users;
    List<User> userList;
    Set<String> added_users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_same_city);
        list = new ArrayList<>();
        same_city_users = new HashSet<>();
        userList = new ArrayList<>();
        added_users = new HashSet<>();
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
        listView = findViewById(R.id.city_listview);

        user_list = new ArrayList<>();
        userAdapter = new UserAdapter(this,R.layout.activity_user_unit,user_list);
        listView.setAdapter(userAdapter);
        getCurrCity();




    }

    public void getCurrCity(){
        FirebaseDatabase.getInstance().getReference().child("city_info").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                AddressInfo addressInfo = snapshot.getValue(AddressInfo.class);
                // if (addressInfo != null) {
                //     list.add(addressInfo);
                //     }

                //  for (AddressInfo addressInfo2 : list) {
                if (addressInfo.getUser_email().equals(curr_user_email)) {
                    curr_city = addressInfo.getCity();

                    Log.e("city_test", curr_city);
                    // break;

                }

                if (curr_city != null) {
                    //for (AddressInfo addressInfo1 : list) {
                    if (addressInfo.getCity().equals(curr_city)) {
                        same_city_users.add(addressInfo.getUser_email());
                        Log.e("city_test2", String.valueOf(same_city_users));
                    }
                }


                if (same_city_users.size() > 0) {
                    findUsers(same_city_users);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                AddressInfo addressInfo = snapshot.getValue(AddressInfo.class);
                // if (addressInfo != null) {
                //     list.add(addressInfo);
                //     }

                //  for (AddressInfo addressInfo2 : list) {
                if (addressInfo.getUser_email().equals(curr_user_email)) {
                    curr_city = addressInfo.getCity();

                    Log.e("city_test", curr_city);
                    // break;

                }

                if (curr_city != null) {
                    //for (AddressInfo addressInfo1 : list) {
                    if (addressInfo.getCity().equals(curr_city)) {
                        same_city_users.add(addressInfo.getUser_email());
                        Log.e("city_test2", String.valueOf(same_city_users));
                    }
                }


                if (same_city_users.size() > 0) {
                    findUsers(same_city_users);
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

        FirebaseDatabase.getInstance().getReference().child("city_info").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    AddressInfo addressInfo = dataSnapshot.getValue(AddressInfo.class);
                   // if (addressInfo != null) {
                   //     list.add(addressInfo);
               //     }

                  //  for (AddressInfo addressInfo2 : list) {
                        if (addressInfo.getUser_email().equals(curr_user_email)) {
                            curr_city = addressInfo.getCity();

                            Log.e("city_test", curr_city);
                           // break;

                    }

                    if (curr_city != null) {
                        //for (AddressInfo addressInfo1 : list) {
                            if (addressInfo.getCity().equals(curr_city)) {
                                same_city_users.add(addressInfo.getUser_email());
                                Log.e("city_test2", String.valueOf(same_city_users));
                            }
                        }


                        if (same_city_users.size() > 0) {
                            findUsers(same_city_users);
                        }


                    }
                }





            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });*/


        return ;
    }

    public void addUser(String email, String nick_name, String user_image, String city){
        if(added_users.contains(email)){
            return;
        }

        UserUnitActivity userUnitActivity = new UserUnitActivity(nick_name,user_image,city);
        user_list.add(userUnitActivity);
        Log.e("city_test7",String.valueOf(user_list));
        userAdapter.notifyDataSetChanged();
        added_users.add(email);

    }

    public void findUsers(Set<String> same_city_users){
        FirebaseDatabase.getInstance().getReference().child("user_info")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        //for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                            User user = snapshot.getValue(User.class);
                            //Log.e("city_test5",String.valueOf(user.getUser_name()));
                            if (same_city_users.contains(user.getUser_name())) {
                                //  userList.add(user);
                                //  Log.e("city_test3", String.valueOf(userList));
                                //}

                                //  for (User user1 : userList) {

                                Log.e("city_test6", String.valueOf(user.getUser_name()));
                                addUser(user.getUser_name(),user.getNick_name(), user.getProfile_url(), user.getCity());
                                Log.e("user added", "success");


                            }


                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                       // for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                            User user = snapshot.getValue(User.class);
                            //Log.e("city_test5",String.valueOf(user.getUser_name()));
                            if (same_city_users.contains(user.getUser_name())) {
                                //  userList.add(user);
                                //  Log.e("city_test3", String.valueOf(userList));
                                //}

                                //  for (User user1 : userList) {

                                Log.e("city_test6", String.valueOf(user.getUser_name()));
                                addUser(user.getUser_name(),user.getNick_name(), user.getProfile_url(), user.getCity());
                                Log.e("user added", "success");


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
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                            User user = dataSnapshot1.getValue(User.class);
                            //Log.e("city_test5",String.valueOf(user.getUser_name()));
                            if (same_city_users.contains(user.getUser_name())) {
                              //  userList.add(user);
                              //  Log.e("city_test3", String.valueOf(userList));
                            //}

                          //  for (User user1 : userList) {

                                Log.e("city_test6", String.valueOf(user.getUser_name()));
                                addUser(user.getUser_name(),user.getNick_name(), user.getProfile_url(), user.getCity());
                                Log.e("user added", "success");


                            }

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });*/
    }








}