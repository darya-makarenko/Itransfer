package ai.fritz.itransfer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Matrix;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class HelpActivity extends AppCompatActivity {

    //private RecyclerView ScrollingView;
    //private Fragment fragment;
    private ImageView imageView;
    private ImageView ImageArrowRight, ImageArrowLeft;
    private int help_images_rus[] = {R.drawable.menu_tutorial_rus, R.drawable.camera_tutorial_rus, R.drawable.camera_save_tutorial_rus, R.drawable.gallery_tutorial_rus, R.drawable.sharing_tutorial_rus, R.drawable.full_image_tutorial_rus};
    private int help_images_eng[] = {R.drawable.menu_tutorial_eng, R.drawable.camera_tutorial_eng, R.drawable.camera_save_tutorial_eng, R.drawable.gallery_tutorial_eng, R.drawable.sharing_tutorial_eng, R.drawable.full_image_tutorial_eng};
    private int index;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onResume() {
        super.onResume();
        index = 0;


        imageView = findViewById(R.id.help_image);
        if (MenuActivity.lang == 0){
            imageView.setImageDrawable(getDrawable(help_images_eng[index]));
        } else {
            imageView.setImageDrawable(getDrawable(help_images_rus[index]));
        }

        imageView.setMaxWidth(300);
        imageView.setMaxHeight(300);


        ImageArrowRight = findViewById(R.id.help_next);
        ImageArrowLeft = findViewById(R.id.help_prev);
        ImageArrowLeft.setRotation(270);
        ImageArrowRight.setRotation(90);


        ImageArrowRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextHelpScreen();
            }
        });

        ImageArrowLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prevHelpScreen();
            }
        });
        /*Matrix matrix = new Matrix();
        ImageArrowLeft.setScaleType(ImageView.ScaleType.MATRIX);   //required
        matrix.postRotate((float) 90, 0, 0);
        imageView.setImageMatrix(matrix);

        matrix = new Matrix();
        ImageArrowRight.setScaleType(ImageView.ScaleType.MATRIX);   //required
        matrix.postRotate((float) 90, 0, 0);
        imageView.setImageMatrix(matrix);
*/
        //LinearLayout view = findViewById(R.id.help_layout);

        /*
        view.setOnTouchListener(new View.OnTouchListener() {
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

                            if (GestureDriver.isSwipeRight(deltaX)){
                                swipeRight();
                            }
                            if (GestureDriver.isSwipeLeft(deltaX)){
                                swipeLeft();
                            }
                        }

                        if (GestureDriver.isVerticalSwipe(deltaX, deltaY)){
                            if (GestureDriver.isSwipeUp(deltaY)) {

                            }
                            if (GestureDriver.isSwipeDown(deltaY)) {

                            }
                        }
                        break;
                }
                return false;
            }
        });
        */
    }


    private void nextHelpScreen(){
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.scale_pver_next_button);
        ImageArrowRight.startAnimation(anim);

        new CountDownTimer(150, 150) {

            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {
                int[] help_images;
                if (MenuActivity.lang == 0){
                    help_images = help_images_eng;
                }
                else{
                    help_images = help_images_rus;
                }
                if (index < help_images.length-1) {
                    imageView.setImageDrawable(getDrawable(help_images[++index]));
                }
            }
        }.start();
    }

    private void prevHelpScreen(){
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.scale_pver_next_button);
        ImageArrowLeft.startAnimation(anim);

        new CountDownTimer(300, 300) {

            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {
                int[] help_images;
                if (MenuActivity.lang == 0){
                    help_images = help_images_eng;
                }
                else{
                    help_images = help_images_rus;
                }
                if (index > 0) {
                    imageView.setImageDrawable(getDrawable(help_images[--index]));
                }
            }
        }.start();
    }

}
