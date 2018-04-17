package com.example.marj.safeph.signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.marj.safeph.R;
import com.example.marj.safeph.SigninActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

/**
 * Created by Marj on 2/23/2018.
 */

public class SignupActivity extends AppCompatActivity {
    EditText etUsername;
    EditText etPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        setupUI();
        setupButtons();
    }

    private void setupUI() {
        etUsername = findViewById(R.id.su_username);
        etPassword = findViewById(R.id.su_password);
    }

    private void setupButtons() {
        ImageView nextBtn = findViewById(R.id.signup_btn_next);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();

                if((Objects.equals(username, "") || Objects.equals(password, "")) || (Objects.equals(username, "") && Objects.equals(password, ""))){
                    Toast.makeText(SignupActivity.this,"Username/password must not be empty",Toast.LENGTH_SHORT).show();
                }
                else{
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if(success){
                                    Toast.makeText(SignupActivity.this,"Username exists",Toast.LENGTH_SHORT).show();
                                } else {
                                    Intent intent = new Intent(SignupActivity.this, SignupBasicActivity.class);
                                    intent.putExtra("username", username);
                                    intent.putExtra("password", password);
                                    startActivity(intent);
                                    Toast.makeText(SignupActivity.this,"Username does not exist",Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    SignupUserExistsRequest signupUserExists = new SignupUserExistsRequest(username,responseListener);
                    RequestQueue queue = Volley.newRequestQueue(SignupActivity.this);
                    queue.add(signupUserExists);

                    //Log.d("SignupActivity",username);
                }
            }
        });

        TextView signinLink = findViewById(R.id.signup_signin_link);
        signinLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
                startActivity(intent);
            }
        });

    }
}
