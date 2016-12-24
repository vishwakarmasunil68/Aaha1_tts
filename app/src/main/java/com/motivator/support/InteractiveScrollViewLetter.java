package com.motivator.support;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

import com.motivator.wecareyou.Letter;
import com.motivator.wecareyou.NewRitual;

public class InteractiveScrollViewLetter extends ScrollView {
    OnBottomReachedListener mListener;
    Context context;
    int i=2;
    public InteractiveScrollViewLetter(Context context, AttributeSet attrs,
            int defStyle) {
        super(context, attrs, defStyle);
        this.context=context;
    }

    public InteractiveScrollViewLetter(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
    }

    public InteractiveScrollViewLetter(Context context) {
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
//            	Toast.makeText(context, "Swipe Left to See Other Sections in letter", Toast.LENGTH_SHORT).show();
                try {
                    Letter letter = (Letter) context;
                    letter.makeFloatColor();
                }
                catch (Exception e){
                    try{
                        NewRitual obj= (NewRitual) context;
                        obj.makeFloatColor();
                    }
                    catch (Exception e1){

                    }
                }
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