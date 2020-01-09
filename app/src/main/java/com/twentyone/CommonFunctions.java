package com.twentyone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.twentyone.LoginActivity.LOGIN_PREFERENCES;

public class CommonFunctions {
    public static SharedPreferences sharedpreferences;
    public static String email = "";
    public static int IS_GOAL_FINISHED = 1;
    public static  String ADMIN_EMAIL="admin";
    public static  String ADMIN_PASSWORD="admin";



    public static String getUserName(Context context) {
        sharedpreferences = context.getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        email = sharedpreferences.getString(LoginActivity.USERNAME_KEY, "");

        if (email.equalsIgnoreCase("")) { // if no user found or something went wrong it will go to login screen
            editor.clear().commit();
            Toast.makeText(context, context.getResources().getString(R.string.err_pleaseTryAgain), Toast.LENGTH_SHORT).show();
            context.startActivity(new Intent(context.getApplicationContext(), LoginActivity.class));
        }

        return email;
    }


    public static String getCurrentDate(Context context) {

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        return dateFormat.format(date);

    }

    public static String getCustomeDate(Context context, String goalCreationDate, String totalDaysStr) {

        int dayIncrement = Integer.parseInt(totalDaysStr);

        String dt = goalCreationDate;  // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, dayIncrement);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
        String output = sdf1.format(c.getTime());
        return output;
    }
}
