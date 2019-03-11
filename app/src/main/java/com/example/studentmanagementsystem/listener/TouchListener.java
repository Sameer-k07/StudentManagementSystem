package com.example.studentmanagementsystem.listener;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class TouchListener implements RecyclerView.OnItemTouchListener {
    private ClickListener mClicklistener;
    private GestureDetector mGestureDetector;

    //creating interface for onClick and for OnLongClick
    public static interface ClickListener{
        /*
        *@param view - view
        *@param position - position
        */
        public void onClick(View view,int position);
        public void onLongClick(View view,int position);
    }

     /*
     *@param context - context
     *@param recycleView - recyclerView
     *@param clicklistener - to handle clicks
     */
    public TouchListener(Context context, final RecyclerView recycleView, final ClickListener clicklistener){
        this.mClicklistener=clicklistener;
        mGestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            /*
            *@param e - motion event
            *@return true on tap
            */
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
            /*
            *@param e - motion event
            */
            @Override
            public void onLongPress(MotionEvent e) {
                View child=recycleView.findChildViewUnder(e.getX(),e.getY());
                if(child!=null && clicklistener!=null){
                    clicklistener.onLongClick(child,recycleView.getChildAdapterPosition(child));
                }
            }
        });
    }
    /*
    *@param rv - recyclerView
    *@param e - motionEvent
    *@return true or false on if condition
    */
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child=rv.findChildViewUnder(e.getX(),e.getY());
        if(child!=null && mClicklistener!=null && mGestureDetector.onTouchEvent(e)){
            mClicklistener.onClick(child,rv.getChildAdapterPosition(child));
        }

        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}


