package ai.fritz.itransfer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.app.Fragment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import ai.fritz.fritzvisionstylemodel.ArtisticStyle;

import ai.fritz.fritzvisionstylemodel.FritzVisionStylePredictor;
import ai.fritz.fritzvisionstylemodel.FritzVisionStyleTransfer;
import ai.fritz.vision.inputs.FritzVisionImage;

public class FullImage extends AppCompatActivity  implements  StyleFragment.OnStyleChoiceListener{

    private float downX, downY;
    private boolean isLongClickPerformed;
    private boolean isSwipePerformed;
    private Uri imageUri;
    private int index;
    private boolean isFilterSet = false;
    private boolean isFilterShown = false;
    private HorizontalScrollView filterLayout;
    private static final String TAG = CameraActivity.class.getSimpleName();
    private View contextView;
    private FritzVisionStylePredictor predictor;

    private static String savedImage = "";
    private boolean isSpeed = false;
    private boolean isSpeedQualityVisible = false;


    private FritzVisionImage styledImage;
    private FritzVisionImage fritzImage;
    private GridLayout speed_quality_view;
    private ImageView image;
    private Intent intent;
    private boolean is_shared;

    private TextView textView_1;
    private TextView textView_2;


/*
    OrientationEventListener mOrientationEventListener = new OrientationEventListener(FullImage.this) {
        @Override
        public void onOrientationChanged(int orientation) {
            isFilterShown = false;

            filterLayout.setVisibility(View.INVISIBLE);
        }
    };
*/



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private Uri getSharedImage(){
        String action = intent.getAction();
        String type = intent.getType();

        Uri uri = null;
        if ((Intent.ACTION_SEND.equals(action) && type != null) &&
                (type.startsWith("image/"))){
            uri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
            if (uri != null) {
                //
            } else {
                //
            }
        }
        if ((Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) &&
                (type.startsWith("image/"))){

        }
        return uri;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_full_image);
        intent = getIntent();
        is_shared = false;
        // if pic_id is set then prev window was gallery
        // else this was share intent from another app

        //index = intent.getIntExtra("pic_id", 0);
        //imageUri = Uri.parse(ImageListAdapter.getUri(index).toString());
        image = findViewById(R.id.full_image);

        index = intent.getIntExtra("pic_id", -1);
        if (index == -1){
            imageUri = getSharedImage();
            String path = imageUri.getPath();
            imageUri = Uri.parse("file:/" + path);

            is_shared = true;

        } else {
            imageUri = Uri.parse(ImageListAdapter.getUri(index).toString());
        }

        setBitmap(imageUri);
        contextView = findViewById(R.id.full_image);
        predictor = null;
        //if (MenuActivity.style_num == 11) {
        //    predictor = null;
        //} else {
        //    predictor = FritzVisionStyleTransfer.getPredictor(contextView.getContext(),
         //           StyleTrasferWork.getArtisticStyle(MenuActivity.style_num));
        //}

        //predictor = FritzVisionStyleTransfer.getPredictor(this, ArtisticStyle.HEAD_OF_CLOWN);
        filterLayout = findViewById(R.id.filter_container_full_image);



        setFilter();
        showFilter();

