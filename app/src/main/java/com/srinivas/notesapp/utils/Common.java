package com.srinivas.notesapp.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;


import com.srinivas.notesapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class Common {


    private static ProgressDialog progressDialog;
    private static Toast toast;
    private static Snackbar snackbar;

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        return connManager.getActiveNetworkInfo() != null
                && connManager.getActiveNetworkInfo().isConnected();
    }

    public static void showProgressDialog(String message) {
        progressDialog = new ProgressDialog(MyApplication.getAppContext(), R.style.AppCompatAlertDialogStyle);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(message);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public static void dismissProgressDialog() {
        try {
            if ((progressDialog != null) && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (final IllegalArgumentException e) {
            // Handle or log or ignore
        } catch (final Exception e) {
            // Handle or log or ignore
        } finally {
            progressDialog = null;
        }
    }

    public static void showAlert(String title, String msg, boolean cancelable) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MyApplication.getAppContext(),R.style.AppCompatAlertDialogStyle);
        builder.setTitle(title)
                .setMessage(msg)
                .setCancelable(cancelable)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();
    }

    public static void showCenteredToast( String msg) {
        if(toast==null){
            toast = Toast.makeText(MyApplication.getAppContext(), msg, Toast.LENGTH_LONG);
        }else {
            toast.setText(msg);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

    }

    public static void showBottomToast( String msg) {
        if(toast==null){
            toast = Toast.makeText(MyApplication.getAppContext(), msg, Toast.LENGTH_LONG);
        }else{
            toast.setText(msg);
        }
        toast.show();
    }


    public static void showSnackBarToast(@Nullable View view,String message){
        if(snackbar==null){
            snackbar = Snackbar
                    .make(view, message, Snackbar.LENGTH_LONG);
        }else{
            snackbar.setText(message);
        }
        snackbar.show();
    }

    public static void showSnackBarToast(@Nullable View view,String message, String action, View.OnClickListener clickListener){
        if(snackbar==null){
            snackbar = Snackbar
                    .make(view, message, Snackbar.LENGTH_LONG)
                    .setAction(action, clickListener);
        }else{
            snackbar.setText(message);
        }
        snackbar.show();
    }

    public static void hideKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void showKeyboard(Activity activity, EditText editField) {
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        editField.requestFocus();
        inputManager.showSoftInput(editField, InputMethodManager.SHOW_IMPLICIT);
    }

    public static final List<Long> times = Arrays.asList(
            TimeUnit.DAYS.toMillis(365),
            TimeUnit.DAYS.toMillis(30),
            TimeUnit.DAYS.toMillis(1),
            TimeUnit.HOURS.toMillis(1),
            TimeUnit.MINUTES.toMillis(1),
            TimeUnit.SECONDS.toMillis(1));
    public static final List<String> timesString = Arrays.asList("year","month","day","hr","min","sec");


    public static String getFormattedTime(long serverTime) {
        long age=System.currentTimeMillis()-serverTime;
        StringBuffer res = new StringBuffer();

        SimpleDateFormat formatter = new SimpleDateFormat("MMM d hh:mm a");
        formatter.setTimeZone(TimeZone.getDefault());

        SimpleDateFormat formatter1 = new SimpleDateFormat("hh:mm a");
        formatter1.setTimeZone(TimeZone.getDefault());

        for(int i=0;i< times.size(); i++) {
            Long current = times.get(i);
            long temp = age/current;
            if(temp>0 && age < TimeUnit.DAYS.toMillis(1)) {
                res.append(temp).append(" ").append( timesString.get(i) ).append(temp > 1 ? "s" : "").append(" ago");
                break;
            }else if(temp>0 && age > TimeUnit.DAYS.toMillis(1) && age < 2*TimeUnit.DAYS.toMillis(1)){
                res.append("Yesterday").append(" ").append(formatter1.format(new Date(serverTime)));
                break;
            }else if(temp>0 || age<0){
                res.append(formatter.format(new Date(serverTime)));
                break;
            }
        }
        if("".equals(res.toString()))
            return "Just now";
        else
            return res.toString();
    }

}
