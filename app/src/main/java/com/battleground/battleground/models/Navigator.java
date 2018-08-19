package com.battleground.battleground.models;

import com.google.firebase.auth.FirebaseUser;

public interface Navigator {
    void navigateToLoginActivity();
    void navigateToOverviewActivity();
    void navigateToRegisterActivity();
    void navigateToWelcomeActivity();
    void navigateToChooseTeamActivity();
    void navigateToShopActivity();
    void navigateToBattleActivity();
}
