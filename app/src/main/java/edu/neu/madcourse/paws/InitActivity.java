package edu.neu.madcourse.paws;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import edu.neu.madcourse.framework.manager.MediaPlayerManager;
import edu.neu.madcourse.paws.GuideActivity;
import edu.neu.madcourse.paws.R;

public class InitActivity extends AppCompatActivity {

    Animation rotateAnimation;
    ImageView rotateLogo;
    FirebaseAuth firebaseAuth;
    Boolean isRemember;
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

    private Handler myHandler2 = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            switch (message.what){
                case SKIP_MAIN:

                    openNav();

                    break;
            }
            return false;
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        firebaseAuth = FirebaseAuth.getInstance();
        isRemember = false;

        /*
        mediaPlayerManager = new MediaPlayerManager();
        AssetFileDescriptor fileDescriptor = getResources().openRawResourceFd(R.raw.sample_6s);
        mediaPlayerManager.startPlay(fileDescriptor);
        */
        rotateLogo = (ImageView) findViewById(R.id.rotate_logo);
        rotateAnimation();
        if(firebaseAuth.getCurrentUser() != null) {
            checkRememberStatus(firebaseAuth.getCurrentUser().getEmail());
            Log.e("login_test2",String.valueOf(isRemember));
            if(isRemember)
                return;
            else{
                myHandler.sendEmptyMessageDelayed(SKIP_MAIN,5*1000);
            }


              //  myHandler.sendEmptyMessageDelayed(SKIP_MAIN,5*1000);

        }
        myHandler.sendEmptyMessageDelayed(SKIP_MAIN,5*1000);

    }

    private void rotateAnimation() {
        rotateAnimation = AnimationUtils.loadAnimation(this,R.anim.rotate);
        rotateLogo.startAnimation(rotateAnimation);
    }

    public void startMain(){

       // boolean isFirstApp = SpUtils.getInstance().getBoolean(Constant.SP_IS_FIRST_APP, true);
        Intent intent = new Intent(this, LoginActivity.class);
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

    public void openNav(){
        Intent intent = new Intent(this,NavActivity.class);
        startActivity(intent);
    }

    public void checkRememberStatus(String email){

        FirebaseDatabase.getInstance().getReference().child("remember_info")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        if(snapshot.getValue(String.class).equals(email)){
                            setRemember(true);
                            myHandler2.sendEmptyMessageDelayed(SKIP_MAIN,5*1000);

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
        //Log.e("login test", String.valueOf(isRemember));

    }

    public void setRemember(Boolean isRemember_v){
        Log.e("login_test3",String.valueOf(isRemember_v));
        this.isRemember = isRemember_v;
    }
}