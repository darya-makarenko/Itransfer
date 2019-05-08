package ai.fritz.itransfer;

import android.media.session.PlaybackState;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MenuActivity extends AppCompatActivity {

    private TextView QualityViewText;
    private TextView QualityViewLow;
    private TextView QualityViewHigh;
    private TextView QualityViewMiddle;



    private TextView VideoPhotoViewText;
    private TextView PhotoViewText;
    private TextView VideoViewText;
    private LinearLayout VideoPhotoChoose;
    private RecyclerView stylesView;

    private int videoPhotoWidth, videoPhotoHeight;
    private int qualityHeight, qualityWidth;

    private LinearLayout QualityViewChoose;
    private boolean isVisibleQuality;
    private boolean isVisibleVideoPhoto;
    private boolean isVisibleStyleChoose;

    public static int lang = 0;

    private List<CustomDrawable> styles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        styles = new ArrayList<CustomDrawable>();
        styles.add(new CustomDrawable(R.drawable.afremov_caleydoscope, "afremov"));
        styles.add(new CustomDrawable(R.drawable.afremov_caleydoscope, "afremov"));
        styles.add(new CustomDrawable(R.drawable.afremov_caleydoscope, "afremov"));
        styles.add(new CustomDrawable(R.drawable.afremov_caleydoscope, "afremov"));
        styles.add(new CustomDrawable(R.drawable.afremov_caleydoscope, "afremov"));
        styles.add(new CustomDrawable(R.drawable.afremov_caleydoscope, "afremov"));
        styles.add(new CustomDrawable(R.drawable.afremov_caleydoscope, "afremov"));
        styles.add(new CustomDrawable(R.drawable.afremov_caleydoscope, "afremov"));

        isVisibleQuality = false;
        isVisibleStyleChoose = false;
        isVisibleVideoPhoto = false;

        QualityViewText = findViewById(R.id.quality_text);

        QualityViewLow = findViewById(R.id.quality_low);
        QualityViewHigh = findViewById(R.id.quality_high);
        QualityViewMiddle = findViewById(R.id.quality_middle);
        QualityViewChoose = findViewById(R.id.quality_choose);

        VideoPhotoViewText = findViewById(R.id.video_photo_text);
        PhotoViewText = findViewById(R.id.photo_choose);
        VideoViewText = findViewById(R.id.video_choose);
        VideoPhotoChoose = findViewById(R.id.video_photo_choose);

        stylesView = findViewById(R.id.choose_default_style_rv);
        TextView chooseStyleText = findViewById(R.id.default_style_cell);



        final StyleAdapter styleAdapter = new StyleAdapter(styles);

        stylesView.setLayoutManager(new GridLayoutManager(this, 2));
        stylesView.setHasFixedSize(true);
        stylesView.setAdapter(styleAdapter);



        stylesView.setVisibility(View.VISIBLE);





        videoPhotoHeight = QualityViewChoose.getLayoutParams().height;
        qualityHeight = VideoPhotoChoose.getLayoutParams().height;
        QualityViewChoose.getLayoutParams().height = 0;
        QualityViewChoose.requestLayout();
        VideoPhotoChoose.getLayoutParams().height = 0;
        VideoPhotoChoose.requestLayout();

        PhotoViewText.setBackgroundResource(R.drawable.main_activity_button);
        QualityViewHigh.setBackgroundResource(R.drawable.main_activity_button);


        chooseStyleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVisibleStyleChoose){
                    isVisibleStyleChoose = false;
                    stylesView.setVisibility(View.INVISIBLE);
                } else{
                    isVisibleStyleChoose = true;
                    stylesView.setVisibility(View.VISIBLE);

                }
            }
        });

        QualityViewText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isVisibleQuality){
                    QualityViewChoose.getLayoutParams().height = 0;
                    QualityViewChoose.requestLayout();

                    QualityViewChoose.setVisibility(View.INVISIBLE);
                    isVisibleQuality = false;
                } else{
                    QualityViewChoose.getLayoutParams().height = qualityHeight;
                    QualityViewChoose.requestLayout();

                    QualityViewChoose.setVisibility(View.VISIBLE);
                    isVisibleQuality = true;
                }
            }
        });

        VideoPhotoViewText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isVisibleVideoPhoto){
                    VideoPhotoChoose.getLayoutParams().height = 0;
                    VideoPhotoChoose.requestLayout();

                    VideoPhotoChoose.setVisibility(View.INVISIBLE);
                    isVisibleVideoPhoto = false;
                } else{
                    VideoPhotoChoose.getLayoutParams().height = videoPhotoHeight;
                    VideoPhotoChoose.requestLayout();

                    VideoPhotoChoose.setVisibility(View.VISIBLE);
                    isVisibleVideoPhoto = true;
                }
            }
        });


        PhotoViewText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoViewText.setBackgroundResource(R.drawable.main_activity_button);
                VideoViewText.setBackgroundResource(R.color.menuBackground);
                CameraActivity.isVideo = false;
            }
        });

        VideoViewText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoViewText.setBackgroundResource(R.color.menuBackground);
                VideoViewText.setBackgroundResource(R.drawable.main_activity_button);
                CameraActivity.isVideo = true;
            }
        });


        QualityViewLow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QualityViewLow.setBackgroundResource(R.drawable.main_activity_button);
                //QualityViewLow.setBackgroundResource(R.color.quailty_choosen);
                QualityViewMiddle.setBackgroundResource(R.color.menuBackground);
                QualityViewHigh.setBackgroundResource(R.color.menuBackground);
                CameraActivity.videoPhotoQualityScale = 0.3;
            }
        });

        QualityViewMiddle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QualityViewLow.setBackgroundResource(R.color.menuBackground);
                //QualityViewMiddle.setBackgroundResource(R.color.quailty_choosen);
                QualityViewMiddle.setBackgroundResource(R.drawable.main_activity_button);
                QualityViewHigh.setBackgroundResource(R.color.menuBackground);
                CameraActivity.videoPhotoQualityScale = 0.5;
            }
        });

        QualityViewHigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QualityViewLow.setBackgroundResource(R.color.menuBackground);
                QualityViewMiddle.setBackgroundResource(R.color.menuBackground);
                //QualityViewHigh.setBackgroundResource(R.color.quailty_choosen);
                QualityViewHigh.setBackgroundResource(R.drawable.main_activity_button);
                CameraActivity.videoPhotoQualityScale = 1;
            }
        });

    }
}
