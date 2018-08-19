package com.battleground.battleground.fragments;


import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.battleground.battleground.R;
import com.battleground.battleground.models.Navigator;
import com.battleground.battleground.models.Team;
import com.battleground.battleground.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    private Navigator navigator;
    private FirebaseAuth mAuth;
    private EditText mEditTextEmail;
    private String mEmail;
    private EditText mEditTextPassword;
    private String mPassword;
    private Button mLoginButton;
    private ProgressBar mProgressBar;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;
    private static Bundle mExtras;
    private User currentUser;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment instance() {
        LoginFragment loginFragment = new LoginFragment();
        return loginFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        setAuthListener();

        myRefValueEventListener();

        mEditTextEmail = view.findViewById(R.id.login_email);

        mEditTextPassword = view.findViewById(R.id.login_password);

        mAuth = FirebaseAuth.getInstance();

        mProgressBar = view.findViewById(R.id.login_progress);
        mProgressBar.setVisibility(View.GONE);

        mLoginButton = view.findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View view) {
        mEmail = mEditTextEmail.getText().toString().trim();
        mPassword = mEditTextPassword.getText().toString().trim();
        mProgressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(mEmail, mPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mProgressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            if (currentUser != null && currentUser.getTeam() != Team.NOT_SET) {
                                navigator.navigateToOverviewActivity();
                                Log.d("HELLO", "HELLO");
                            }
                            else {
                                navigator.navigateToChooseTeamActivity();
                            }
                            Toast.makeText(getContext(), "SUCCESS", Toast.LENGTH_SHORT).show();
                            // Sign in success, update UI with the signed-in user's information

                        } else {
                            // If sign in fails, display a message to the user.
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                                Toast.makeText(getContext(), "Invalid email or password", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getContext(), "ERROR", Toast.LENGTH_SHORT).show();

                            }
                        }
                        // ...
                    }
                });
    }

    private void setAuthListener() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Toast.makeText(getContext(), "SUCCESS", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "ERROR", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    private void setCurrentUser(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            currentUser = ds.child(userID).getValue(User.class);
            currentUser.setGender(currentUser.getGender());
        }
    }


    private void myRefValueEventListener() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setCurrentUser(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void setNavigator(Navigator navigator) {
        this.navigator = navigator;
    }
}
