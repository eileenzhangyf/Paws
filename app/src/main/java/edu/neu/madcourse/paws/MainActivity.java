package edu.neu.madcourse.paws;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.os.Bundle;

import edu.neu.madcourse.framework.base.BaseUIActivity;
import edu.neu.madcourse.framework.manager.MediaPlayerManager;
import edu.neu.madcourse.framework.utils.LogUtil;

public class MainActivity extends BaseUIActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openInitPage();
       // MediaPlayerManager mediaPlayerManager = new MediaPlayerManager();
        AssetFileDescriptor fileDescriptor = getResources().openRawResourceFd(R.raw.guitar_1);
        //mediaPlayerManager.startPlay(fileDescriptor);
        /*

        mediaPlayerManager.setOnProgressListener(new MediaPlayerManager.OnMusicProgressListener() {
            @Override
            public void onProgress(int progress, int pos) {
                //LogUtil.e("progress"+progress+"All:"+mediaPlayerManager.getDuration());
                int percentage = (int)(((float)progress)/((float) mediaPlayerManager.getDuration())*100);
                LogUtil.e("Percentage" + percentage);
            }
        });

        //verifyStoragePermissions((Activity) getApplicationContext());
        /*
    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
        /*
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }*/
    }

    public void openInitPage(){
        Intent intent = new Intent(this, InitActivity.class);
        startActivity(intent);
    }

}