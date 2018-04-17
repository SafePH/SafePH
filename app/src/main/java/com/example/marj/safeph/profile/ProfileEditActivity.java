package com.example.marj.safeph.profile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.marj.safeph.R;
import com.example.marj.safeph.SigninActivity;
import com.example.marj.safeph.contact.ContactActivity;
import com.example.marj.safeph.hospital.HospitalActivity;
import com.example.marj.safeph.hotline.HotlineActivity;
import com.example.marj.safeph.session.SessionManager;
import com.example.marj.safeph.session.UserManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by Marj on 4/5/2018.
 */

public class ProfileEditActivity extends AppCompatActivity{
    EditText etName;
    EditText etBloodtype;
    EditText etBirthday;
    EditText etAddress;
    EditText etMedications;
    EditText etAllergies;

    CheckBox diabeticYes;
    CheckBox diabeticNo;
    CheckBox organdonorYes;
    CheckBox organdonorNo;

    Calendar myCalendar;
    UserManager userManager;
    SessionManager session;

    String diabetic;
    String organdonor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        myCalendar = Calendar.getInstance();
        userManager = new UserManager(getApplicationContext());
        session = new SessionManager(getApplicationContext());

        setupUI();
        setupButtons();
    }

    private void setupUI() {
        etName = findViewById(R.id.ep_name_et);
        etBloodtype = findViewById(R.id.ep_bloodtype_et);
        etBirthday = findViewById(R.id.ep_birthday_et);
        etAddress = findViewById(R.id.ep_address_et);
        etMedications = findViewById(R.id.ep_medications_et);
        etAllergies = findViewById(R.id.ep_allergies_et);

        diabeticYes = findViewById(R.id.ep_diabetic_yes);
        diabeticNo = findViewById(R.id.ep_diabetic_no);
        organdonorYes = findViewById(R.id.ep_organdonor_yes);
        organdonorNo = findViewById(R.id.ep_organdonor_no);

        HashMap<String, String> user = userManager.getUserDetails();
        etName.setText(user.get(UserManager.KEY_NAME));
        etAddress.setText(user.get(UserManager.KEY_ADDRESS));
        etBloodtype.setText(user.get(UserManager.KEY_BLOODTYPE));
        etBirthday.setText(user.get(UserManager.KEY_BIRTHDAY));
        etMedications.setText(user.get(UserManager.KEY_MEDICATIONS));
        etAllergies.setText(user.get(UserManager.KEY_ALLERGIES));
        diabetic = user.get(UserManager.KEY_DIABETIC);
        organdonor = user.get(UserManager.KEY_ORGANDONOR);

        if(diabetic.contains("Yes")) {
            diabeticYes.setChecked(true);
            diabetic = "Yes";
        } else {
            diabeticNo.setChecked(true);
            diabetic = "No";
        }

        if(organdonor.contains("Yes")) {
            organdonorYes.setChecked(true);
            organdonor = "Yes";
        } else {
            organdonorNo.setChecked(true);
            organdonor = "No";
        }

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        etBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                DatePickerDialog datePickerDialog = new DatePickerDialog(ProfileEditActivity.this, date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

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
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        etBirthday.setText(sdf.format(myCalendar.getTime()));
    }

    private void setupButtons() {
        ImageView cancelBtn = findViewById(R.id.ep_cancel_btn);
        ImageView saveBtn = findViewById(R.id.ep_save_btn);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cancel = new Intent(ProfileEditActivity.this, ProfileActivity.class);
                startActivity(cancel);
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                if (!etName.getText().toString().isEmpty() && !etBloodtype.getText().toString().isEmpty() &&
                        !etBirthday.getText().toString().isEmpty() && !etAddress.getText().toString().isEmpty() &&
                        !diabetic.isEmpty() && !etMedications.getText().toString().isEmpty() &&
                        !etAllergies.getText().toString().isEmpty() && !organdonor.isEmpty()){
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if(success){
                                    Toast.makeText(ProfileEditActivity.this,"Edit profile successful",Toast.LENGTH_SHORT).show();
                                    Intent cancel = new Intent(ProfileEditActivity.this, ProfileActivity.class);
                                    startActivity(cancel);
                                } else {
                                    Toast.makeText(ProfileEditActivity.this,"Edit profile not successful",Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    HashMap<String, String> user = session.getUserDetails();
                    String username = user.get(SessionManager.KEY_NAME);

                    ProfileUpdateRequest profileUpdateRequest = new ProfileUpdateRequest(username,
                            etName.getText().toString(),etBloodtype.getText().toString(),etBirthday.getText().toString(),etAddress.getText().toString(),
                            diabetic,etMedications.getText().toString(),etAllergies.getText().toString(),organdonor,responseListener);
                    RequestQueue queue = Volley.newRequestQueue(ProfileEditActivity.this);
                    queue.add(profileUpdateRequest);

                    SigninActivity signinActivity = new SigninActivity();
                    signinActivity.getUserDetails(getApplicationContext());
                }
                else
                    Toast.makeText(ProfileEditActivity.this,"No field must be empty",Toast.LENGTH_SHORT).show();

                //Log.d("ProfileEditForDB","name = "+etName.getText());
                //Log.d("ProfileEditForDB","bloodtype = "+etBloodtype.getText());
                //Log.d("ProfileEditForDB","birthday = "+etBirthday.getText());
                //Log.d("ProfileEditForDB","address = "+etAddress.getText());
                //Log.d("ProfileEditForDB","diabetic = "+diabetic);
                //Log.d("ProfileEditForDB","medications = "+etMedications.getText());
                //Log.d("ProfileEditForDB","allergies = "+etAllergies.getText());
                //Log.d("ProfileEditForDB","organdonor = "+organdonor);
            }
        });
    }

}
