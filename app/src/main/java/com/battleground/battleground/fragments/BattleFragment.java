package com.battleground.battleground.fragments;


import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.battleground.battleground.R;
import com.battleground.battleground.activities.BattleActivity;
import com.battleground.battleground.models.Navigator;
import com.battleground.battleground.models.Team;
import com.battleground.battleground.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 */
public class BattleFragment extends Fragment implements View.OnClickListener {

    private Navigator navigator;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private DatabaseReference myRef2;
    private String userID;
    private static Bundle mExtras;
    private DrawerLayout mDrawerLayout;
    private User mUser;
    private Integer opponentStrength;
    private List<User> users;
    private Button mBattle;
    private int chanceToWinPercent;
    private TextView mResultText;

    public BattleFragment() {
        // Required empty public constructor
    }

    public static BattleFragment instance() {
        BattleFragment battleFragment = new BattleFragment();

        return battleFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_battle, container, false);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        myRef2 = mFirebaseDatabase.getReference("Users");
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData2(dataSnapshot);
                //Toast.makeText(getContext(), String.valueOf(users.size()), Toast.LENGTH_SHORT).show();
                List<User> givenList = new ArrayList<>();
//                for (int i = 0; i < users.size(); i++){
//                    Toast.makeText(getContext(), String.valueOf(users.get(i)), Toast.LENGTH_SHORT).show();
//                }
                givenList = users.stream().map(user1 -> (User) user1).filter(user1 -> !mUser.getTeam().equals(user1.getTeam())).collect(Collectors.toList());
                Random rand = new Random();
                User randomElement = givenList.get(rand.nextInt(givenList.size()));
                TextView textView = view.findViewById(R.id.fragment_battle_opponentEmail);
                textView.setText(randomElement.getEmail());
                ImageView imageView = view.findViewById(R.id.fragment_battle_opponent);
                if (mUser.getTeam().equals(Team.SUPERVILLAINS)) {
                    imageView.setImageResource(R.drawable.superheroes);
                } else {
                    imageView.setImageResource(R.drawable.supervillains);
                }
                TextView chanceToWin = view.findViewById(R.id.fragment_battle_chanceToWin);
                opponentStrength = randomElement.getStrength();
                int difference = mUser.getStrength() - opponentStrength;
                if (difference == 0) {
                    chanceToWinPercent = 50;
                    chanceToWin.setText("Chance to win: 50%");
                } else if (difference > 0) {
                    if (difference >= 50) {
                        chanceToWinPercent = 100;
                        chanceToWin.setText("Chance to win: 100%");
                    } else {
                        chanceToWinPercent = 50 + difference;
                        chanceToWin.setText("Chance to win: " + String.valueOf(50 + difference) + "%");
                    }
                } else {
                    if (difference <= -50) {
                        chanceToWinPercent = 100;
                        chanceToWin.setText("Chance to win: 100%");
                    } else {
                        chanceToWinPercent = 50 - difference;
                        chanceToWin.setText("Chance to win: " + String.valueOf(50 - difference) + "%");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fragment_battle_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                mDrawerLayout = getActivity().findViewById(R.id.activity_battle_drawer);
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        mBattle = view.findViewById(R.id.fragment_battle_battleButton);
        mBattle.setOnClickListener(this);

        mResultText = view.findViewById(R.id.fragment_battle_resultBattleMessage);
        mResultText.setVisibility(View.GONE);

        return view;
    }

    private void showData(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            mUser = ds.child(userID).getValue(User.class);
        }
    }

    private void showData2(DataSnapshot dataSnapshot) {
        users = new ArrayList<>();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            users.add(ds.getValue(User.class));
        }
    }

    public void setNavigator(BattleActivity navigator) {
        this.navigator = navigator;
    }

    @Override
    public void onClick(View view) {
        Random rand = new Random();
        int number = rand.nextInt(101);
        mResultText.setVisibility(View.VISIBLE);
        if (number <= chanceToWinPercent){
            mBattle.setVisibility(View.GONE);
            mResultText.setText("YOU WON!");
        }
        else {
            mBattle.setVisibility(View.GONE);
            mResultText.setText("YOU LOST!");
        }
    }
}
