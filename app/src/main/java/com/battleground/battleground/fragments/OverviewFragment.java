package com.battleground.battleground.fragments;


import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.battleground.battleground.R;
import com.battleground.battleground.models.Navigator;
import com.battleground.battleground.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class OverviewFragment extends Fragment {

    private Navigator navigator;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;
    private static Bundle mExtras;
    private TextView mTextView;
    private TextView mNavHeaderTitle;
    private DrawerLayout mDrawerLayout;
    private User mUser;
    private TextView mEmailTextView;
    private TextView mAgeTextView;
    private TextView mDaysSinceRegisteredTextView;
    private TextView mGender;
    private TextView mClan;
    private TextView mHeroes;
    private TextView mTeam;
    private TextView mGold;
    private TextView mBattles;
    private TextView mGoldWon;
    private TextView mDamageDealt;

    public OverviewFragment() {
        // Required empty public constructor
    }

    public static OverviewFragment instance(Bundle bundle) {
        OverviewFragment overviewFragment = new OverviewFragment();

        mExtras = bundle;

        return overviewFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_overview, container, false);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

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

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                mDrawerLayout = getActivity().findViewById(R.id.drawer_layout);
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        mEmailTextView = view.findViewById(R.id.fragment_overview_email);
        mEmailTextView.setText("Email: " + mAuth.getCurrentUser().getEmail());
        mAgeTextView = view.findViewById(R.id.fragment_overview_age);
        mDaysSinceRegisteredTextView = view.findViewById(R.id.fragment_overview_registration_days);
        mGender = view.findViewById(R.id.fragment_overview_gender);
        mClan = view.findViewById(R.id.fragment_overview_clan);
        mHeroes = view.findViewById(R.id.fragment_overview_heroes);
        mTeam = view.findViewById(R.id.fragment_overview_team);
        mGold = view.findViewById(R.id.fragment_overview_gold);
        mBattles = view.findViewById(R.id.fragment_overview_battles);
        mGoldWon = view.findViewById(R.id.fragment_overview_goldWon);
        mDamageDealt = view.findViewById(R.id.fragment_overview_damageDealt);

        return view;
    }

    private void showData(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            mUser = ds.child(userID).getValue(User.class);
            LocalDate birthday = LocalDate.of(1996, 2, 29);
            int age = calculateAge(birthday, LocalDate.now());
            mAgeTextView.setText("Age: " + String.valueOf(mUser.getYearsUser()));
            String[] dateUserRegistered = mUser.getDateUserRegistered().split("-");
            int yearUserRegistered = Integer.parseInt(dateUserRegistered[0]);
            int monthUserRegistered = Integer.parseInt(dateUserRegistered[1]);
            int dayUserRegistered = Integer.parseInt(dateUserRegistered[2]);
            mDaysSinceRegisteredTextView.setText("Days since registration: " + String.valueOf(ChronoUnit.YEARS.between(LocalDate.of(yearUserRegistered, monthUserRegistered, dayUserRegistered), LocalDate.now())) + " days");

            mGender.setText("Gender: " + mUser.getGender().toString().charAt(0));

            if (mUser.getClan() == null) {
                mClan.setText("Still not in a clan");
            } else {
                mClan.setText("Clan: " + mUser.getClan().getName());
            }

            mHeroes.setText("Number of heroes: " + mUser.getHeroes().size());

            mTeam.setText("Team: " + mUser.getTeam().toString());

            mGold.setText("Gold: " + String.valueOf(mUser.getGold()));

            mBattles.setText("Number of battles: " + mUser.getStatistics().getBattles());

            mGoldWon.setText("All time gold won: " + mUser.getStatistics().getGoldWon());

            mDamageDealt.setText("All time damage dealt: " + mUser.getStatistics().getDamageDealt());
        }
    }

    public static int calculateAge(LocalDate birthDate, LocalDate currentDate) {
        if ((birthDate != null) && (currentDate != null)) {
            return Period.between(birthDate, currentDate).getYears();
        } else {
            return 0;
        }
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

    public void setNavigator(Navigator navigator) {
        this.navigator = navigator;
    }
}
