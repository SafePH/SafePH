package com.example.marj.safeph.signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.marj.safeph.HomeActivity;
import com.example.marj.safeph.R;
import com.example.marj.safeph.SigninActivity;
import com.example.marj.safeph.session.SessionManager;
import com.example.marj.safeph.session.UserManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

/**
 * Created by Marj on 4/3/2018.
 */

public class SignupMedicalActivity extends AppCompatActivity{
    EditText etDiabetic;
    EditText etMedications;
    EditText etAllergies;
    EditText etOrgandonor;

    CheckBox diabeticYes;
    CheckBox diabeticNo;
    CheckBox organdonorYes;
    CheckBox organdonorNo;

    SessionManager session;
    UserManager userManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_medical);

        session = new SessionManager(getApplicationContext());
        userManager = new UserManager(getApplicationContext());

        setupUI();
        setupButtons();
    }

    private void setupUI() {
        etDiabetic = findViewById(R.id.m_diabetic_et);
        etMedications = findViewById(R.id.m_medication_et);
        etAllergies = findViewById(R.id.m_allergies_et);
        etOrgandonor = findViewById(R.id.m_organ_donor_et);

        etDiabetic.setFocusable(false);
        etDiabetic.setEnabled(false);
        etDiabetic.setCursorVisible(false);
        etDiabetic.setKeyListener(null);
        etOrgandonor.setFocusable(false);
        etOrgandonor.setEnabled(false);
        etOrgandonor.setCursorVisible(false);
        etOrgandonor.setKeyListener(null);

        diabeticYes = findViewById(R.id.diabetic_yes_cb);
        diabeticNo = findViewById(R.id.diabetic_no_cd);
        organdonorYes = findViewById(R.id.organdonor_yes_cb);
        organdonorNo = findViewById(R.id.organdonor_no_cb);

        diabeticYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (diabeticYes.isChecked())
                    diabeticNo.setChecked(false);
            }
        });
        diabeticNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (diabeticNo.isChecked())
                    diabeticYes.setChecked(false);
            }
        });
        organdonorYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (organdonorYes.isChecked())
                    organdonorNo.setChecked(false);
            }
        });
        organdonorNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (organdonorNo.isChecked())
                    organdonorYes.setChecked(false);
            }
        });

        Intent data = getIntent();
        if(Objects.equals(data.getStringExtra("diabetic"), "Yes"))
            diabeticYes.setChecked(true);
        else if(Objects.equals(data.getStringExtra("diabetic"), "No"))
            diabeticNo.setChecked(true);
        if(!Objects.equals(data.getStringExtra("medications"), ""))
            etMedications.setText(data.getStringExtra("medications"));
        if(!Objects.equals(data.getStringExtra("allergies"), ""))
            etAllergies.setText(data.getStringExtra("allergies"));
        if(Objects.equals(data.getStringExtra("organdonor"), "Yes"))
            organdonorYes.setChecked(true);
        else if(Objects.equals(data.getStringExtra("organdonor"), "No"))
            organdonorNo.setChecked(true);
    }

    private void setupButtons() {
        ImageView basicBtn = findViewById(R.id.m_basic_btn);
        basicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = getIntent();
                Intent intent = new Intent(SignupMedicalActivity.this,SignupBasicActivity.class);
                String diabetic = "";
                String organdonor = "";

                intent.putExtra("username", data.getStringExtra("username"));
                intent.putExtra("password", data.getStringExtra("password"));
                intent.putExtra("name", data.getStringExtra("name"));
                intent.putExtra("bloodtype", data.getStringExtra("bloodtype"));
                intent.putExtra("birthday", data.getStringExtra("birthday"));
                intent.putExtra("address", data.getStringExtra("address"));
                if(diabeticYes.isChecked())
                    diabetic = "Yes";
                else if (diabeticNo.isChecked())
                    diabetic = "No";
                intent.putExtra("diabetic", diabetic);
                intent.putExtra("medications", etMedications.getText().toString());
                intent.putExtra("allergies", etAllergies.getText().toString());
                if(organdonorYes.isChecked())
                    organdonor = "Yes";
                else if (organdonorNo.isChecked())
                    organdonor = "No";
                intent.putExtra("organdonor", organdonor);

                Log.d("Medical","username = "+data.getStringExtra("username"));
                Log.d("Medical","password = "+data.getStringExtra("password"));
                Log.d("Medical","name = "+data.getStringExtra("name"));
                Log.d("Medical","bloodtype = "+data.getStringExtra("bloodtype"));
                Log.d("Medical","birthday = "+data.getStringExtra("birthday"));
                Log.d("Medical","address = "+data.getStringExtra("address"));
                Log.d("Medical","diabetic = "+etDiabetic.getText().toString());
                Log.d("Medical","medications = "+etMedications.getText().toString());
                Log.d("Medical","allergies = "+etAllergies.getText().toString());
                Log.d("Medical","organdonor = "+etOrgandonor.getText().toString());

                startActivity(intent);
            }
        });

        ImageView signupBtn = findViewById(R.id.sum_signup_btn);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = getIntent();
                final String username = data.getStringExtra("username");
                final String password = data.getStringExtra("password");
                final String name = data.getStringExtra("name");
                final String bloodtype = data.getStringExtra("bloodtype");
                final String birthday = data.getStringExtra("birthday");
                final String address = data.getStringExtra("address");
                String diabetic;
                final String medications = etMedications.getText().toString();
                final String allergies = etAllergies.getText().toString();
                String organdonor;

                if(diabeticYes.isChecked())
                    diabetic = "Yes";
                else if (diabeticNo.isChecked())
                    diabetic = "No";
                else diabetic = null;
                if(organdonorYes.isChecked())
                    organdonor = "Yes";
                else if (organdonorNo.isChecked())
                    organdonor = "No";
                else organdonor = null;

                if(!name.isEmpty() && !bloodtype.isEmpty() && !birthday.isEmpty() && !address.isEmpty() &&
                        diabetic != null && !medications.isEmpty() && !allergies.isEmpty() && organdonor != null){
                    final String finalDiabetic = diabetic;
                    final String finalOrgandonor = organdonor;
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if(success){
                                    session.createLoginSession(username);
                                    userManager.setUser(username,password,name,address,bloodtype,birthday,finalDiabetic,finalOrgandonor,medications,allergies);
                                    SigninActivity signinActivity = new SigninActivity();
                                    signinActivity.getHotlines(getApplicationContext());
                                    signinActivity.getContacts(getApplicationContext());

                                    Intent intent = new Intent(SignupMedicalActivity.this,HomeActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(SignupMedicalActivity.this,"Add user successful",Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SignupMedicalActivity.this,"Add user not successful",Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    UserAddRequest userAddRequest = new UserAddRequest(username,password,name,bloodtype,birthday,address,diabetic,
                            medications,allergies,organdonor,responseListener);
                    RequestQueue queue = Volley.newRequestQueue(SignupMedicalActivity.this);
                    queue.add(userAddRequest);
                }
                else
                    Toast.makeText(SignupMedicalActivity.this,"No field must be empty",Toast.LENGTH_SHORT).show();

                Log.d("MedicalForDB","username = "+username);
                Log.d("MedicalForDB","password = "+password);
                Log.d("MedicalForDB","name = "+name);
                Log.d("MedicalForDB","bloodtype = "+bloodtype);
                Log.d("MedicalForDB","birthday = "+birthday);
                Log.d("MedicalForDB","address = "+address);
                Log.d("MedicalForDB","diabetic = "+diabetic);
                Log.d("MedicalForDB","medications = "+medications);
                Log.d("MedicalForDB","allergies = "+allergies);
                Log.d("MedicalForDB","organdonor = "+organdonor);
            }
        });
    }
}
