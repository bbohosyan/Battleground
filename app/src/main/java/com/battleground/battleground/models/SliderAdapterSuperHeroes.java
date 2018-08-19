package com.battleground.battleground.models;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.battleground.battleground.R;
import com.battleground.battleground.fragments.ShopFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapterSuperHeroes extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ImageView slideImageView;
    private Button button;
    private DatabaseReference myRef;
    private User mUser;
    private String userID;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;

    public SliderAdapterSuperHeroes(Context context){
        this.context = context;
        superheroes.add(R.drawable.iron_man);
        superheroes.add(R.drawable.hulk);
    }

    List<Integer> superheroes = new ArrayList<>();

    @Override
    public int getCount() {
        return superheroes.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        slideImageView = (ImageView) view.findViewById(R.id.slide_image);
        slideImageView.setImageResource(superheroes.get(position));
        button = view.findViewById(R.id.buy_hero_button);
        button.setText("Aaaa");

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

        button.setOnClickListener((View v) -> {
            mUser.setGold(mUser.getGold() - 5000);
            mFirebaseDatabase.getReference("Users")
                    .child(mAuth.getCurrentUser().getUid())
                    .setValue(mUser);
            superheroes.remove(position);
            this.notifyDataSetChanged();
        });

        container.addView(view);

        return view;
    }

    private void showData(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            mUser = ds.child(userID).getValue(User.class);
        }
    }

    public Button getBuyButton(){
        return button;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager) container).removeView((LinearLayout)object);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }
}
