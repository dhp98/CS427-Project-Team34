package edu.uiuc.cs427app.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * This is a utility class that would handle the
 * showing of toasts
 */
public class ToastMessageHandler {
    public static void popToastMessage(Context context, String message, int toastLength) {
        Toast toast = Toast.makeText(context, message, toastLength);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
}
