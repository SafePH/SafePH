package com.example.marj.safeph.hotline;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Marj on 4/3/2018.
 */

public class HotlineAddRequest extends StringRequest{
    private static final String HOTLINE_ADD_REQUEST_URL = "https://ixuriz.000webhostapp.com/addhotline.php";
    private Map<String, String> params;

    public HotlineAddRequest(String username,String name,String phone,Response.Listener<String> listener){
        super(Request.Method.POST, HOTLINE_ADD_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username",username);
        params.put("name",name);
        params.put("phone",phone);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
