package com.example.nebir.festimap.pin.ui;

/**
 * Created by nebir on 08/06/2016.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

import com.example.nebir.festimap.R;
import com.example.nebir.festimap.pin.util.Vector;


public class PinUtil {

    private static final int pinDrawable = R.drawable.pin_white;

    public static Bitmap loadPinDrawable(Context context, @ColorInt int pinColor, float sizeAdaptionFactor) {
        return loadPinDrawable(context, pinDrawable, pinColor, sizeAdaptionFactor);
    }

    public static Bitmap loadPinDrawable(Context context, int pinDrawable, @ColorInt int pinColor,
                                         float sizeAdaptionFactor) {
        Bitmap pin = BitmapFactory.decodeResource(context.getResources(), pinDrawable);

        pin = Bitmap.createScaledBitmap(pin, Math.round(pin.getWidth() * sizeAdaptionFactor),
                Math.round(pin.getHeight() * sizeAdaptionFactor), false);

        // re-coloring the black icon to make it better visible on the plan
        pin = pin.copy(Bitmap.Config.ARGB_8888, true); // the original bitmap is immutable so we have to copy it
        Paint paint = new Paint();
        return createPinBitmap(pin, paint, pinColor);
    }

    @NonNull
    public static Bitmap createPinBitmap(Bitmap pin, Paint paint, @ColorInt int pinColor) {
        ColorFilter filter = new PorterDuffColorFilter(pinColor, PorterDuff.Mode.SRC_IN);
        paint.setColorFilter(filter);

        Canvas canvas = new Canvas(pin);
        canvas.drawBitmap(pin, 0, 0, paint);

        return pin;
    }

}
