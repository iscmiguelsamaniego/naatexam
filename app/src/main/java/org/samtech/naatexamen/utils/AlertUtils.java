package org.samtech.naatexamen.utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.samtech.naatexamen.R;
import org.samtech.naatexamen.callbacks.CallbackUtilAlert;

import static org.samtech.naatexamen.utils.Keys.NEGATIVE;
import static org.samtech.naatexamen.utils.Keys.POSITIVE;

public class AlertUtils {

    public static void showConfirmAlert(final Activity activity, int msgBody, String msgBodyBelow,
                                        int msgPositive, int msgNegative, int msgImage,
                                        boolean isCancelable,
                                        final CallbackUtilAlert callbackUtilAlert) {

        final Dialog customConfirmDialog = new Dialog(activity);
        final TextView bodyTextView, bodyTextBelowTextView, positiveTextView, negativeTextView;
        final ImageView informativeImageview;

        customConfirmDialog.setContentView(R.layout.dialog_confirm_custom);
        bodyTextView = customConfirmDialog.findViewById(R.id.dialog_alert_custom_body);
        bodyTextBelowTextView = customConfirmDialog.findViewById(R.id.dialog_alert_custom_body_below);
        positiveTextView = customConfirmDialog.findViewById(R.id.dialog_alert_custom_positive);
        negativeTextView = customConfirmDialog.findViewById(R.id.dialog_alert_custom_negative);
        informativeImageview = customConfirmDialog.findViewById(R.id.dialog_alert_custom_image);

        customConfirmDialog.setCancelable(isCancelable);
        bodyTextView.setText(activity.getString(msgBody));
        bodyTextBelowTextView.setText(msgBodyBelow);
        positiveTextView.setText(activity.getString(msgPositive));
        negativeTextView.setText(activity.getString(msgNegative));
        informativeImageview.setImageResource(msgImage);

        negativeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callbackUtilAlert.onClickAlert(true, NEGATIVE);
                customConfirmDialog.dismiss();
            }
        });

        positiveTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callbackUtilAlert.onClickAlert(true, POSITIVE);
                customConfirmDialog.dismiss();
            }
        });

        customConfirmDialog.show();
    }

    public static void showReadyAlert(final Activity activity, int msgBody, int msgPositive,
                                      int msgImage, boolean isCancelable,
                                        final CallbackUtilAlert callbackUtilAlert) {

        final Dialog customConfirmDialog = new Dialog(activity);
        final TextView bodyTextView, positiveTextView;
        final ImageView informativeImageview;

        customConfirmDialog.setContentView(R.layout.dialog_ready_custom);
        bodyTextView = customConfirmDialog.findViewById(R.id.dialog_alert_ready_body);
        positiveTextView = customConfirmDialog.findViewById(R.id.dialog_alert_ready_positive);
        informativeImageview = customConfirmDialog.findViewById(R.id.dialog_alert_ready_image);

        customConfirmDialog.setCancelable(isCancelable);
        bodyTextView.setText(activity.getString(msgBody));
        positiveTextView.setText(activity.getString(msgPositive));
        informativeImageview.setImageResource(msgImage);

        positiveTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callbackUtilAlert.onClickAlert(true, POSITIVE);
                customConfirmDialog.dismiss();
            }
        });

        customConfirmDialog.show();
    }

}
