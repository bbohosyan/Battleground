package com.battleground.battleground.fragments;


import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.battleground.battleground.R;
import com.battleground.battleground.models.Gender;
import com.battleground.battleground.models.Navigator;
import com.battleground.battleground.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

//import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {

    private Navigator navigator;
    private FirebaseAuth mAuth;
    private EditText mEditTextEmail;
    private String mEmail;
    private EditText mEditTextPassword;
    private String mPassword;
    private Button mRegisterButton;
    private ProgressBar mProgressBar;
    private Spinner mGenderSpinner;
    private TextView mBirthDateTextView;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private int yearOfBirth;
    private int monthOfBirth;
    private int dayOfBirth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatePickerDialog datePickerDialog;

    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment instance() {
        RegisterFragment registerFragment = new RegisterFragment();
        return registerFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        mEditTextEmail = view.findViewById(R.id.register_email);
        mEditTextPassword = view.findViewById(R.id.register_password);

        mProgressBar = view.findViewById(R.id.register_progress);
        mProgressBar.setVisibility(View.GONE);

        mGenderSpinner = view.findViewById(R.id.register_gender);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                getContext(),R.layout.spinner_item, new String[]{Gender.FEMALE.toString(), Gender.MALE.toString()}
        );
//        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        mGenderSpinner.setAdapter(spinnerArrayAdapter);

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                String date = month + "/" + day + "/" + year;
                yearOfBirth = year;
                monthOfBirth = month;
                dayOfBirth = day;
                mBirthDateTextView.setText(date);
            }
        };
        mBirthDateTextView = view.findViewById(R.id.register_date);
        mBirthDateTextView.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        mRegisterButton = view.findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_button:
                clickMRegisterButton();
                break;
            case R.id.register_date:
                mBirthDateTextView();
                break;
            default:
                break;
        }
    }

    private void mBirthDateTextView() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(getContext(),
                android.R.style.Theme_Holo_Dialog_MinWidth,
                mDateSetListener,
                year, month, day);

        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();
    }

    private void clickMRegisterButton() {
        mEmail = mEditTextEmail.getText().toString().trim();
        mPassword = mEditTextPassword.getText().toString().trim();
        if (mEmail.isEmpty()) {
            mEditTextEmail.setError("Email is required");
            mEditTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
            mEditTextEmail.setError("Email is not valid");
            mEditTextEmail.requestFocus();
            return;
        }
        if (mPassword.isEmpty()) {
            mEditTextPassword.setError("Password is required");
            mEditTextPassword.requestFocus();
            return;
        }
        if (mBirthDateTextView.getText().toString().isEmpty()){
            mBirthDateTextView.setError("Birthdate is required");
            mBirthDateTextView.requestFocus();
            return;
        }
        mProgressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(mEmail, mPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mProgressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            final User user = new User(mAuth.getCurrentUser().getEmail(), Gender.valueOf(mGenderSpinner.getSelectedItem().toString()), LocalDate.of(datePickerDialog.getDatePicker().getYear(), datePickerDialog.getDatePicker().getMonth(), datePickerDialog.getDatePicker().getDayOfMonth()).toString());
                            //Log.d("GENDER", user.getGender().toString());
                            mFirebaseDatabase.getReference("Users")
                                    .child(mAuth.getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        navigator.navigateToChooseTeamActivity();
                                        Toast.makeText(getContext(), "LIMONADAAA", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(getContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                            //navigator.navigateToOverviewActivity(mAuth.getCurrentUser());
                        } else {
                            if (task.getException() instanceof FirebaseAuthWeakPasswordException) {
                                Toast.makeText(getContext(), "Password should have more than 5 characters", Toast.LENGTH_SHORT).show();
                            } else if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(getContext(), "You are already registered", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    public void setNavigator(Navigator navigator) {
        this.navigator = navigator;
    }
}
