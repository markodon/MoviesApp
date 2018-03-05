package com.movieapp.markodonovski.moviesapp.other;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


import com.google.gson.Gson;
import com.movieapp.markodonovski.moviesapp.token.Token;

/**
 * Created by markodonovski on 1/22/18.
 */


public class PreferencesManager {

    private static final String USERID = "USERID";

    private static final String SESIONID = "sesionID";

    private static SharedPreferences getPreferences (Context c) {

        return c.getApplicationContext().getSharedPreferences("MySharedPreffsFile", Activity.MODE_PRIVATE);

    }

    public static void  addUserID (String Email, Context c) {
        SharedPreferences.Editor prefsEditor;
        prefsEditor = getPreferences(c).edit();

        prefsEditor.putString("UserLogin", Email).apply();
        prefsEditor.putBoolean("Logged", true);
        prefsEditor.apply();
        prefsEditor.commit();
    }

    public static Token getToken (Context c) {

        return  new Gson().fromJson(getPreferences(c).getString("token", "" ),Token.class);
    }

    public static String getUserid(Token token, Context c) {

        return getPreferences(c).getString( "UserLogin","");
    }

    public static void addSessionID (String Email, Context c)  {
        SharedPreferences.Editor prefsEditor;
        prefsEditor = getPreferences(c).edit();

        prefsEditor.putString("SessionID", Email).apply();
        prefsEditor.apply();
        prefsEditor.commit();

    }

// Oznaka kako za folder pod koj se zapisuvaat nekoi fajlovi stringovi

    public static  String getSessionID (Context c) {


        return getPreferences(c).getString("SessionID", "");
    }
    public static  String getSessionID1 (Context c) {


        return getPreferences(c).getString("SessionID1", "");

    }



    public static void removeUser(Context c) {

        getPreferences(c).edit().remove(USERID).apply();



    }



}


