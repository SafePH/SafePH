package com.example.marj.safeph.hotline;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Marj on 4/3/2018.
 */

public class HotlineUpdateRequest extends StringRequest{
    private static final String HOTLINE_UPDATE_REQUEST_URL = "https://ixuriz.000webhostapp.com/updatehotline.php";
    private Map<String, String> params;

    public HotlineUpdateRequest(String username, String name, String phone,
                                String newname, String newphone, Response.Listener<String> listener){
        super(Method.POST, HOTLINE_UPDATE_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username",username);
        params.put("name",name);
        params.put("phone",phone);
        params.put("newname",newname);
        params.put("newphone",newphone);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
