package com.example.marj.safeph.signup;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.marj.safeph.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

/**
 * Created by Marj on 4/3/2018.
 */

public class SignupBasicActivity extends AppCompatActivity{
    EditText etName;
    EditText etBloodtype;
    EditText etBirthday;
    EditText etAddress;

    Calendar myCalendar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_basic);

        myCalendar = Calendar.getInstance();

        setupUI();
        setupButtons();
    }

    private void setupUI() {
        etName = findViewById(R.id.b_name_et);
        etBloodtype = findViewById(R.id.b_blood_type_et);
        etBirthday = findViewById(R.id.b_birthday_et);
        etAddress = findViewById(R.id.b_address_et);

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
                DatePickerDialog datePickerDialog = new DatePickerDialog(SignupBasicActivity.this, date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        Intent data = getIntent();
        if(!Objects.equals(data.getStringExtra("name"), ""))
            etName.setText(data.getStringExtra("name"));
        if(!Objects.equals(data.getStringExtra("bloodtype"), ""))
            etBloodtype.setText(data.getStringExtra("bloodtype"));
        if(!Objects.equals(data.getStringExtra("birthday"), ""))
            etBirthday.setText(data.getStringExtra("birthday"));
        if(!Objects.equals(data.getStringExtra("address"), ""))
            etAddress.setText(data.getStringExtra("address"));
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        etBirthday.setText(sdf.format(myCalendar.getTime()));
    }

    private void setupButtons() {
        ImageView nextBtn = findViewById(R.id.b_next_btn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = getIntent();
                Intent intent = new Intent(SignupBasicActivity.this,SignupMedicalActivity.class);

                intent.putExtra("username", data.getStringExtra("username"));
                intent.putExtra("password", data.getStringExtra("password"));
                intent.putExtra("name", etName.getText().toString());
                intent.putExtra("bloodtype", etBloodtype.getText().toString());
                intent.putExtra("birthday", etBirthday.getText().toString());
                intent.putExtra("address", etAddress.getText().toString());
                intent.putExtra("diabetic", data.getStringExtra("diabetic"));
                intent.putExtra("medications", data.getStringExtra("medications"));
                intent.putExtra("allergies", data.getStringExtra("allergies"));
                intent.putExtra("organdonor", data.getStringExtra("organdonor"));

//                Log.d("Basic","username = "+data.getStringExtra("username"));
//                Log.d("Basic","password = "+data.getStringExtra("password"));
//                Log.d("Basic","name = "+etName.getText().toString());
//                Log.d("Basic","bloodtype = "+etBloodtype.getText().toString());
//                Log.d("Basic","birthday = "+etBirthday.getText().toString());
//                Log.d("Basic","address = "+etAddress.getText().toString());
//                Log.d("Basic","diabetic = "+data.getStringExtra("diabetic"));
//                Log.d("Basic","medications = "+data.getStringExtra("medications"));
//                Log.d("Basic","allergies = "+data.getStringExtra("allergies"));
//                Log.d("Basic","organdonor = "+data.getStringExtra("organdonor"));

                if (etName.getText().toString().isEmpty() || etBloodtype.getText().toString().isEmpty() ||
                        etBirthday.getText().toString().isEmpty() || etAddress.getText().toString().isEmpty()){
                    Toast.makeText(SignupBasicActivity.this, "No field must be empty", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(intent);
                }
            }
        });

        ImageView medicalBtn = findViewById(R.id.b_medical_btn);
        medicalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = getIntent();
                Intent intent = new Intent(SignupBasicActivity.this,SignupMedicalActivity.class);

                intent.putExtra("username", data.getStringExtra("username"));
                intent.putExtra("password", data.getStringExtra("password"));
                intent.putExtra("name", etName.getText().toString());
                intent.putExtra("bloodtype", etBloodtype.getText().toString());
                intent.putExtra("birthday", etBirthday.getText().toString());
                intent.putExtra("address", etAddress.getText().toString());
                intent.putExtra("diabetic", data.getStringExtra("diabetic"));
                intent.putExtra("medications", data.getStringExtra("medications"));
                intent.putExtra("allergies", data.getStringExtra("allergies"));
                intent.putExtra("organdonor", data.getStringExtra("organdonor"));

//                Log.d("Basic","username = "+data.getStringExtra("username"));
//                Log.d("Basic","password = "+data.getStringExtra("password"));
//                Log.d("Basic","username = "+etName.getText().toString());
//                Log.d("Basic","bloodtype = "+etBloodtype.getText().toString());
//                Log.d("Basic","birthday = "+etBirthday.getText().toString());
//                Log.d("Basic","address = "+etAddress.getText().toString());
//                Log.d("Basic","diabetic = "+data.getStringExtra("diabetic"));
//                Log.d("Basic","medications = "+data.getStringExtra("medications"));
//                Log.d("Basic","allergies = "+data.getStringExtra("allergies"));
//                Log.d("Basic","organdonor = "+data.getStringExtra("organdonor"));

                if (etName.getText().toString().isEmpty() || etBloodtype.getText().toString().isEmpty() ||
                        etBirthday.getText().toString().isEmpty() || etAddress.getText().toString().isEmpty()){
                    Toast.makeText(SignupBasicActivity.this, "No field must be empty", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(intent);
                }
            }
        });
    }
}
