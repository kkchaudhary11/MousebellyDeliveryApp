package com.mousebelly.app.deliveryapp;

import android.content.Context;
import android.content.SharedPreferences;

import com.mousebelly.app.deliveryapp.Login.LoginActivity;


/**
 * Created by Kamal Kant on 28-04-2017.
 */

public class LocalSaveModule {
    public static final String Credential = "login";
    static SharedPreferences sharedpreferences;


    public static void storePreferences(Context c, String pwd) {


        sharedpreferences = c.getSharedPreferences(Credential, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString("UserName", LoginActivity.USERID);
        editor.putString("Password", pwd);

        editor.commit();
    }

    public static void clearPreferences(Context c) {
        SharedPreferences preferences = c.getSharedPreferences(Credential, 0);
        preferences.edit().remove("Password").commit();
    }
}
