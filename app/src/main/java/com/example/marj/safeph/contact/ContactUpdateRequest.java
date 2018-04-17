package com.example.marj.safeph.contact;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Marj on 4/2/2018.
 */

public class ContactUpdateRequest extends StringRequest{
    private static final String CONTACT_UPDATE_REQUEST_URL = "https://ixuriz.000webhostapp.com/updatecontact.php";
    private Map<String, String> params;

    public ContactUpdateRequest(String username, String name, String phone, String relation,
                                String newname, String newphone, String newrelation, Response.Listener<String> listener){
        super(Method.POST, CONTACT_UPDATE_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username",username);
        params.put("name",name);
        params.put("phone",phone);
        params.put("relation",relation);
        params.put("newname",newname);
        params.put("newphone",newphone);
        params.put("newrelation",newrelation);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
