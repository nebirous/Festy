package com.example.nebir.festimap.pin.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alexvasilkov.gestures.GestureController;
import com.alexvasilkov.gestures.State;
import com.alexvasilkov.gestures.internal.MovementBounds;
import com.alexvasilkov.gestures.views.GestureImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.nebir.festimap.R;
import com.example.nebir.festimap.pin.util.Vector;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.pavelsikun.vintagechroma.ChromaDialog;
import com.pavelsikun.vintagechroma.IndicatorMode;
import com.pavelsikun.vintagechroma.OnColorSelectedListener;
import com.pavelsikun.vintagechroma.colormode.ColorMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vudroid.core.DecodeServiceBase;
import org.vudroid.pdfdroid.codec.PdfContext;
import org.vudroid.pdfdroid.codec.PdfPage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.ButterKnife;

public class PlanMarkerActivity extends AppCompatActivity implements OnColorSelectedListener {

    private static final Logger LOG = LoggerFactory.getLogger(PlanMarkerActivity.class);

    private static final int MENU_SHOW_VIEWFINDER = 1;
    private static final int MENU_COLOR_PICKER = 3;
    private static final String TAG = PlanMarkerActivity.class.getName();


    private GestureImageView gestureImageView;
    private FinderView finderView;
    private PinImageView pinView;
    private ProgressBar progressBar;
    private TextView progressText;
    public Bitmap croppedImage;

    private float maxZoom, pinSizeAdaption;
    float width;
    float height;
    private Vector currentPinVector;
    private ArrayList<File> fileList = new ArrayList<>();
    private File pictureFile;
    private File f;
    private boolean viewFinderActive = false;
    private int filePath;

    @ColorInt
    private int pinColor = -1;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pin_activity_plan_marker);

        try {
            init();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void init() throws InterruptedException, IOException {
        setupViews();
        readOutIntent();
        loadImageIntoView();
        setupGestureImageView();
    }

    private void readOutIntent() {
        pinSizeAdaption = 1;
        maxZoom = 3;
        filePath = getIntent().getIntExtra("IMAGE_PATH", R.drawable.error);
    }

    private void setupViews() {
        pinView = (PinImageView) findViewById(R.id.imageView);
        finderView = (FinderView) findViewById(R.id.cropping_finder);
        if (finderView != null) {
            finderView.setPinView(pinView);
        }
        gestureImageView = (GestureImageView) findViewById(R.id.gestureImageView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressText = (TextView) findViewById(R.id.progressText);
    }

    private void setupGestureImageView() {
        if (maxZoom > 0) {
            gestureImageView.getController().getSettings().setMaxZoom(maxZoom);
        }

        gestureImageView.getController().setOnGesturesListener(new PinGestureListener());
        gestureImageView.getController().addOnStateChangeListener(new PinStateListener());
        finderView.setSettings(gestureImageView.getController().getSettings());
    }

    private void loadImageIntoView() throws InterruptedException, IOException {

        progressBar.setVisibility(View.VISIBLE);

        File f = new File(this.getCacheDir(), "imagenCamp");
        f.createNewFile();

        Bitmap icon = BitmapFactory.decodeResource(this.getResources(), filePath);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        icon.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();

        FileOutputStream fos = new FileOutputStream(f);
        fos.write(bitmapdata);
        fos.flush();
        fos.close();
        tryLoadImage(f, 1);

    }

    @Override
    public void onColorSelected(@ColorInt int color) {
        pinColor = color;
    }

    private void tryLoadImage(File f, final float multiplier) {
        try {
            this.f = f;
            Glide.with(this).load(f).sizeMultiplier(multiplier).diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true).listener(new RequestListener<File, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, File model, Target<GlideDrawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, File model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    progressBar.setVisibility(View.GONE);
                    gestureImageView.getController().updateState();
                    width = width / multiplier;
                    height = height / multiplier;
                    return false;
                }
            }).into(gestureImageView);
        } catch (OutOfMemoryError error) {
            tryLoadImage(f, multiplier / 2.0f);

        }

    }


    @SuppressLint("AlwaysShowAction")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(Menu.NONE, MENU_SHOW_VIEWFINDER, 1, R.string.pin_show_finder_menu)
                .setIcon(R.drawable.pin_ic_crop_3_2_white_24dp)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

