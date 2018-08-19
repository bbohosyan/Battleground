package com.battleground.battleground.models;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;

import com.battleground.battleground.R;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class SliderAdapterSuperVillains extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ImageView slideImageView;

    public SliderAdapterSuperVillains(Context context) {
        this.context = context;
    }

    public int[] supervillains = {
            R.drawable.supervillains
    };

    @Override
    public int getCount() {
        return supervillains.length;
    }

    public ImageView getSlideImageView() {
        return slideImageView;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (RelativeLayout) o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        slideImageView = (ImageView) view.findViewById(R.id.slide_image);
        slideImageView.setImageResource(supervillains[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}
