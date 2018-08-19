package com.battleground.battleground.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.battleground.battleground.R;
import com.battleground.battleground.fragments.LoginFragment;
import com.battleground.battleground.models.Navigator;
import com.battleground.battleground.models.User;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements Navigator {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginFragment loginFragment = LoginFragment.instance();
        loginFragment.setNavigator(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.login_layout, loginFragment).commit();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @Override
    public void navigateToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void navigateToOverviewActivity() {
        Intent intent = new Intent(this, OverviewActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateToRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateToWelcomeActivity() {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateToChooseTeamActivity() {
        Intent intent = new Intent(this, ChooseTeamActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateToShopActivity() {
        Intent intent = new Intent(this, ShopActivity.class);
        startActivity(intent);
    }
}
