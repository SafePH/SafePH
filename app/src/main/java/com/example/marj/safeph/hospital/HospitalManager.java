package com.example.marj.safeph.hospital;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Marj on 4/2/2018.
 */

public class HospitalManager {
    private final static String TAG = "HospitalManager";

    SharedPreferences pref;
    public SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE;

    private static final String PREF_NAME = "HospitalPref";
    public static final String KEY_HOSPITALS = "hospitals";

    public HospitalManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setHospitals(ArrayList<HospitalModel> hospitals){
        Gson gson = new Gson();
        List<HospitalModel> hospitalList = new ArrayList<>();
        hospitalList.addAll(hospitals);
        String json = gson.toJson(hospitalList);
        editor.putString(KEY_HOSPITALS, json);
        //Log.d(TAG,"hospitals: "+json);

        editor.commit();
    }

    public HashMap<String, String> getHospitalsDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_HOSPITALS, pref.getString(KEY_HOSPITALS, null));

        // return user
        return user;
    }

    public void clearHospitalsDetails(){
        editor.clear();
        editor.commit();
    }
}
