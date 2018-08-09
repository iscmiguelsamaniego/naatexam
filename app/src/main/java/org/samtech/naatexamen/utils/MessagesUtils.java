package org.samtech.naatexamen.utils;

import android.app.Activity;
import android.widget.Toast;

public class MessagesUtils {

    public static void showToast(Activity activity, String paramMsg){
        Toast.makeText(activity, paramMsg, Toast.LENGTH_SHORT).show();
    }
}
