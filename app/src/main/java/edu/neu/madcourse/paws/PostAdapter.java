package edu.neu.madcourse.paws;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

        String post_url = postUnit.getPost_image_uri();
        Log.e("url is",post_url);



        /*
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Bitmap bmp = BitmapFactory.decodeStream(post_url.openConnection().getInputStream());
                    default_post_image.setImageBitmap(bmp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();*/


        TextView userName_tv = (TextView) listView.findViewById(R.id.username_tv);
        String userName = postUnit.getUser_name();
        userName_tv.setText(userName);

        TextView post_tv = (TextView) listView.findViewById(R.id.post_tv);
        String post_content = postUnit.getPost_content();
        post_tv.setText(post_content);



        return listView;
    }
}
