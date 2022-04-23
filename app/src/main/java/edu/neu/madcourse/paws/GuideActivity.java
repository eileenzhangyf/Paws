package edu.neu.madcourse.paws;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import edu.neu.madcourse.framework.manager.MediaPlayerManager;

public class GuideActivity extends AppCompatActivity {
    MediaPlayerManager mediaPlayerManager;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
       // mediaPlayerManager = new MediaPlayerManager();
        AssetFileDescriptor fileDescriptor = getResources().openRawResourceFd(R.raw.park);
       // mediaPlayerManager.startPlay(fileDescriptor);

        TextView skip = (TextView) findViewById(R.id.tv_guide_skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLogin();
              //  mediaPlayerManager.pausePlay();
            }
        });
    }

    public void openLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}