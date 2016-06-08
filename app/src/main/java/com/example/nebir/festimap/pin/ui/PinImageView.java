package com.example.nebir.festimap.pin.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.nebir.festimap.pin.util.Vector;


public class PinImageView extends ImageView {


    private Bitmap pin;
    private boolean hasPin = false;

    private Paint paint = new Paint(Color.parseColor("#FF1100"));
    private Vector originalVector;
    private Vector calculatedVector;

    public PinImageView(Context context) {
        super(context);
        loadPinDrawable(context);
    }


    public PinImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        loadPinDrawable(context);
    }

    public PinImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        loadPinDrawable(context);
    }

    public void drawPin(Vector vector, Vector clickVector) {
        this.originalVector = vector;
        this.calculatedVector = clickVector;
        this.hasPin = true;
        this.invalidate();
    }

    public Vector getAbsolutePinVector() {
        if (originalVector != null) {
            return originalVector.clone();
        }
        return null;
    }

    public void updateState(Vector stateVector, float zoom) {
        if (originalVector == null)
            return;

        calculatedVector = originalVector.clone().multiply(zoom).add(stateVector); // Vectors.add(Vectors.multiply(originalVector, zoom), stateVector);

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (hasPin) {
            // the location pin is centered and the target point is at the bottom so the X and Y positions have to be adjusted
            // the pin also has a small padding around the whole image, but it is marginal and can be ignored.
            canvas.save();
            canvas.drawBitmap(pin, calculatedVector.x - (pin.getWidth() / 2),
                    calculatedVector.y - (pin.getHeight()), paint);
            canvas.restore();
        }
    }


    private void loadPinDrawable(Context context) {
        loadPinDrawable(context, Color.BLACK, 1);
    }

    public void loadPinDrawable(Context context, @ColorInt int whitePin, float sizeAdaptionFactor) {
        pin = PinUtil.loadPinDrawable(context, whitePin, sizeAdaptionFactor);
    }


}

