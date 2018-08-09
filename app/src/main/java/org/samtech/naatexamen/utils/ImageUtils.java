package org.samtech.naatexamen.utils;

import android.content.Context;
import android.os.Build;
import android.view.View;

import org.samtech.naatexamen.R;

public class ImageUtils {
    public static void setImage(Context context, View view, int drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setBackgroundDrawable(context.getResources().getDrawable(drawable, context.getTheme()));
        } else {
            view.setBackground(context.getResources().getDrawable(drawable));
        }
    }

    public static int getDrawable(String paramBCName) {
        int drawableSrc;
        switch (paramBCName) {
            case "Claro":
                drawableSrc = R.drawable.ic_claro;
                break;

            case "Tuenti":
                drawableSrc = R.drawable.ic_tuenti;
                break;

            case "Entel":
                drawableSrc = R.drawable.ic_entel;
                break;

            default:
                drawableSrc = R.drawable.ic_logo;
        }
        return drawableSrc;
    }

}
