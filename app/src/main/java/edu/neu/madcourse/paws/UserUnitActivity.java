package edu.neu.madcourse.paws;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.provider.ContactsContract;

public class UserUnitActivity extends AppCompatActivity {
    String NickName;
    String profile_url;
    String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_unit);
    }

    public UserUnitActivity(){

    }

    public UserUnitActivity(String NickName, String profile_url, String city){
        this.NickName = NickName;

        this.profile_url = profile_url;
        this.city = city;
    }

    public String getNickName() {
        return NickName;
    }



    public String getProfile_url() {
        return profile_url;
    }

    public String getCity() {
        return city;
    }
}