package edu.neu.madcourse.paws;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

public class UserAdapter extends ArrayAdapter<UserUnitActivity> {
    public UserAdapter(@NonNull Context context, int resource, List<UserUnitActivity> user_list) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listview = convertView;
        if(listview == null){
            listview = LayoutInflater.from(getContext()).inflate(R.layout.activity_user_unit,parent,false);
        }
        UserUnitActivity userUnit = getItem(position);
        TextView userName_tv = (TextView) listview.findViewById(R.id.name_tv);
        String name = userUnit.getNickName();
        userName_tv.setText(name);



        TextView city_tv = (TextView) listview.findViewById(R.id.city_tv);
        String city = userUnit.getCity();
        city_tv.setText(city);

        ImageView profile = listview.findViewById(R.id.userUnit_profile);
        Picasso.get().load(userUnit.getProfile_url()).into(profile);


        return listview;
    }
}
