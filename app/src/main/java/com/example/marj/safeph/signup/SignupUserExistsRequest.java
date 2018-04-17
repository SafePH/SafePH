package com.example.marj.safeph.signup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Marj on 4/3/2018.
 */

public class SignupUserExistsRequest extends StringRequest{
    private static final String USER_EXISTS_REQUEST_URL = "https://ixuriz.000webhostapp.com/checkuserexists.php";
    private Map<String, String> params;

    public SignupUserExistsRequest(String username, Response.Listener<String> listener){
        super(Request.Method.POST, USER_EXISTS_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username",username);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
