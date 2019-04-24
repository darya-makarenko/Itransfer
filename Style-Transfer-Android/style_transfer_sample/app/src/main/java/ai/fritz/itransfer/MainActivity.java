package ai.fritz.itransfer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.Image;
import android.media.ImageReader;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.util.Size;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

import ai.fritz.core.Fritz;
import ai.fritz.fritzvisionstylemodel.ArtisticStyle;
import ai.fritz.fritzvisionstylemodel.FritzVisionStylePredictor;
import ai.fritz.fritzvisionstylemodel.FritzVisionStyleTransfer;
import ai.fritz.vision.inputs.FritzVisionImage;
import ai.fritz.vision.inputs.FritzVisionOrientation;

public class MainActivity extends BaseCameraActivity implements ImageReader.OnImageAvailableListener, StyleFragment.OnStyleChoiceListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final Size DESIRED_PREVIEW_SIZE = new Size(1280, 960);

    private AtomicBoolean computing = new AtomicBoolean(false);

    private FritzVisionImage styledImage;

    private FritzVisionStylePredictor predictor;

    private Size cameraViewSize;

    private FrameLayout layout;
    private HorizontalScrollView filterLayout;
    private boolean isLongClickPerformed = false;

    private View contextView;
    private boolean isFilterSet = false;
    private  boolean isFilterShown = false;
    private static String savedImage = "";

    private float downX = 0, upX = 0;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fritz.configure(this);
        predictor = FritzVisionStyleTransfer.getPredictor(this, ArtisticStyle.HEAD_OF_CLOWN);

        layout = findViewById(R.id.camera_container);
        filterLayout = findViewById(R.id.filter_container);


        layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                saveImage();
                isLongClickPerformed = true;
                return true;
            }
        });

        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case(MotionEvent.ACTION_DOWN):

                        downX = event.getX();
                        break;
                    case(MotionEvent.ACTION_UP):

                        upX = event.getX();
                        float deltaX = downX - upX;
                        if(Math.abs(deltaX) > 20){
                            isLongClickPerformed = false;
                            if (deltaX>=0){
                                swipePerformed();
                                return false;
                            }else{
                                swipePerformed();
                                return false;
                            }
                        } else {

                            if (isFilterShown) {
                                filterLayout.setVisibility(View.INVISIBLE);
                                isFilterShown = false;
                                break;
                            }
                            if (event.getAxisValue(MotionEvent.AXIS_Y) >= cameraViewSize.getHeight() / 2 &&
                                    !isLongClickPerformed && !isFilterShown) {
                                Log.d(TAG, "show styles");
                                contextView = v;
                                if (!isFilterSet) {
                                    setFilter();
                                    isFilterShown = true;
                                } else {
                                    StyleFragment styleFrag = (StyleFragment)
                                            getFragmentManager().findFragmentById(R.id.filter_container);

                                    if (styleFrag != null) {
                                        styleFrag.setVisible();
                                        filterLayout.setVisibility(View.VISIBLE);
                                        isFilterShown = true;
                                    }
                                }
                            }
                            isLongClickPerformed = false;
                            break;
                        }
                }
                return false;
            }
        });

    }

    void swipePerformed() {
            Intent intent = new Intent(MainActivity.this, GalleryActivity.class);
            startActivity(intent);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.camera_connection_fragment_stylize;
    }


    @Override
    protected Size getDesiredPreviewFrameSize() {
        return DESIRED_PREVIEW_SIZE;
    }

    @Override
    public void onPreviewSizeChosen(final Size previewSize, final Size cameraViewSize, final int rotation) {

        this.cameraViewSize = cameraViewSize;

        // Callback draws a canvas on the OverlayView
        addCallback(
                new OverlayView.DrawCallback() {
                    @Override
                    public void drawCallback(final Canvas canvas) {
                        if (styledImage != null) {
                             styledImage.drawOnCanvas(canvas);
                        }
                    }
                });
    }

    public void saveImage(){
        if (styledImage != null){
            Log.d(TAG,"saveImage");
            runInBackground(new ImageSaver(styledImage.getBitmap()));
            if (this.savedImage != ""){
                galleryAddPic(savedImage);
                this.savedImage = "";
            }
            Toast toast = Toast.makeText(getApplicationContext(), "Image saved!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    @Override
    public void onImageAvailable(final ImageReader reader) {
        Image image = reader.acquireLatestImage();

        if (image == null) {
            return;
        }

        if (!computing.compareAndSet(false, true)) {
            image.close();
            return;
        }

        // Create the FritzVisionImage object from media.Image
        int rotationFromCamera = FritzVisionOrientation.getImageRotationFromCamera(this, cameraId);
        final FritzVisionImage fritzImage = FritzVisionImage.fromMediaImage(image, rotationFromCamera);

        image.close();


        runInBackground(
                new Runnable() {
                    @Override
                    public void run() {
                        final long startTime = SystemClock.uptimeMillis();
                        styledImage = predictor.predict(fritzImage);
                        styledImage.scale(cameraViewSize.getWidth(), cameraViewSize.getHeight());
                        Log.d(TAG, "INFERENCE TIME:" + (SystemClock.uptimeMillis() - startTime));

                        requestRender();
                        computing.set(false);
                    }
                });
    }

    @Override
    public void onStyleChoice(int numStyle) {
        predictor = FritzVisionStyleTransfer.getPredictor(contextView.getContext(), getArtisticStyle(numStyle));
        filterLayout.setVisibility(View.INVISIBLE);
        isFilterShown = false;
    }

    private ArtisticStyle getArtisticStyle(int numStyle){
        ArtisticStyle style = ArtisticStyle.BICENTENNIAL_PRINT;
        switch (numStyle){
            case (0):
                style = ArtisticStyle.BICENTENNIAL_PRINT;
                break;
            case (1):
                style = ArtisticStyle.FEMMES;
                break;
            case (2):
                style = ArtisticStyle.HEAD_OF_CLOWN;
                break;
            case (3):
                style = ArtisticStyle.HORSES_ON_SEASHORE;
                break;
            case (4):
                style = ArtisticStyle.KALEIDOSCOPE;
                break;
            case (5):
                style = ArtisticStyle.PINK_BLUE_RHOMBUS;
                break;
            case (6):
                style = ArtisticStyle.POPPY_FIELD;
                break;
            case (7):
                style = ArtisticStyle.RITMO_PLASTICO;
                break;
            case (8):
                style = ArtisticStyle.STARRY_NIGHT;
                break;
            case (9):
                style = ArtisticStyle.THE_SCREAM;
                break;
            case (10):
                style = ArtisticStyle.THE_TRAIL;
                break;
        }
        return style;
    }


    private static class ImageSaver implements Runnable {

        /**
         * The JPEG image
         */
        private final Bitmap mImage;
        /**
         * The file we save the image into.
         */

        ImageSaver(Bitmap image) {
            mImage = image;
        }

        @Override
        public void run() {
            Log.d(TAG, "run saving");
          String root = Environment.getExternalStorageDirectory().toString();
          //File myDir = new File(root + "/saved_images");
          File myDir = new File(
                  Environment.getExternalStoragePublicDirectory(
                          Environment.DIRECTORY_PICTURES
                  ),
                  "Itransferred_images"
          );
          myDir.mkdirs();

          String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
          String filename = "Styled_" + timestamp + ".jpg";

          File file = new File(myDir, filename);
          if (file.exists()) file.delete();
          try{
              FileOutputStream out = new FileOutputStream(file);
              mImage.compress(Bitmap.CompressFormat.JPEG, 100, out);
              out.flush();
              out.close();
              MainActivity.savedImage = file.getAbsolutePath();
          }
          catch (Exception e){
              e.printStackTrace();
          }
        }

    }

    private void setFilter() {
        final Fragment fragment = StyleFragment.newInstance(0);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.filter_container, fragment)
                .commit();
        isFilterSet = true;
    }

    private void galleryAddPic(String photoPath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(photoPath);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }
}
