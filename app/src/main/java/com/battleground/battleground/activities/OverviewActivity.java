package com.battleground.battleground.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.battleground.battleground.R;
import com.battleground.battleground.fragments.OverviewFragment;
import com.battleground.battleground.models.Navigator;
import com.google.firebase.auth.FirebaseAuth;

public class OverviewActivity extends AppCompatActivity implements Navigator {

    private DrawerLayout mDrawerLayout;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        setDrawer();

        OverviewFragment overviewFragment = OverviewFragment.instance(getIntent().getExtras());
        overviewFragment.setNavigator(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_overview, overviewFragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                return true;
        }
        return super.onOptionsItemSelected(item);
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

    @Override
    public void navigateToBattleActivity() {
        Intent intent = new Intent(this, BattleActivity.class);
        startActivity(intent);
    }

    private void setDrawer() {
        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.activity_overview_nav_view);
        navigationView.setCheckedItem(R.id.nav_profile);
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    menuItem.setChecked(true);
                    switch (menuItem.getItemId()){
                        case R.id.nav_profile:
                            navigateToOverviewActivity();
                            break;
                        case R.id.nav_shop:
                            navigateToShopActivity();
                            break;
                        case R.id.nav_battle:
                            navigateToBattleActivity();
                            break;
                    }
                    mDrawerLayout.closeDrawers();
                    return true;
                });

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.nav_head_text);

        mAuth = FirebaseAuth.getInstance();
        navUsername.setText(mAuth.getCurrentUser().getEmail().toString());


        mDrawerLayout.bringToFront();
    }
}
