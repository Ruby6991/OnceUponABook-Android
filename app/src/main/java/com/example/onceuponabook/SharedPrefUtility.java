package com.example.onceuponabook;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.onceuponabook.EnumClasses.UserRole;

public class SharedPrefUtility {

    private static SharedPrefUtility instance = new SharedPrefUtility();
    private static Context mContext;

    private SharedPrefUtility(){}

    public static SharedPrefUtility getInstance(Context context){
        mContext = context;
        return instance;
    }

    public SharedPreferences.Editor getEditor(){
        return mContext.getSharedPreferences("login",Context.MODE_PRIVATE).edit();
    }

    public void resetSharedPreferences(){
        SharedPreferences.Editor editor = getEditor();
        editor.remove("email");
        editor.remove("pass");
        editor.remove("token");
        editor.remove("name");
        editor.remove("role");
        editor.apply();
    }

    private SharedPreferences getSharedPref(){
        return mContext.getSharedPreferences("login",Context.MODE_PRIVATE);
    }

    public void setUserName(String name){
        SharedPreferences.Editor editor = getEditor();
        editor.putString("name",name);
        editor.apply();
    }

    public void setUserEmail(String email){
        SharedPreferences.Editor editor = getEditor();
        editor.putString("email",email);
        editor.apply();
    }

    public void setUserToken(String token){
        SharedPreferences.Editor editor = getEditor();
        editor.putString("token",token);
        editor.apply();
    }

    public void setUserPass(String pass){
        SharedPreferences.Editor editor = getEditor();
        editor.putString("pass",pass);
        editor.apply();
    }

    public void setUserRole(UserRole role){
        SharedPreferences.Editor editor = getEditor();
        editor.putString("role",role.toString());
        editor.apply();
    }

    public String getUserName(){
        SharedPreferences pref = getSharedPref();
        return pref.getString("name","");
    }

    public String getUserEmail(){
        SharedPreferences pref = getSharedPref();
        return pref.getString("email","");
    }

    public String getUserToken(){
        SharedPreferences pref = getSharedPref();
        return pref.getString("token","");
    }

    public String getUserPass(){
        SharedPreferences pref = getSharedPref();
        return pref.getString("pass","");
    }

    public String getUserRole(){
        SharedPreferences pref = getSharedPref();
        return pref.getString("role","");
    }

}
