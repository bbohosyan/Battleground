package com.battleground.battleground.models;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class SpecialCharacterView extends View {
    private static final int SQUARE_SIZE = 50;
    private Rect mRectSquare;
    private Paint mPaintSquare;

    public SpecialCharacterView(Context context) {
        super(context);
        init(null);
    }

    public SpecialCharacterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public SpecialCharacterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public SpecialCharacterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(@Nullable AttributeSet set){
        mRectSquare = new Rect();
        mPaintSquare = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintSquare.setColor(Color.GREEN);
    }

    public void changeColor(){
        if (mPaintSquare.getColor() == Color.GREEN){
            mPaintSquare.setColor(Color.YELLOW);
        }
        else {
            mPaintSquare.setColor(Color.GREEN);
        }
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mRectSquare.left = 10;
        mRectSquare.top = 10;
        mRectSquare.right = mRectSquare.left + SQUARE_SIZE;
        mRectSquare.bottom = mRectSquare.top + SQUARE_SIZE;

        canvas.drawRect(mRectSquare, mPaintSquare);
    }
}
