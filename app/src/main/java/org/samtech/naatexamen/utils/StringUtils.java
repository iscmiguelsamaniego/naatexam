package org.samtech.naatexamen.utils;

import android.widget.EditText;

public class StringUtils {

    public static String getEText(EditText paramEText){
        return paramEText.getText().toString().trim();
    }
}
