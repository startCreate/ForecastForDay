package com.user.vladimir_voronov_openweathermap.screens.adapter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.user.vladimir_voronov_openweathermap.R;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyItemDecorator extends RecyclerView.ItemDecoration {

    @Nullable
    @BindView(R.id.location) TextView locationView;
    private Paint paintGrey;

    public MyItemDecorator() {
        paintGrey = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintGrey.setStyle(Paint.Style.STROKE);
        paintGrey.setStrokeWidth(3);
    }

    @Override public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        paintGrey.setColor(parent.getContext().getResources().getColor(R.color.divider));
        ButterKnife.bind(this, parent);

        for (int i = 0; i < parent.getChildCount(); i++) {
            final View child = parent.getChildAt(i);
            c.drawLine(child.getLeft() + locationView.getLeft(), child.getBottom(), child.getRight(), child.getBottom(), paintGrey);
        }
    }

    @Override public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int offset = 15;
        outRect.set(offset, offset, offset, offset);
    }
}
