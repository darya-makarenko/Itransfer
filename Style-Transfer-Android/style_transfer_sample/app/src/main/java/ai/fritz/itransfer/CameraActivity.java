package ai.fritz.itransfer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.Image;
import android.media.ImageReader;
import android.net.Uri;
import android.os.SystemClock;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Size;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.Toast;

import org.jcodec.common.io.FileChannelWrapper;
import org.jcodec.common.io.NIOUtils;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

import ai.fritz.core.Fritz;
import ai.fritz.fritzvisionstylemodel.ArtisticStyle;
import ai.fritz.fritzvisionstylemodel.FritzVisionStylePredictor;
import ai.fritz.fritzvisionstylemodel.FritzVisionStyleTransfer;
import ai.fritz.vision.inputs.FritzVisionImage;
import ai.fritz.vision.inputs.FritzVisionOrientation;

public class CameraActivity extends BaseCameraActivity implements ImageReader.OnImageAvailableListener, StyleFragment.OnStyleChoiceListener {

    private static final String TAG = CameraActivity.class.getSimpleName();

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
    private int Scale;
    public static double videoPhotoQualityScale;
    public static boolean isVideo;

    private float downX = 0, downY = 0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fritz.configure(this);
        Scale = 1;
        //predictor = FritzVisionStyleTransfer.getPredictor(this, ArtisticStyle.HEAD_OF_CLOWN);
        predictor = null;
        videoPhotoQualityScale = 1;
        isVideo = false;
        layout = findViewById(R.id.camera_container);
        filterLayout = findViewById(R.id.filter_container);

        contextView = findViewById(R.id.camera_container);
        setFilter();
        showFilter();

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
                        downY = event.getY();
                        break;
                    case(MotionEvent.ACTION_UP):
                        float deltaX = downX - event.getX();
                        float deltaY = downY - event.getY();

                        if (GestureDriver.isGorizontalSwipe(deltaX, deltaY)){
                            isLongClickPerformed = false;
                            if (GestureDriver.isSwipeRight(deltaX)){
                                //swipeRightPerformed();
                            }
                            if (GestureDriver.isSwipeLeft(deltaX)){
                                //swipeLeftPerformed();
                            }
                        }

                        if (GestureDriver.isVerticalSwipe(deltaX, deltaY)){
                            isLongClickPerformed = false;
                            if (!isFilterSet) {
                                setFilter();
                            }
                            if (GestureDriver.isSwipeUp(deltaY)) {
                                contextView = v;
                                showFilter();
                            }
                            if (GestureDriver.isSwipeDown(deltaY)) {
                                hideFilter();
                            }
                        }
                        break;
                }
                return false;
            }
        });

    }





    //@Override
    //protected void onNewIntent(Intent intent) {
    //    super.onNewIntent(intent);
    //    int i = 0;
    //}

    private void swipeRightPerformed() {
            Intent intent = new Intent(this, GalleryActivity.class);
            startActivity(intent);
    }

    private void swipeLeftPerformed() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    private void setFilter() {

        Fragment fragment = StyleFragment.newInstance(0);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.filter_container, fragment)
                .commit();
        isFilterSet = true;
        isFilterShown = true;
    }

    private void hideFilter(){
        if (!isFilterShown){ return; }
        filterLayout.setVisibility(View.INVISIBLE);
        isFilterShown = false;
    }

    private void showFilter(){
        if (isFilterShown) { return; }
        StyleFragment styleFrag = (StyleFragment)
                getFragmentManager().findFragmentById(R.id.filter_container);

        if (styleFrag != null) {
            styleFrag.setVisible();
            filterLayout.setVisibility(View.VISIBLE);
            isFilterShown = true;
        }
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
            StringContainer StringCont = new StringContainer(savedImage);
            ImageSaver imageSaver = new ImageSaver(styledImage.getBitmap(), StringCont);
            runInBackground(imageSaver);
            savedImage = StringCont.string;
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

        int rotationFromCamera = FritzVisionOrientation.getImageRotationFromCamera(this, cameraId);
        final FritzVisionImage fritzImage = FritzVisionImage.fromMediaImage(image, rotationFromCamera);

        int h = fritzImage.getHeight();
        int w = fritzImage.getWidth();

        image.close();

        runInBackground(
                new Runnable() {
                    @Override
                    public void run() {
                        final long startTime = SystemClock.uptimeMillis();
                        scaleFritz(fritzImage);
                        if (predictor == null){
                            styledImage = fritzImage;
                        } else {
                            styledImage = predictor.predict(fritzImage);
                        }
                        styledImage.scale(cameraViewSize.getWidth(), cameraViewSize.getHeight());

                        requestRender();
                        computing.set(false);
                    }
                });
    }

    public void scaleFritz(FritzVisionImage image){
        image.scale((int)(image.getWidth()*videoPhotoQualityScale),
                    (int)(image.getHeight()*videoPhotoQualityScale));
    }

    @Override
    public void onStyleChoice(int numStyle) {
        if (numStyle == 11) {
            predictor = null;
        } else {
            predictor = FritzVisionStyleTransfer.getPredictor(contextView.getContext(),
                    StyleTrasferWork.getArtisticStyle(numStyle));
        }
        filterLayout.setVisibility(View.INVISIBLE);
        isFilterShown = false;
    }




    private void saveVideo(){
/*
        SequenceEncoder encoder = new SequenceEncoder(new File("video.mp4"));
        for (int i = 1; i < 100; i++) {
            BufferedImage bi = ImageIO.read(new File(String.format("img%08d.png", i)));
            encoder.encodeImage(bi);
        }
        encoder.finish();
        */
/*
        FileChannelWrapper out = null;
        File dir =
        File file = new File(dir, "test.mp4");

        try { out = NIOUtils.writableFileChannel(file.getAbsolutePath());
            AndroidSequenceEncoder encoder = new AndroidSequenceEncoder(out, Rational.R(15, 1));
            for (Bitmap bitmap : bitmaps) {
                encoder.encodeImage(bitmap);
            }
            encoder.finish();
        } finally {
            NIOUtils.closeQuietly(out);
        }
        */
    }


    private void galleryAddPic(String photoPath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(photoPath);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }
}
