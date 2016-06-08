package com.example.nebir.festimap.pin.ui;

import android.view.MotionEvent;

import com.alexvasilkov.gestures.GestureController;

public abstract class BaseGestureListener implements GestureController.OnGestureListener {

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public void onDown(MotionEvent e) {
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public void onUpOrCancel(MotionEvent e) {
    }
}