//        menu.add(Menu.NONE, MENU_COLOR_PICKER, 3, R.string.pin_colorpicker_menu)
//                .setIcon(R.drawable.pin_ic_colorize_white_24dp)
//                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == MENU_SHOW_VIEWFINDER) {
            toggleShowViewFinder();
        }  /*else if (item.getItemId() == MENU_COLOR_PICKER) {
            new ChromaDialog.Builder()
                    .initialColor(Color.GREEN)
                    .colorMode(ColorMode.RGB)
                    .indicatorMode(IndicatorMode.DECIMAL)
                    .onColorSelected(this)
                    .create()
                    .show(getSupportFragmentManager(), "ChromaDialog");
        }*/

        return super.onOptionsItemSelected(item);
    }

    private void storeCroppedImage() {
        Bitmap croppedImage = getCroppedBitmap();


        System.out.println("entrar entra");

        pictureFile = getOutputMediaFile();

        if (croppedImage == null || pictureFile == null) {
            LOG.debug("SaveFile",
                    "Error creating media file, check storage permissions: ");
            return;
        }
        FileOutputStream fos;
        try {

            fos = new FileOutputStream(pictureFile);
            croppedImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();

            fileList.add(pictureFile);
            //Toast.makeText(PlanMarkerActivity.this, "Imagen guardada correctamente", Toast.LENGTH_SHORT).show();


            LOG.debug(TAG, "storeCroppedImage: stored to " + pictureFile.getAbsolutePath());
        } catch (IOException e) {
            LOG.debug("SaveFile", "Error accessing file: " + e.getMessage(), e);
        }

    }

    public void shareSocialMedia(View v){

        storeCroppedImage();

        Uri uri = Uri.parse(pictureFile.getAbsolutePath());

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/*");
        share.putExtra(Intent.EXTRA_STREAM, uri);
        share.putExtra(Intent.EXTRA_TEXT,R.string.pin_acampado);
        this.startActivity(Intent.createChooser(share, "Comparte tu ubicación"));
    }

    private Bitmap getCroppedBitmap() {

        croppedImage = gestureImageView.crop();
        if (croppedImage != null) {
            paintPinOnBitmap(croppedImage);
            return getCroppedBitmapInViewFinderResolution(croppedImage);
        } else {
            return null;
        }
    }

    private Bitmap getCroppedBitmapInViewFinderResolution(Bitmap croppedImage) {
        return Bitmap.createScaledBitmap(croppedImage,
                gestureImageView.getController().getSettings().getMovementAreaW(),
                gestureImageView.getController().getSettings().getMovementAreaH(), false);
    }

    private void paintPinOnBitmap(Bitmap croppedImage) {

        Vector vPin = pinView.getAbsolutePinVector();

        if (vPin != null) {


            State state = gestureImageView.getController().getState();

            Vector pinInCropByZoom = getPinVectorInCroppedImage(vPin, state);

            Canvas canvas = new Canvas(croppedImage);

            Bitmap pin = PinUtil.loadPinDrawable(this, Color.BLACK, pinSizeAdaption / state.getZoom());

            float x = pinInCropByZoom.x - (pin.getWidth() / 2);
            float y = pinInCropByZoom.y - pin.getHeight();

            canvas.save();
            Paint paint = new Paint(Color.parseColor("#FF1100"));
            canvas.drawBitmap(pin, x, y, paint);
            canvas.restore();
        }
    }

    private Vector getPinVectorInCroppedImage(Vector vPin, State state) {
        Rect pos = MovementBounds.getMovementAreaWithGravity(gestureImageView.getController().getSettings());
        Vector vFrame = new Vector(pos.left, pos.top);

        Vector vState = new Vector(state.getX(), state.getY());

        Vector vPinZoom = vPin.multiply(state.getZoom());
        Vector vPinZoomState = vPinZoom.add(vState);
        Vector vPinZoomStateFrameSubstracted = vPinZoomState.subtract(vFrame);

        return vPinZoomStateFrameSubstracted.divide(state.getZoom());
    }


    private void toggleShowViewFinder() {
        if (finderView.getVisibility() == View.VISIBLE) {
            hideViewFinder();
        } else {
            showViewFinder();
        }
    }

    private void showViewFinder() {
        finderView.setVisibility(View.VISIBLE);

        setViewFinderArea();
        finderView.setSettings(gestureImageView.getController().getSettings());
        viewFinderActive = true;
    }

    private void hideViewFinder() {
        finderView.setVisibility(View.GONE);
        viewFinderActive = false;
    }

    private void setViewFinderArea() {
        int width = gestureImageView.getWidth();
        int height = gestureImageView.getHeight();

        if (width < height) {
            float widthFloat = (float) width / 4 * 0.8f;
            width = Math.round(widthFloat);

            height = width * 3;
            width *= 4;
        } else {
            float heightFloat = (float) height / 3 * 0.8f;
            height = Math.round(heightFloat);

            width = height * 4;
            height *= 3;
        }
        gestureImageView.getController().getSettings()
                .setMovementArea(width, height);
    }

    public void closeActivity(View v) {
        setResult(RESULT_CANCELED);
        finish();
    }

    private File getOutputMediaFile() {
        File cacheDir = getExternalCacheDir();

        // Create a media file name
        File mediaFile;
        String timestamp = new SimpleDateFormat("ddMMyyyy_HHmmssSS", Locale.US).format(new Date());
        String mImageName = "MI_" + timestamp + ".png";
        //noinspection ConstantConditions
        mediaFile = new File(cacheDir.getPath() + File.separator + mImageName);

        return mediaFile;
    }

    @Override
    public void onBackPressed() {
        if (viewFinderActive) {
            toggleShowViewFinder();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.pin_exit_dialog_title)
                    .setMessage(R.string.pin_exit_dialog_message)
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            closeActivity(null);
                        }
                    }).create().show();
        }
    }

    private class PinGestureListener extends BaseGestureListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {

            setPin(e.getX(), e.getY(), false);

            return false;
        }
    }

    private void setPin(float x, float y, boolean inital) {
        Vector p = new Vector(x, y);
        LOG.debug("clickPos: {}", p);

        State state = gestureImageView.getController().getState();
        LOG.debug("state: {}", state);


        currentPinVector = p.clone();
        if (!inital) {
            Vector s = new Vector(state.getX(), state.getY());
            currentPinVector.subtract(s);
        }
        currentPinVector.divide(state.getZoom()); // absolute vector based on zoom=1.0 and image position 0,0

        pinView.drawPin(currentPinVector, p);
        finderView.invalidate();

    }

    private class PinStateListener implements GestureController.OnStateChangeListener {

        @Override
        public void onStateChanged(State state) {
            pinView.updateState(new Vector(state.getX(),state.getY()), state.getZoom());
            finderView.invalidate();
        }

        @Override
        public void onStateReset(State oldState, State newState) {
        }
    }

}