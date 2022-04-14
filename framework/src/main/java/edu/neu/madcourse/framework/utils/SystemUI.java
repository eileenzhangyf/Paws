package edu.neu.madcourse.framework.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;

public class SystemUI {
    public static void fixSystemUi(Activity activity){
    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)

    {
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
    }
    }
}
