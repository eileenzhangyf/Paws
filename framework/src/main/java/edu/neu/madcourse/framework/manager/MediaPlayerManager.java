package edu.neu.madcourse.framework.manager;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.LogRecord;

import edu.neu.madcourse.framework.utils.LogUtil;

public class MediaPlayerManager {
    public static final int MEDIA_STATUS_PLAY = 0;

    public static final int MEDIA_STATUS_PAUSE = 1;

    public static final int MEDIA_STATUS_STOP = 2;

    public static int MEDIA_STATUS = MEDIA_STATUS_STOP;

    private static final int H_PROGRESS = 1000;

    private MediaPlayer mediaPlayer;

    private OnMusicProgressListener musicProgressListener;

    public MediaPlayerManager(){
        mediaPlayer = new MediaPlayer();
    }

    /**
     * calculate the progress in a song
     */

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            switch (message.what){
                case H_PROGRESS:
                    if(musicProgressListener != null){
                        int currPosition = getCurrentPosition();
                        int percentage = (int)(((float)currPosition)/((float) getDuration())*100);
                        musicProgressListener.onProgress(currPosition,percentage);
                        handler.sendEmptyMessageDelayed(H_PROGRESS,1000);
                    }
                    break;
            }
            return false;
        }
    });

    /**
     * is playing
     * @return
     */

    public boolean isPlaying(){
        return mediaPlayer.isPlaying();
    }

    /**
     *start
     * @param path
     */

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void startPlay(AssetFileDescriptor path){

        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.start();
            MEDIA_STATUS = MEDIA_STATUS_PLAY;
            handler.sendEmptyMessage(H_PROGRESS);
        } catch (IOException e) {
            LogUtil.e(e.toString());
            e.printStackTrace();
        }
    }

    /**
     * pause
     */

    public void pausePlay(){
        if(isPlaying()) {
            mediaPlayer.pause();
            MEDIA_STATUS = MEDIA_STATUS_PAUSE;
            handler.removeMessages(H_PROGRESS);
        }
    }

    /**
     * continue
     */

    public void continuePlay(){
        mediaPlayer.start();
        MEDIA_STATUS = MEDIA_STATUS_PLAY;
        handler.sendEmptyMessage(H_PROGRESS);
    }

    /**
     * Stop
     */

    public void stopPlay(){
        mediaPlayer.stop();
        MEDIA_STATUS = MEDIA_STATUS_STOP;
        handler.removeMessages(H_PROGRESS);
    }

    /**
     * get current position
     * @return
     */

    public int getCurrentPosition(){
        return mediaPlayer.getCurrentPosition();
    }

    /**
     * get total length
     * @return
     */

    public int getDuration(){
        return mediaPlayer.getDuration();
    }

    /**
     * is loop
     * @param isLooping
     */

    public void setLooping(boolean isLooping){
        mediaPlayer.setLooping(isLooping);
    }

    /**
     * jump to
     * @param ms
     */

    public void seekTo(int ms){
        mediaPlayer.seekTo(ms);
    }

    /**
     * end of playing
     * @param listener
     */

    public void setOnCompletionListener(MediaPlayer.OnCompletionListener listener){
        mediaPlayer.setOnCompletionListener(listener);
    }

    /**
     * error in playing
     * @param listener
     */

    public void setOnErrorListener(MediaPlayer.OnErrorListener listener){
        mediaPlayer.setOnErrorListener(listener);
    }

    /**
     * progress in playing
     * @param listener
     */

    public void setOnProgressListener (OnMusicProgressListener listener){
        musicProgressListener = listener;
    }

    public interface OnMusicProgressListener{
        void onProgress(int progress, int position);
    }

}
