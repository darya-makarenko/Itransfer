package ai.fritz.itransfer;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.TransitionDrawable;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView CameraButtonView;
    private TextView GalleryButtonView;
    private TextView HelpButtonView;
    private ImageView ViewSettings;
    private ImageView imageView;
    private int images_index[] = {R.drawable.animation_cat,
                            R.drawable.animation_dog,
                            R.drawable.animation_mona};

    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        imageView = findViewById(R.id.image_background_main_activity);
        ViewSettings = findViewById(R.id.image_settings_main_activity);
        LinearLayout images_view = findViewById(R.id.images_main_axtivity);
        //ConstraintLayout images_view = findViewById(R.id.images_main_axtivity);

        ConstraintLayout view = findViewById(R.id.main_activity);
        view.bringChildToFront((View)ViewSettings.getParent());

        ViewSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSettingsView();
            }
        });

       // ViewSettings.bringToFront();
        //bringChildToFront(ViewSettings);
        //imageView.bringToFront();
        //\images_view.bringChildToFront(ViewSettings);
        //ViewSettings.setVisibility(View.VISIBLE);




        //imageView.setImageResource(R.drawable.background_image_gendalf);










        CameraButtonView = findViewById(R.id.camera_button);
        GalleryButtonView = findViewById(R.id.gallery_button);
        HelpButtonView = findViewById(R.id.help_button);

        CameraButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCameraView();
            }
        });

        GalleryButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGalleryView();
            }
        });

        HelpButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHelpView();
            }
        });




    }

    private int getRandomImageIndex(int min, int max){
        Random random = new Random();
        return random.nextInt(max);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Intent intent_ = new Intent(MainActivity.this, EncodeVideoActivity.class);
        //startActivity(intent_);

        int id = getRandomImageIndex(0, images_index.length);
        ///imageView.setImageBitmap(BitmapFactory.decodeResource(
          //      getResources(), images_index[id]));

        imageView.setImageDrawable(getDrawable(images_index[id]));

        final TransitionDrawable transitionDrawableransitionDrawable = (TransitionDrawable) imageView
                .getDrawable();

        timer = new CountDownTimer(1000000, 2000) {
            public void onTick(long millisUntilFinished) {
                //transitionDrawableransitionDrawable.startTransition(2000);
                transitionDrawableransitionDrawable.reverseTransition(1000);
            }

            public void onFinish() {

            }
        };
        timer.start();

    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
    }

    private void showCameraView(){
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.scale_main_activity_button);
        CameraButtonView.startAnimation(anim);
        Intent intent = new Intent(MainActivity.this, CameraActivity.class);
        startActivity(intent);

        /*new CountDownTimer(100, 100) {

            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {

            }
        }.start();
*/
    }

    private void showGalleryView(){
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.scale_main_activity_button);
        GalleryButtonView.startAnimation(anim);

        new CountDownTimer(50, 50) {

            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {
                Intent intent = new Intent(MainActivity.this, GalleryActivity.class);
                startActivity(intent);
            }
        }.start();


    }

    private void showHelpView(){
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.scale_main_activity_button);
        HelpButtonView.startAnimation(anim);

        new CountDownTimer(50, 50) {

            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {
                Intent intent = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(intent);
            }
        }.start();

    }



    private void showSettingsView(){

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.settings_animation);
        ViewSettings.startAnimation(anim);

        new CountDownTimer(200, 200) {

            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        }.start();


    }

}
