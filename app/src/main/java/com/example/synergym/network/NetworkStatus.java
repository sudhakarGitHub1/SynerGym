package com.example.synergym.network;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.synergym.R;
import com.example.synergym.utils.UtilsKt;

/**
 * This BroadcastReceiver to update network connections is Available/Not.
 */
public class NetworkStatus extends BroadcastReceiver {
    private static Context mContext;
    public static Context appContext;
    private String message;
    public static Dialog errorDialog;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        try {
            System.out.println("_____________netChange onReceive");
            if (isOnline(mContext)) {
                System.out.println("_____________netChange*$");
                Intent i = new Intent(UtilsKt.ACTIVITY_ACTION);
                LocalBroadcastManager.getInstance(mContext).sendBroadcast(i);
            } else {
                System.out.println("_____________netChange*!!");
                DivertToNoInternetScreen();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public static boolean isOnline(Context mContext2) {
        if (mContext2 != null) {
            ConnectivityManager connectivity = (ConnectivityManager) mContext2.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null)
                    for (int i = 0; i < info.length; i++)
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            CloseNoInternetScreen();
                            return true;
                        }
            }
        }
        return false;
    }

    private void DivertToNoInternetScreen() {
        if (mContext != null)
            errorInSplash(mContext.getString(R.string.check_internet_connection));
    }

    private static void CloseNoInternetScreen() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (errorDialog != null && errorDialog.isShowing()) {

                    errorDialog.dismiss();
                }
            }
        });

    }

    private void errorInSplash(String message) {
        try {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    if (appContext != null && appContext instanceof AppCompatActivity && !((AppCompatActivity) appContext).isFinishing()) {
                        if (errorDialog != null && errorDialog.isShowing()) {
                            errorDialog.dismiss();
                        }
                        final View view = View.inflate(appContext, R.layout.no_internet_lay, null);
                        errorDialog = new Dialog(appContext, R.style.Theme_Transparent1);
                        errorDialog.setContentView(view);
                        errorDialog.setCancelable(false);
                        errorDialog.setCanceledOnTouchOutside(false);
                        Window window = errorDialog.getWindow();
                        window.setGravity(Gravity.TOP);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                            window.setStatusBarColor(Color.BLACK);
                        }
                        errorDialog.show();
                    } else {
                        try {
                            errorDialog.dismiss();
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                    }
                }
            });

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}