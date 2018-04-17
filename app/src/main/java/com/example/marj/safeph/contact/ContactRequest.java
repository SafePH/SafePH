package com.example.marj.safeph.contact;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Marj on 4/1/2018.
 */

public class ContactRequest extends StringRequest {
    private static final String CONTACT_REQUEST_URL = "https://ixuriz.000webhostapp.com/getcontacts.php";
    private Map<String, String> params;

    public ContactRequest(String username, Response.Listener<String> listener){
        super(Method.POST, CONTACT_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username",username);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
