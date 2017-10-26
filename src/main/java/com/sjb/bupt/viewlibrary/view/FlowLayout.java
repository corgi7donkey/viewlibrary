package com.sjb.bupt.viewlibrary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjb on 2017/10/25.
 */

public class FlowLayout extends ViewGroup {
    //存储每行的view
    private List<List<View>> mViews;
    private List<Integer> mLineHeight;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mViews = new ArrayList<>();
        mLineHeight = new ArrayList<>();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //wrap_content
        int width = 0;
        int height = 0;

        int lineHeight = 0;
        int lineWidth = 0;

        int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            //测量子view的宽高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams mp = (MarginLayoutParams) child.getLayoutParams();

            int childWidth = child.getMeasuredWidth() + mp.rightMargin + mp.leftMargin;
            int childHeight = child.getMeasuredHeight() + mp.topMargin + mp.bottomMargin;

            if (lineWidth + childWidth > widthSize-getPaddingLeft()-getPaddingRight()) {
                width = Math.max(width, lineWidth);
                lineWidth = childWidth;
                height += lineHeight;
                lineHeight = childHeight;
            } else {
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }
            if (i == cCount-1) {
                height += lineHeight;
                width = Math.max(width, lineWidth);
            }
        }
        setMeasuredDimension(widthMode == MeasureSpec.AT_MOST ? width+getPaddingLeft()+getPaddingRight() : widthSize,
                heightMode == MeasureSpec.AT_MOST ? height+getPaddingBottom()+getPaddingRight() : heightSize);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        mViews.clear();
        mLineHeight.clear();

        int width=getWidth();

        int lineWidth=0;
        int lineHeight=0;

        int cCount=getChildCount();
        List<View> lineViews = new ArrayList<>();
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams mp = (MarginLayoutParams) child.getLayoutParams();

            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            if (lineWidth + childWidth + mp.leftMargin + mp.rightMargin > width-getPaddingRight()-getPaddingLeft()) {//换行
                mLineHeight.add(lineHeight);
                mViews.add(lineViews);

                lineWidth=0;
                lineHeight = childHeight +mp.topMargin+mp.bottomMargin;
                lineViews = new ArrayList<>();
            }
                lineWidth+=childWidth+mp.leftMargin+mp.rightMargin;
                lineHeight = Math.max(lineHeight, childHeight + mp.topMargin + mp.bottomMargin);
                lineViews.add(child);
        }
        //处理最后一行
        mLineHeight.add(lineHeight);
        mViews.add(lineViews);


        //放置子view
        int left=getPaddingLeft();
        int top=getPaddingTop();

        int lineNums=mViews.size();
        for (int i = 0; i < lineNums; i++) {
            lineViews = mViews.get(i);
            lineHeight = mLineHeight.get(i);

            for (int j = 0; j < lineViews.size(); j++) {
                View child = lineViews.get(j);
                if (child.getVisibility() == View.GONE) {
                    continue;
                }
                MarginLayoutParams mp = (MarginLayoutParams) child.getLayoutParams();
                int lc=left+mp.leftMargin;
                int tc=top+mp.topMargin;
                int rc=lc+child.getMeasuredWidth();
                int bc=tc+child.getMeasuredHeight();

                //放置子view
                child.layout(lc, tc, rc, bc);

                left+=child.getMeasuredWidth()+mp.leftMargin+mp.rightMargin;
            }
            left=getPaddingLeft();
            top+=lineHeight;
        }

    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
