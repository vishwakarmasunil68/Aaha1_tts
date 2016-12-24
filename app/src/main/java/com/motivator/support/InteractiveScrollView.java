package com.motivator.support;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;
public class InteractiveScrollView extends ScrollView {
    OnBottomReachedListener mListener;
    Context context;
    int i=2;
    public InteractiveScrollView(Context context, AttributeSet attrs,
            int defStyle) {
        super(context, attrs, defStyle);
        this.context=context;
    }

    public InteractiveScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
    }

    public InteractiveScrollView(Context context) {
        super(context);
        this.context=context;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        View view = (View) getChildAt(getChildCount()-1);
        int diff = (view.getBottom()-(getHeight()+getScrollY()));
        
        if (diff == 0 ) {
//            mListener.onBottomReached();
//            Log.d("sunil","botton reached");

            if(i%2==0){
            	++i;
                Log.d("sunil","green");
            	Toast.makeText(context, "Swipe Left to See Other Sections", Toast.LENGTH_SHORT).show();
            }
        }

        super.onScrollChanged(l, t, oldl, oldt);
    }


    // Getters & Setters

    public OnBottomReachedListener getOnBottomReachedListener() {
        return mListener;
    }

    public void setOnBottomReachedListener(
            OnBottomReachedListener onBottomReachedListener) {
        mListener = onBottomReachedListener;
    }
    

    /**
     * Event listener.
     */
    public interface OnBottomReachedListener{
        public void onBottomReached();
    }
    
}