        //showSpeedQuality();
        image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                saveImage();
                isLongClickPerformed = true;
                return true;
            }
        });



        image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case(MotionEvent.ACTION_DOWN):
                        downX = event.getX();
                        downY = event.getY();
                        break;
                    case(MotionEvent.ACTION_UP):

                        isSwipePerformed = false;
                        float deltaX = downX - event.getX();
                        float deltaY = downY - event.getY();

                        if ((!is_shared) && GestureDriver.isGorizontalSwipe(deltaX, deltaY)){
                            if (GestureDriver.isSwipeRight(deltaX)) {
                                swipeRight();
                            }
                            if (GestureDriver.isSwipeLeft(deltaX)) {
                                swipeLeft();
                            }
                            isLongClickPerformed = false;
                            isSwipePerformed = true;
                        }
                        if (GestureDriver.isVerticalSwipe(deltaX, deltaY)){
                            isLongClickPerformed = false;
                            isSwipePerformed = true;
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
                        if (!isSwipePerformed)
                        {
                          /*  if (isSpeedQualityVisible){
                                if (event.getY() > 40) {
                                    hideSpeedQuality();
                                }
                            } else {

                            }
                            */
                        }
                        break;
                }
                return false;
            }
        });

        //speed_quality_view = (GridLayout)findViewById(R.id.style_quality_speed);

        textView_1 = findViewById(R.id.style_quality_1);
        textView_2 = findViewById(R.id.style_quality_2);
        if (MenuActivity.lang == 0){
            setEnglish();
        } else {
            setRussian();
        }
        textView_1.setTextColor(getResources().getColor(R.color.quailty_choosen));

        // textView_1.setTextColor(16777215);

        textView_1.setWidth(getResources().getDisplayMetrics().widthPixels / 2);
        textView_2.setWidth(getResources().getDisplayMetrics().widthPixels / 2);

        /*View container =  speed_quality_view.getChildAt(0);
        container.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                isSpeed = false;
                showSpeedQuality();
            }
        });

        container = speed_quality_view.getChildAt(1);
        container.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                isSpeed = true;
                showSpeedQuality();
            }
        });
*/
        isSwipePerformed = false;

        textView_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSpeed = false;
                //showSpeedQuality();
                //textView_1.setTextColor((int)R.color.colorWhite);
                textView_2.setTextColor(getResources().getColor(R.color.colorWhite));
                textView_1.setTextColor(getResources().getColor(R.color.quailty_choosen));

            }
        });

        textView_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSpeed = true;
                //showSpeedQuality();
                textView_2.setTextColor(getResources().getColor(R.color.quailty_choosen));
                textView_1.setTextColor(getResources().getColor(R.color.colorWhite));
                //textView_2.setTextColor((int)R.color.colorWhite);
            }
        });


    }


    @Override
    protected void onPause() {
        super.onPause();
        if (is_shared){
            //finish();
            //System.exit(0);
        }
    }

    private void setBitmap(Uri imageUri){
        Bitmap bitmap = null;
        try {
            bitmap = createScaledBitmap(imageUri.getPath(), 400, 600);
        }catch (Exception e){
            //
        }
        image.setImageBitmap(bitmap);
    }

    private void swipeRight(){
        if (index < ImageListAdapter.getUriCount()-1){
            index++;
            imageUri = Uri.parse(ImageListAdapter.getUri(index).toString());
            setBitmap(imageUri);
        }

    }

    private void swipeLeft(){
        if (index > 0){
            index--;
            imageUri = Uri.parse(ImageListAdapter.getUri(index).toString());
            setBitmap(imageUri);
        }

    }


    private void hideSpeedQuality(){
        speed_quality_view.setVisibility(View.INVISIBLE);
        isSpeedQualityVisible = false;
    }

    private void showSpeedQuality(){
        speed_quality_view.setVisibility(View.VISIBLE);
        isSpeedQualityVisible = true;
    }


    private void hideFilter(){

        if (!isFilterShown){ return; }
        filterLayout.setVisibility(View.INVISIBLE);
        isFilterShown = false;
    }

    private void showFilter(){
            if (isFilterShown) { return; }
            StyleFragment styleFrag = (StyleFragment)
                    getFragmentManager().findFragmentById(R.id.filter_container_full_image);

            if (styleFrag != null) {
                styleFrag.setVisible();
                filterLayout.setVisibility(View.VISIBLE);
                isFilterShown = true;
            }
    }

    private void setFilter() {
        final Fragment fragment = StyleFragment.newInstance(1);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.filter_container_full_image, fragment)
                .commit();
        isFilterSet = true;
        isFilterShown = true;
    }

    void saveImage(){
        if (styledImage != null){

            StringContainer StringCont = new StringContainer(savedImage);
            ImageSaver imageSaver = new ImageSaver(styledImage.getBitmap(), StringCont);
            Thread thread = new Thread(imageSaver);
            thread.start();
            savedImage = StringCont.string;

            try {
                thread.join();
                galleryAddPic(this.savedImage);
                this.savedImage = "";
                Toast toast = Toast.makeText(getApplicationContext(), "Image saved!", Toast.LENGTH_SHORT);
                toast.show();
            } catch (Exception ex){

            }
        }
    }

    private void galleryAddPic(String photoPath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(photoPath);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }



    @Override
    public void onStyleChoice(int numStyle) {

        if (numStyle == 11) {
            setBitmap(imageUri);
            filterLayout.setVisibility(View.INVISIBLE);
            isFilterShown = false;
            return;
        }
        predictor = FritzVisionStyleTransfer.getPredictor(contextView.getContext(),
                StyleTrasferWork.getArtisticStyle(numStyle));

        int Scale = 1080;
        if (isSpeed){
            Scale = 80;
        }
        Bitmap bitmap = null;
        int h = 0, w = 0;
        try {
            //bitmap = Picasso.get().load(imageUri.toString()).get();
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            if (bitmap == null){
                Toast toast = Toast.makeText(getApplicationContext(), "corrupted file!!!", Toast.LENGTH_SHORT);
                toast.show();
                filterLayout.setVisibility(View.INVISIBLE);
                isFilterShown = false;
                return;
            }
            h = bitmap.getHeight();
            w = bitmap.getWidth();
            bitmap = createScaledBitmap(imageUri.getPath(),
                    w * Scale / h, h * Scale / w);
        }catch (Exception e){
            //
        }
        fritzImage = FritzVisionImage.fromBitmap(bitmap);

        if (h < w) {
            fritzImage.rotate(90);
        }
        styledImage = predictor.predict(fritzImage);
        if (h < w) {
            styledImage.rotate(270);
        }
        filterLayout.setVisibility(View.INVISIBLE);
        ImageView image = findViewById(R.id.full_image);

        image.setImageBitmap(styledImage.getBitmap());

        isFilterShown = false;
    }










    protected Bitmap createScaledBitmap(String filePath, int desiredBitmapWith, int desiredBitmapHeight) throws IOException, FileNotFoundException {
        BufferedInputStream imageFileStream = new BufferedInputStream(new FileInputStream(filePath));
        try {
            // Phase 1: Get a reduced size image. In this part we will do a rough scale down
            int sampleSize = 1;
            if (desiredBitmapWith > 0 && desiredBitmapHeight > 0) {
                final BitmapFactory.Options decodeBoundsOptions = new BitmapFactory.Options();
                decodeBoundsOptions.inJustDecodeBounds = true;
                imageFileStream.mark(64 * 1024);
                BitmapFactory.decodeStream(imageFileStream, null, decodeBoundsOptions);
                imageFileStream.reset();
                final int originalWidth = decodeBoundsOptions.outWidth;
                final int originalHeight = decodeBoundsOptions.outHeight;
                // inSampleSize prefers multiples of 2, but we prefer to prioritize memory savings
                sampleSize = Math.max(1, Math.max(originalWidth / desiredBitmapWith, originalHeight / desiredBitmapHeight));
            }
            BitmapFactory.Options decodeBitmapOptions = new BitmapFactory.Options();
            decodeBitmapOptions.inSampleSize = sampleSize;
            decodeBitmapOptions.inPreferredConfig = Bitmap.Config.RGB_565; // Uses 2-bytes instead of default 4 per pixel

            // Get the roughly scaled-down image
            Bitmap bmp = BitmapFactory.decodeStream(imageFileStream, null, decodeBitmapOptions);

            // Phase 2: Get an exact-size image - no dimension will exceed the desired value
            float ratio = Math.min((float)desiredBitmapWith/ (float)bmp.getWidth(), (float)desiredBitmapHeight/ (float)bmp.getHeight());
            int w =(int) ((float)bmp.getWidth() * ratio);
            int h =(int) ((float)bmp.getHeight() * ratio);
            return Bitmap.createScaledBitmap(bmp, w,h, true);

        } catch (IOException e) {
            throw e;
        } finally {
            try {
                imageFileStream.close();
            } catch (IOException ignored) {
            }
        }
    }


    private void setEnglish(){
        textView_1.setText(R.string.full_image_high_eng);
        textView_2.setText(R.string.full_image_low_eng);
    }

    private void setRussian(){
        textView_1.setText(R.string.full_image_high_rus);
        textView_2.setText(R.string.full_image_low_rus);

    }

}
