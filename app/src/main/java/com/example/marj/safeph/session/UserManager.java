package com.example.marj.safeph.session;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.marj.safeph.contact.ContactModel;
import com.example.marj.safeph.hotline.HotlineModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Marj on 3/31/2018.
 */

public class UserManager {
    private final static String TAG = "UserManager";

    SharedPreferences pref;
    public SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE;

    private static final String PREF_NAME = "UserPref";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_NAME = "name";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_BLOODTYPE = "bloodtype";
    public static final String KEY_BIRTHDAY = "birthday";
    public static final String KEY_DIABETIC = "diabetic";
    public static final String KEY_ORGANDONOR = "organdonor";
    public static final String KEY_MEDICATIONS = "medications";
    public static final String KEY_ALLERGIES = "allergies";
    public static final String KEY_HOTLINES = "hotlines";
    public static final String KEY_CONTACTS = "contacts";

    public UserManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setUser(String username,String password,String name,String address,String bloodtype,String birthday,
                        String diabetic,String organdonor,String medications,String allergies){
        editor.putString(KEY_USERNAME,username);
        editor.putString(KEY_PASSWORD,password);
        editor.putString(KEY_NAME,name);
        editor.putString(KEY_ADDRESS,address);
        editor.putString(KEY_BLOODTYPE,bloodtype);
        editor.putString(KEY_BIRTHDAY,birthday);
        editor.putString(KEY_DIABETIC,diabetic);
        editor.putString(KEY_ORGANDONOR,organdonor);
        editor.putString(KEY_MEDICATIONS,medications);
        editor.putString(KEY_ALLERGIES,allergies);

        editor.commit();
    }

    public void setDbHotlines(ArrayList<HotlineModel> dbHotlines){
        Gson gson = new Gson();
        List<HotlineModel> hotlineList = new ArrayList<>();
        hotlineList.addAll(dbHotlines);
        String json = gson.toJson(hotlineList);
        editor.putString(KEY_HOTLINES, json);
        Log.d(TAG,"hotlines: "+json);

        editor.commit();
    }

    public void setDbContacts(ArrayList<ContactModel> dbContacts){
        Gson gson = new Gson();
        List<ContactModel> contactList = new ArrayList<>();
        contactList.addAll(dbContacts);
        String json = gson.toJson(contactList);
        editor.putString(KEY_CONTACTS, json);
        Log.d(TAG,"contacts: "+json);

        editor.commit();
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_USERNAME, pref.getString(KEY_USERNAME, null));
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));
        user.put(KEY_NAME, pref.getString(KEY_NAME,null));
        user.put(KEY_ADDRESS, pref.getString(KEY_ADDRESS,null));
        user.put(KEY_BLOODTYPE, pref.getString(KEY_BLOODTYPE,null));
        user.put(KEY_BIRTHDAY, pref.getString(KEY_BIRTHDAY,null));
        user.put(KEY_DIABETIC, pref.getString(KEY_DIABETIC,null));
        user.put(KEY_ORGANDONOR, pref.getString(KEY_ORGANDONOR,null));
        user.put(KEY_MEDICATIONS, pref.getString(KEY_MEDICATIONS,null));
        user.put(KEY_ALLERGIES, pref.getString(KEY_ALLERGIES,null));
        user.put(KEY_HOTLINES, pref.getString(KEY_HOTLINES,null));
        user.put(KEY_CONTACTS, pref.getString(KEY_CONTACTS,null));

        // return user
        return user;
    }

    public void clearUserDetails(){
        editor.clear();
        editor.commit();
    }
}
