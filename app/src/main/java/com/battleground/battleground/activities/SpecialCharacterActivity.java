package com.battleground.battleground.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.battleground.battleground.R;
import com.battleground.battleground.models.SpecialCharacterView;

public class SpecialCharacterActivity extends AppCompatActivity implements View.OnClickListener {

    private SpecialCharacterView mSpecialCharacterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SpecialCharacterView specialCharacterView = new SpecialCharacterView(this);
        setContentView(R.layout.activity_special_character);
        mSpecialCharacterView = findViewById(R.id.special_character);
        findViewById(R.id.increase_gold).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mSpecialCharacterView.changeColor();
    }
}
