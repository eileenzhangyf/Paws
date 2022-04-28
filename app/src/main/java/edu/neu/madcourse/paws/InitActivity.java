package edu.neu.madcourse.paws;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import edu.neu.madcourse.framework.manager.MediaPlayerManager;
import edu.neu.madcourse.paws.GuideActivity;
import edu.neu.madcourse.paws.R;

public class InitActivity extends AppCompatActivity {

    Animation rotateAnimation;
    ImageView rotateLogo;
    /**
     * 1. set full screen
     * 2. delay entering main page
     */
   // MediaPlayerManager mediaPlayerManager;
    private static final int SKIP_MAIN = 100;
    private Handler myHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            switch (message.what){
                case SKIP_MAIN:
                    startMain();
                    break;
            }
            return false;
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        /*
        mediaPlayerManager = new MediaPlayerManager();
        AssetFileDescriptor fileDescriptor = getResources().openRawResourceFd(R.raw.sample_6s);
        mediaPlayerManager.startPlay(fileDescriptor);
        */
        rotateLogo = (ImageView) findViewById(R.id.rotate_logo);
        rotateAnimation();
       myHandler.sendEmptyMessageDelayed(SKIP_MAIN,5*1000);
    }

    private void rotateAnimation() {
        rotateAnimation = AnimationUtils.loadAnimation(this,R.anim.rotate);
        rotateLogo.startAnimation(rotateAnimation);
    }

    public void startMain(){

       // boolean isFirstApp = SpUtils.getInstance().getBoolean(Constant.SP_IS_FIRST_APP, true);
        Intent intent = new Intent(this, GuideActivity.class);
        /*
        if (isFirstApp) {

            intent.setClass(this, NavActivity.class);

            SpUtils.getInstance().putBoolean(Constant.SP_IS_FIRST_APP, false);
        } else {

            String token = SpUtils.getInstance().getString(Constant.SP_TOKEN, "");
            if (TextUtils.isEmpty(token)) {

                    //跳转到登录页
                intent.setClass(this, LoginActivity.class);

            } else {
                //跳转到主页

            }
        }*/
        startActivity(intent);
        //finish();
    }
}