package com.battleground.battleground.models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.battleground.battleground.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SliderAdapterSuperVillains extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ImageView slideImageView;
    private TextView mName;
    private TextView mDescription;
    private TextView mDescription2;
    private Button button;
    private DatabaseReference myRef;
    private User mUser;
    private String userID;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private List<Integer> pictureSuperHeroes = new ArrayList<>();
    private List<String> nameSuperHeroes = new ArrayList<>();
    private List<Integer> priceSuperheroes = new ArrayList<>();
    private List<String> descriptionSuperheroes = new ArrayList<>();
    private List<String> descriptionSuperheroes2 = new ArrayList<>();
    private List<Integer> attackSuperHeroes = new ArrayList<>();
    private List<Integer> defenceSuperHeroes = new ArrayList<>();
    private Map<String, Hero> boughtHeroes = new HashMap<>();

    public SliderAdapterSuperVillains(Context context) {
        this.context = context;

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
                //        pictureSuperHeroes.add(R.drawable.wonder_woman);
                for (Map.Entry<String, Hero> entry : boughtHeroes.entrySet())
                {
                    Log.d("IMPORTANT", String.valueOf(entry.getKey()));
                }
                Log.d("IMPORTANT", String.valueOf(boughtHeroes.size()));
                //Log.d("IMPORTANT", String.valueOf(boughtHeroes.containsKey("IRON MAN")));
                if (!boughtHeroes.containsKey("MANDARIN") && !nameSuperHeroes.contains("MANDARIN")) {
                    pictureSuperHeroes.add(R.drawable.mandarin);
                    nameSuperHeroes.add("MANDARIN");
                    priceSuperheroes.add(5500);
                    descriptionSuperheroes.add("The Mandarin was a major foe of Iron Man in The Marvel Super Heroes, voiced by Bernard Cowan.");
                    descriptionSuperheroes2.add("ATTACK: 3 DEFENCE: 3");
                    attackSuperHeroes.add(3);
                    defenceSuperHeroes.add(3);
                }
                if (!boughtHeroes.containsKey("SAMUEL STERNS")&& !nameSuperHeroes.contains("SAMUEL STERNS")) {
                    pictureSuperHeroes.add(R.drawable.samuel);
                    nameSuperHeroes.add("SAMUEL STERNS");
                    priceSuperheroes.add(9500);
                    descriptionSuperheroes.add("Samuel Sterns was a confidant of Bruce Banner in the 2008 film Incredible Hulk who helped empower the Abomination ");
                    descriptionSuperheroes2.add("ATTACK: 7 DEFENCE: 2");
                    attackSuperHeroes.add(7);
                    defenceSuperHeroes.add(2);
                }

                if (!boughtHeroes.containsKey("CARL CREEL")&& !nameSuperHeroes.contains("CARL CREEL")) {
                    pictureSuperHeroes.add(R.drawable.carl_creel);
                    nameSuperHeroes.add("CARL CREEL");
                    priceSuperheroes.add(16000);
                    descriptionSuperheroes.add("Carl Creel was abducted by Loki, and imbued with the powers of matter absorbtion in order to battle Loki's enemies, Thor and Odin.");
                    descriptionSuperheroes2.add("ATTACK: 13 DEFENCE: 0");
                    attackSuperHeroes.add(13);
                    defenceSuperHeroes.add(0);
                }

                if (!boughtHeroes.containsKey("RED SKULL")&& !nameSuperHeroes.contains("RED SKULL")){
                    pictureSuperHeroes.add(R.drawable.red_skull);
                    nameSuperHeroes.add("RED SKULL");
                    priceSuperheroes.add(25000);
                    descriptionSuperheroes.add("Red Skull: Captain America's archenemy, a Nazi super-soldier and Hitler's successor.");
                    descriptionSuperheroes2.add("ATTACK: 15 DEFENCE: 0");
                    attackSuperHeroes.add(15);
                    defenceSuperHeroes.add(0);
                }

                if (!boughtHeroes.containsKey("DORMAMMU")&& !nameSuperHeroes.contains("DORMAMMU")) {
                    pictureSuperHeroes.add(R.drawable.dorm);
//        nameSuperHeroes.add("WONDER WOMAN");

                    nameSuperHeroes.add("DORMAMMU");
//        priceSuperheroes.add(0);
                    priceSuperheroes.add(70000);
//        descriptionSuperheroes.add("Before she was Wonder Woman (Gal Gadot), she was Diana, princess of the Amazons, trained to be an unconquerable warrior. She was raised on a sheltered island paradise");
                    descriptionSuperheroes.add("Abilities: Mystic energy manipulation; Mastery of dark magic; Dimensional teleportation; Immortality");
//        descriptionSuperheroes2.add("ATTACK: 2 DEFENCE: 0");
                    descriptionSuperheroes2.add("ATTACK: 25 DEFENCE: 4");
//        attackSuperHeroes.add(2);

                    attackSuperHeroes.add(20);
//        defenceSuperHeroes.add(0);

                    defenceSuperHeroes.add(8);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public int getCount() {
        return pictureSuperHeroes.size();
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
        slideImageView.setImageResource(pictureSuperHeroes.get(position));

        mName = view.findViewById(R.id.slide_layout_name);
        mName.setText(nameSuperHeroes.get(position));

        button = view.findViewById(R.id.buy_hero_button);
        button.setText(String.valueOf(priceSuperheroes.get(position)));

        mDescription = view.findViewById(R.id.slide_layout_description);
        mDescription.setText(descriptionSuperheroes.get(position));

        mDescription2 = view.findViewById(R.id.slide_layout_description2);
        mDescription2.setText(descriptionSuperheroes2.get(position));

        button.setOnClickListener((View v) -> {
            /**
             *     private List<Integer> pictureSuperHeroes = new ArrayList<>();
             private List<String> nameSuperHeroes = new ArrayList<>();
             private List<Integer> priceSuperheroes = new ArrayList<>();
             private List<String> descriptionSuperheroes = new ArrayList<>();
             private List<String> descriptionSuperheroes2 = new ArrayList<>();
             private List<Integer> attackSuperHeroes = new ArrayList<>();
             private List<Integer> defenceSuperHeroes = new ArrayList<>();
             */
//            ImageView slideImageView2 = v.findViewById(R.id.slide_image);
//            TextView mName2 = v.findViewById(R.id.slide_layout_name);
//            TextView mDescription2 = v.findViewById(R.id.slide_layout_description);
//            TextView mDescription22 = v.findViewById(R.id.slide_layout_description2);
//            String[] mDescription22Split = mDescription22.getText().toString().split(" ");
//            int attack = Integer.parseInt(mDescription22Split[1]);
//            int defence = Integer.parseInt(mDescription22Split[3]);
            if (mUser.getGold() >= priceSuperheroes.get(position)){
                mUser.setGold(mUser.getGold() - priceSuperheroes.get(position));
                mUser.getHeroes().put(nameSuperHeroes.get(position), new Hero(nameSuperHeroes.get(position), attackSuperHeroes.get(position), defenceSuperHeroes.get(position)));
                mUser.setStrength(mUser.getStrength() + attackSuperHeroes.get(position) + defenceSuperHeroes.get(position));
                mFirebaseDatabase.getReference("Users")
                        .child(mAuth.getCurrentUser().getUid())
                        .setValue(mUser);
                pictureSuperHeroes.remove(position);
                nameSuperHeroes.remove(position);
                priceSuperheroes.remove(position);
                descriptionSuperheroes.remove(position);
                descriptionSuperheroes2.remove(position);
                attackSuperHeroes.remove(position);
                defenceSuperHeroes.remove(position);
                this.notifyDataSetChanged();
            }
            else {
                Snackbar.make(view, "Not enough money", Snackbar.LENGTH_SHORT).show();
                this.notifyDataSetChanged();
            }
        });

        container.addView(view);

        return view;
    }

    private void showData(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            mUser = ds.child(userID).getValue(User.class);
            for (Map.Entry<String, Hero> entry : mUser.getHeroes().entrySet())
            {
                boughtHeroes.put(entry.getKey(), entry.getValue());
            }
            Log.d("NAME", mUser.getEmail());
            Log.d("NUM", String.valueOf(mUser.getHeroes().size()));
        }
    }

    public Button getBuyButton() {
        return button;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager) container).removeView((LinearLayout) object);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }
}
