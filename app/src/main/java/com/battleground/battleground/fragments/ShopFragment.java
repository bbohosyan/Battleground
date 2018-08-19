package com.battleground.battleground.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.battleground.battleground.R;
import com.battleground.battleground.models.Navigator;
import com.battleground.battleground.models.SliderAdapterSuperHeroes;
import com.battleground.battleground.models.SliderAdapterSuperVillains;
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
public class ShopFragment extends Fragment implements View.OnClickListener {

    private DrawerLayout mDrawerLayout;
    private Navigator navigator;
    private ViewPager mSlideViewPager;
    private SliderAdapterSuperHeroes sliderAdapterSuperHeroes;
    private SliderAdapterSuperVillains sliderAdapterSuperVillains;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private User mUser;
    private String userID;
    private FirebaseDatabase mFirebaseDatabase;
    private Button button;

    public ShopFragment() {
        // Required empty public constructor
    }

    public static ShopFragment instance() {
        ShopFragment shopFragment = new ShopFragment();
        return shopFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop, container, false);

        mSlideViewPager = view.findViewById(R.id.slideViewPager);

        sliderAdapterSuperHeroes = new SliderAdapterSuperHeroes(getContext());
        sliderAdapterSuperVillains = new SliderAdapterSuperVillains(getContext());

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fragment_shop_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                mDrawerLayout = getActivity().findViewById(R.id.activity_shop_drawer);
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
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

        return view;
    }

    private void showData(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            mUser = ds.child(userID).getValue(User.class);
        }
        if (mUser.getTeam().equals(Team.SUPERVILLAINS)) {
            mSlideViewPager.setAdapter(sliderAdapterSuperVillains);
        } else {
            mSlideViewPager.setAdapter(sliderAdapterSuperHeroes);
        }
    }

    public void setNavigator(Navigator navigator) {
        this.navigator = navigator;
    }

    public void buyHero() {
        mUser.setGold(mUser.getGold() - 5000);
        mFirebaseDatabase.getReference("Users")
                .child(mAuth.getCurrentUser().getUid())
                .setValue(mUser);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(getContext(), "KURVII", Toast.LENGTH_SHORT).show();
    }
}
