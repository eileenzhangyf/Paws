package edu.neu.madcourse.paws;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PetSettingActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_setting2);

        Button finish_button = (Button) findViewById(R.id.fin_button);
        finish_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMain();
            }
        });
    }

    public void openMain(){
        Intent intent = new Intent(this,NavActivity.class);
        startActivity(intent);
    }
}