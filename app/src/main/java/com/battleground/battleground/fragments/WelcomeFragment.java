package com.battleground.battleground.fragments;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.battleground.battleground.R;
import com.battleground.battleground.models.Navigator;

/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFragment extends Fragment implements View.OnClickListener {

    private Navigator navigator;
    private Button mLoginButton;
    private Button mRegisterButton;
    private Button mLoginButtonText;
    private Button mRegisterButtonText;


    public WelcomeFragment() {
        // Required empty public constructor
    }

    public static WelcomeFragment instance(){
        WelcomeFragment welcomeFragment = new WelcomeFragment();
        return welcomeFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        mLoginButton = view.findViewById(R.id.welcome_button_login);
        mLoginButton.setOnClickListener(this);
        mRegisterButton = view.findViewById(R.id.welcome_button_register);
        mRegisterButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.welcome_button_login:
                navigator.navigateToLoginActivity();
                break;

            case R.id.welcome_button_register:
                navigator.navigateToRegisterActivity();
                break;

            default:
                break;
        }
    }

    public void setNavigator(Navigator navigator) {
        this.navigator = navigator;
    }
}
