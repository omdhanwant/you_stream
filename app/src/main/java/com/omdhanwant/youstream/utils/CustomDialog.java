package com.omdhanwant.youstream.utils;


import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.omdhanwant.youstream.R;

public class CustomDialog {

    static boolean isAlertShown = false;

    public static void getTheAlertDialog(Context context, String message){

        if (isAlertShown){
            return;
        }

        final AlertDialog.Builder dialog = new AlertDialog.Builder(context,R.style.DialogStyle);
        LayoutInflater factory = LayoutInflater.from(context);
        final View customLayoutView = factory.inflate(R.layout.custom_dialog, null);
        dialog.setView(customLayoutView);

        TextView messageTitleView = (TextView)customLayoutView.findViewById(R.id.dialogTitleView);
        messageTitleView.setTypeface(FontUtility.getSemiBoldFont(context));


        TextView messageTextView = (TextView) customLayoutView.findViewById(R.id.dialogMessageView);
        messageTextView.setTypeface(FontUtility.getRegularFont(context));
        messageTextView.setText(message);

        dialog.setPositiveButton("OK",new DialogInterface.OnClickListener() { // define the 'Cancel' button
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        isAlertShown = true;
        dialog.show();
    }


}
