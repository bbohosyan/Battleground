package com.battleground.battleground.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.battleground.battleground.R;
import com.battleground.battleground.models.Hero;
import com.battleground.battleground.models.Navigator;
import com.battleground.battleground.models.Team;
import com.battleground.battleground.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseTeamFragment extends Fragment implements View.OnClickListener {

    private ImageView mSuperheroesImageView;
    private ImageView mSupervillainsImageView;
    private Navigator navigator;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;
    private User currentUser;

    public ChooseTeamFragment() {
        // Required empty public constructor
    }

    public static ChooseTeamFragment instance() {
        ChooseTeamFragment chooseTeamFragment = new ChooseTeamFragment();

        return chooseTeamFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_team, container, false);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        setAuthListener();

        myRefValueEventListener();

        mSuperheroesImageView = view.findViewById(R.id.choose_team_superheroes);
        mSuperheroesImageView.setClickable(true);
        mSuperheroesImageView.setOnClickListener(this);

        mSupervillainsImageView = view.findViewById(R.id.choose_team_supervillains);
        mSupervillainsImageView.setClickable(true);
        mSupervillainsImageView.setOnClickListener(this);

        return view;
    }

    public void setNavigator(Navigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.choose_team_superheroes:
                clickSuperheroesImage();
                break;
            case R.id.choose_team_supervillains:
                clickSupervillainsImage();
                break;
        }
    }

    private void clickSupervillainsImage() {
        currentUser.setTeam(Team.SUPERVILLAINS);
        currentUser.getHeroes().remove(Hero.WONDERWOMAN_NAME);
        currentUser.getHeroes().put(Hero.JOKER_NAME, new Hero(Hero.JOKER_NAME, Hero.JOKER_ATTACK, Hero.JOKER_DEFENCE));
        mFirebaseDatabase.getReference("Users")
                .child(mAuth.getCurrentUser().getUid())
                .setValue(currentUser);
        navigator.navigateToOverviewActivity();
    }

    private void clickSuperheroesImage() {
        currentUser.setTeam(Team.SUPERHEROES);
        currentUser.setStrength(currentUser.getHeroes().get(Hero.WONDERWOMAN_NAME).attack + currentUser.getHeroes().get(Hero.WONDERWOMAN_NAME).defence);
        mFirebaseDatabase.getReference("Users")
                .child(mAuth.getCurrentUser().getUid())
                .setValue(currentUser);
        navigator.navigateToOverviewActivity();
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
            //currentUser.setGender(currentUser.getGender());
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

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
