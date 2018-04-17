package com.example.marj.safeph.signup;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Marj on 4/4/2018.
 */

public class UserAddRequest extends StringRequest{
    private static final String ADD_USER_REQUEST_URL = "https://ixuriz.000webhostapp.com/adduser.php";
    private Map<String, String> params;

    public UserAddRequest(String username, String password, String name, String bloodtype, String birthday,
                          String address, String diabetic, String medications, String allergies, String organdonor,
                          Response.Listener<String> listener) {
        super(Method.POST, ADD_USER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username",username);
        params.put("password",password);
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
