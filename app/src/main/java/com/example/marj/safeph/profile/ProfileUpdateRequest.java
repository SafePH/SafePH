package com.example.marj.safeph.profile;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Marj on 4/5/2018.
 */

public class ProfileUpdateRequest extends StringRequest{
    private static final String USER_UPDATE_REQUEST_URL = "https://ixuriz.000webhostapp.com/updateuser.php";
    private Map<String, String> params;

    public ProfileUpdateRequest(String username, String name, String bloodtype, String birthday, String address,
                                String diabetic, String medications, String allergies, String organdonor,
                                Response.Listener<String> listener){
        super(Method.POST, USER_UPDATE_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username",username);
        params.put("name",name);
        params.put("bloodtype",bloodtype);
        params.put("birthday",birthday);
        params.put("address",address);
        params.put("diabetic",diabetic);
        params.put("medications",medications);
        params.put("allergies",allergies);
        params.put("organdonor",organdonor);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
