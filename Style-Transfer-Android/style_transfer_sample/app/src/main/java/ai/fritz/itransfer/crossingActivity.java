package ai.fritz.itransfer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class crossingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crossing);
       // ImageView view = (ImageView) findViewById(R.id.start_image);



        //view.setImageBitmap(BitmapFactory.decodeResource(
        //        getResources(), R.drawable.before));

        //view.setImageBitmap(BitmapFactory.decodeResource(
        //        getResources(), R.drawable.));

        //Intent intent = new Intent(crossingActivity.this, CameraActivity.class);
        //crossingActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(crossingActivity.this, CameraActivity.class);
        startActivity(intent);
        /*
        new CountDownTimer(600, 100) {

            @Override
            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished < 300) {
                    ImageView view = (ImageView) findViewById(R.id.start_image);
                    view.setImageBitmap(BitmapFactory.decodeResource(
                            getResources(), R.drawable.after));
                }
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(crossingActivity.this, CameraActivity.class);
                crossingActivity(intent);
            }

        }.start();
        */

    }
}
