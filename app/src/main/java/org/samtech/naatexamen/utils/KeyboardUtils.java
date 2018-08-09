package org.samtech.naatexamen.utils;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class KeyboardUtils {

    public static void hideKeyboard(Activity paramActivity) {
        InputMethodManager imm = (InputMethodManager) paramActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = paramActivity.getCurrentFocus();
        if (view == null) {
            view = new View(paramActivity);
        }
        assert imm != null;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
