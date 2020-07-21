package br.com.navegacao.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;


public class UserSession {
    // Shared Preferences reference
    SharedPreferences pref;
    
    // Editor reference for Shared preferences
    SharedPreferences.Editor editor;
    
    // Context
    Context _context;

    // All Shared Preferences Keys
    public static final String IS_USER_LOGIN = "IsUserLoggedIn";
    public static final String KEY_NAME = "Nome";
    public static final String KEY_PASSWORD = "Senha";
    

    // Constructor
    public UserSession(Context context){
        this._context = context;
        pref = _context.getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        editor = pref.edit();
    }
    

    //Create login session
    public void createUserLoginSession(String usuario, String senhaUsuario){
        // Storing login value as TRUE
        editor.putBoolean(IS_USER_LOGIN, true);
        // Storing name in preferences
        editor.putString(KEY_NAME, usuario);
        // Storing email in preferences
        editor.putString(KEY_PASSWORD,  senhaUsuario);
        // commit changes
        editor.commit();
    }
    

    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else do anything
     * */
    public boolean checkLogin(){
        // Check login status
        if(!this.isUserLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, Login.class);    
            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    
            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);    
            // Staring Login Activity
            _context.startActivity(i);
    
            return true;
        }
        return false;
    }
    
    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        // user email id
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));
        // return user
        return user;
    }
    
    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();
    
        // After logout redirect
        Intent i = new Intent(_context, Login.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    
        // Staring Login Activity
        _context.startActivity(i);
    }
    
    // Check for login
    public boolean isUserLoggedIn(){
        return pref.getBoolean(IS_USER_LOGIN, false);
    }
}