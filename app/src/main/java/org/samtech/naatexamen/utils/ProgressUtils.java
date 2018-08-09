package org.samtech.naatexamen.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.widget.TextView;

public class ProgressUtils {

    public static void setVisibilityToView
            (final View paramVProgress, final View paramVForm, final boolean isShowing,
             final TextView progressText, String progressTextLabel) {

        int shortAnimTime =
                paramVProgress
                        .getContext()
                        .getResources()
                        .getInteger(android.R.integer.config_shortAnimTime);

        paramVProgress.setVisibility(isShowing ? View.VISIBLE : View.GONE);
        paramVProgress.animate().setDuration(shortAnimTime).alpha(
                isShowing ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                paramVProgress.setVisibility(isShowing ? View.VISIBLE : View.GONE);
            }
        });

        progressText.setVisibility(isShowing ? View.VISIBLE : View.GONE);
        if (progressText.getVisibility() == View.VISIBLE) {
            progressText.setText(progressTextLabel);
        }

        paramVForm.setVisibility(isShowing ? View.GONE : View.VISIBLE);
        paramVForm.animate().setDuration(shortAnimTime).alpha(
                isShowing ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                paramVForm.setVisibility(isShowing ? View.GONE : View.VISIBLE);
            }
        });

    }
}
