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

public class SliderAdapterSuperHeroes extends PagerAdapter {

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

    public SliderAdapterSuperHeroes(Context context) {
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
                if (!boughtHeroes.containsKey("IRON MAN") && !nameSuperHeroes.contains("IRON MAN")) {
                    pictureSuperHeroes.add(R.drawable.iron_man);
                    nameSuperHeroes.add("IRON MAN");
                    priceSuperheroes.add(5000);
                    descriptionSuperheroes.add("Genius, billionaire, playboy and philanthropist Tony Stark, who has inherited the defense contractor Stark Industries from his father, is in war-torn Afghanistan.");
                    descriptionSuperheroes2.add("ATTACK: 2 DEFENCE: 4");
                    attackSuperHeroes.add(2);
                    defenceSuperHeroes.add(4);
                }
                if (!boughtHeroes.containsKey("HULK")&& !nameSuperHeroes.contains("HULK")) {
                    pictureSuperHeroes.add(R.drawable.hulk);
                    nameSuperHeroes.add("HULK");
                    priceSuperheroes.add(10000);
                    descriptionSuperheroes.add("Scientist David Banner has the idea to create super soldiers by introducing modified DNA sequences extracted from various animals to strengthen the human body.");
                    descriptionSuperheroes2.add("ATTACK: 4 DEFENCE: 5");
                    attackSuperHeroes.add(4);
                    defenceSuperHeroes.add(5);
                }

                if (!boughtHeroes.containsKey("THOR")&& !nameSuperHeroes.contains("THOR")) {
                    pictureSuperHeroes.add(R.drawable.thor);
                    nameSuperHeroes.add("THOR");
                    priceSuperheroes.add(15000);
                    descriptionSuperheroes.add("Thor is a hammer-wielding god associated with thunder, lightning, storms, oak trees, strength, the protection of mankind, and also hallowing and fertility.");
                    descriptionSuperheroes2.add("ATTACK: 10 DEFENCE: 3");
                    attackSuperHeroes.add(10);
                    defenceSuperHeroes.add(3);
                }

                if (!boughtHeroes.containsKey("CAPTAIN AMERICA")&& !nameSuperHeroes.contains("CAPTAIN AMERICA")){
                    pictureSuperHeroes.add(R.drawable.captain_america);
                    nameSuperHeroes.add("CAPTAIN AMERICA");
                    priceSuperheroes.add(30000);
                    descriptionSuperheroes.add("Captain America has no superhuman powers, but through the Super-Soldier Serum and \"Vita-Ray\" treatment, he is transformed and his strength are at the zenith of natural human potential.");
                    descriptionSuperheroes2.add("ATTACK: 15 DEFENCE: 1");
                    attackSuperHeroes.add(15);
                    defenceSuperHeroes.add(1);
                }

                if (!boughtHeroes.containsKey("DOCTOR STRANGE")&& !nameSuperHeroes.contains("DOCTOR STRANGE")) {
                    pictureSuperHeroes.add(R.drawable.doctor_strange);
//        nameSuperHeroes.add("WONDER WOMAN");

                    nameSuperHeroes.add("DOCTOR STRANGE");
//        priceSuperheroes.add(0);
                    priceSuperheroes.add(60000);
//        descriptionSuperheroes.add("Before she was Wonder Woman (Gal Gadot), she was Diana, princess of the Amazons, trained to be an unconquerable warrior. She was raised on a sheltered island paradise");
                    descriptionSuperheroes.add("In Kathmandu, the sorcerer Kaecilius and his zealots enter the secret compound Kamar-Taj and behead its librarian. They steal a few pages from an ancient, mystical text belonging to the Ancient One, a long-lived sorcerer who has taught every student at Kamar-Taj, including Kaecilius, in the mystic arts.");
//        descriptionSuperheroes2.add("ATTACK: 2 DEFENCE: 0");
                    descriptionSuperheroes2.add("ATTACK: 20 DEFENCE: 8");
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
