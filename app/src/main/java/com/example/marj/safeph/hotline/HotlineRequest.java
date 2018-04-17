package com.example.marj.safeph.hotline;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Marj on 3/31/2018.
 */

public class HotlineRequest extends StringRequest{
    private static final String HOTLINE_REQUEST_URL = "https://ixuriz.000webhostapp.com/gethotlines.php";
    private Map<String, String> params;

    public HotlineRequest(String username, Response.Listener<String> listener){
        super(Method.POST, HOTLINE_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username",username);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
