package com.example.marj.safeph;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.marj.safeph.contact.ContactActivity;
import com.example.marj.safeph.contact.ContactModel;
import com.example.marj.safeph.contact.ContactRequest;
import com.example.marj.safeph.hotline.HotlineActivity;
import com.example.marj.safeph.hotline.HotlineModel;
import com.example.marj.safeph.hotline.HotlineRequest;
import com.example.marj.safeph.session.AlertDialogManager;
import com.example.marj.safeph.session.SessionManager;
import com.example.marj.safeph.session.UserManager;
import com.example.marj.safeph.signup.SignupActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Marj on 2/23/2018.
 */

public class SigninActivity extends AppCompatActivity{
    private final static String TAG = "SigninActivity";

    AlertDialogManager alert = new AlertDialogManager();
    SessionManager session;
    UserManager userManager;

    private ArrayList<HotlineModel> hotlines;
    private ArrayList<ContactModel> contacts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        hotlines = new ArrayList<>();
        contacts = new ArrayList<>();
        session = new SessionManager(getApplicationContext());
        userManager = new UserManager(getApplicationContext());
        //Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_SHORT).show();

        setupButtons();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SigninActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void setupButtons(){
        final EditText etUsername = findViewById(R.id.signin_et_username);
        final EditText etPassword = findViewById(R.id.signin_et_password);
        final ImageView signinBtn = findViewById(R.id.signin_btn_signin);

        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameInput = etUsername.getText().toString();
                String passwordInput = etPassword.getText().toString();

                //Response received from the server
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                String username = jsonResponse.getString("username");
                                String password = jsonResponse.getString("password");
                                String name = jsonResponse.getString("name");
                                String address = jsonResponse.getString("address");
                                String bloodtype = jsonResponse.getString("bloodtype");
                                String birthday = jsonResponse.getString("birthday");
                                String diabetic = jsonResponse.getString("diabetic");
                                String organdonor = jsonResponse.getString("organdonor");
                                String medications = jsonResponse.getString("medications");
                                String allergies = jsonResponse.getString("allergies");

                                Log.d("Signin","username = "+username);
                                Log.d("Signin","name = "+name);
                                Log.d("Signin","bloodtype = "+bloodtype);
                                Log.d("Signin","birthday = "+birthday);
                                Log.d("Signin","address = "+address);
                                Log.d("Signin","diabetic = "+diabetic);
                                Log.d("Signin","medications = "+medications);
                                Log.d("Signin","allergies = "+allergies);
                                Log.d("Signin","organdonor = "+organdonor);

                                //Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_SHORT).show();

                                session.createLoginSession(username);
                                userManager.setUser(username,password,name,address,bloodtype,birthday,diabetic,organdonor,medications,allergies);
                                getHotlines(getApplicationContext());
                                getContacts(getApplicationContext());

                                Intent intent = new Intent(SigninActivity.this,HomeActivity.class);
                                SigninActivity.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SigninActivity.this);
                                builder.setMessage("Incorrect username / password")
                                        .setNegativeButton("Try Again",null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                SigninRequest signinRequest = new SigninRequest(usernameInput,passwordInput,responseListener);
                RequestQueue queue = Volley.newRequestQueue(SigninActivity.this);
                queue.add(signinRequest);
            }
        });

        final TextView signupLink = findViewById(R.id.signin_signup_link);
        signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SigninActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    public void getHotlines(Context context){
        final UserManager userManager = new UserManager(context);
        final ArrayList<HotlineModel> hotlines = new ArrayList<>();
        //Default
        hotlines.add(new HotlineModel("EMERGENCY NUMBER (Default)","911"));
        hotlines.add(new HotlineModel("RED CROSS (Default)","143"));
        hotlines.add(new HotlineModel("NDRRMC (Default)","9111406"));
        hotlines.add(new HotlineModel("PNP/FIRE (Default)","117"));
        hotlines.add(new HotlineModel("MMDA (Default)","136"));
        hotlines.add(new HotlineModel("PAG-ASA (Default)","4338526"));
        hotlines.add(new HotlineModel("PHILIPPINE COAST GUARD (Default)","5278481"));
        hotlines.add(new HotlineModel("PHIVOLCS (Default)","4261468"));
        hotlines.add(new HotlineModel("DSWD (Default)","9318101"));

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    Log.d(TAG, String.valueOf("jsonResponse hotlines: "+jsonResponse));

                    if(success){
                        for(int i = 0; i < jsonResponse.length()-1; i++){
                            JSONObject row = jsonResponse.getJSONObject(String.valueOf(i));
                            String name = row.getString("hotline_name");
                            String phone = row.getString("hotline_phone");
                            hotlines.add(new HotlineModel(name,phone));
                        }
                    }

                    for(int i = 0; i < hotlines.size(); i++){
                        Log.d(TAG, "getHotlines(): " +
                                "Name: " + hotlines.get(i).getName() +
                                " Phone: " + hotlines.get(i).getPhone());
                    }
                    userManager.setDbHotlines(hotlines);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        SessionManager session = new SessionManager(context);
        HashMap<String,String> user = session.getUserDetails();
        String username = user.get(SessionManager.KEY_NAME);

        HotlineRequest hotlineRequest = new HotlineRequest(username,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(hotlineRequest);
    }

    public void getContacts(Context context){
        Log.d(TAG, "getContacts: ");
        final ArrayList<ContactModel> contacts = new ArrayList<>();
        final UserManager userManager = new UserManager(context);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    Log.d(TAG, String.valueOf("jsonResponse contacts: "+jsonResponse));

                    if(success){
                        for(int i = 0; i < jsonResponse.length()-1; i++){
                            JSONObject row = jsonResponse.getJSONObject(String.valueOf(i));
                            String name = row.getString("contact_name");
                            String phone = row.getString("contact_phone");
                            String relation = row.getString("contact_relation");
                            contacts.add(new ContactModel(name,phone,relation));
                        }
                    }

                    for(int i = 0; i < contacts.size(); i++){
                        Log.d(TAG, "getContacts(): " +
                                "Name: " + contacts.get(i).getName() +
                                " Phone: " + contacts.get(i).getPhone() +
                                " Relation: " + contacts.get(i).getRelation());
                    }
                    userManager.setDbContacts(contacts);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        SessionManager session = new SessionManager(context);
        HashMap<String,String> user = session.getUserDetails();
        String username = user.get(SessionManager.KEY_NAME);

        ContactRequest contactRequest = new ContactRequest(username,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(contactRequest);
    }

    public void getUserDetails(final Context context){
        final UserManager userManager = new UserManager(context);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if(success){
                        String username = jsonResponse.getString("username");
                        String password = jsonResponse.getString("password");
                        String name = jsonResponse.getString("name");
                        String address = jsonResponse.getString("address");
                        String bloodtype = jsonResponse.getString("bloodtype");
                        String birthday = jsonResponse.getString("birthday");
                        String diabetic = jsonResponse.getString("diabetic");
                        String organdonor = jsonResponse.getString("organdonor");
                        String medications = jsonResponse.getString("medications");
                        String allergies = jsonResponse.getString("allergies");

                        Log.d("Signin--","username = "+username);
                        Log.d("Signin--","name = "+name);
                        Log.d("Signin--","bloodtype = "+bloodtype);
                        Log.d("Signin--","birthday = "+birthday);
                        Log.d("Signin--","address = "+address);
                        Log.d("Signin--","diabetic = "+diabetic);
                        Log.d("Signin--","medications = "+medications);
                        Log.d("Signin--","allergies = "+allergies);
                        Log.d("Signin--","organdonor = "+organdonor);

                        userManager.setUser(username,password,name,address,bloodtype,birthday,diabetic,organdonor,medications,allergies);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        HashMap<String, String> user = userManager.getUserDetails();

        String usernameSession = user.get(UserManager.KEY_USERNAME);
        String passwordSession = user.get(UserManager.KEY_PASSWORD);

        SigninRequest signinRequest = new SigninRequest(usernameSession,passwordSession,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(signinRequest);
    }
}
