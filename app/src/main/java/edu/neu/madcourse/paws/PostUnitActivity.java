package edu.neu.madcourse.paws;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

import java.net.URL;

public class PostUnitActivity extends AppCompatActivity {
    String user_name;
    URL post_image_uri;
    String post_content;
    URL user_image_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_unit);
    }

    public PostUnitActivity(String user_name, URL post_uri, String post_content, URL user_image_uri){
        this.user_image_uri = user_image_uri;
        this.user_name = user_name;
        this.post_image_uri = post_uri;
        this.post_content = post_content;

    }

    public String getUser_name(){
        return user_name;
    }

    public URL getPost_image_uri(){
        return post_image_uri;
    }

    public String getPost_content(){
        return post_content;
    }

    public URL get_user_image(){
        return user_image_uri;
    }
}