package ai.fritz.itransfer;

import android.media.session.PlaybackState;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MenuActivity extends AppCompatActivity {

    private TextView QualityViewText;
    private TextView QualityViewLow;
    private TextView QualityViewHigh;
    private TextView QualityViewMiddle;



    private TextView ChooseLangText;
    private TextView EngText;
    private TextView RusText;
    private LinearLayout LangChoose;


    private TextView chooseStyleText;
    private RecyclerView stylesView;

    private int chooseLangHeight, videoPhotoHeight;
    private int qualityHeight, qualityWidth;
    private int styleHeight;

    private LinearLayout QualityViewChoose;
    private boolean isVisibleQuality;
    private boolean isLangChooseVisible;
    public static boolean isVisibleStyleChoose;

    public static int lang = 0;
    public static int style_num = 0;

    private List<CustomDrawable> styles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        styles = new ArrayList<CustomDrawable>();
        styles.add(new CustomDrawable(R.drawable.lichtenstein_bicentennial, "lichtenstein_bicentennial"));
        styles.add(new CustomDrawable(R.drawable.picasso_femmes, "picasso_femmes"));
        styles.add(new CustomDrawable(R.drawable.kutter_clown, "kutter_clown"));
        styles.add(new CustomDrawable(R.drawable.horses_on_seashore, "horses_on_seashore"));
        styles.add(new CustomDrawable(R.drawable.afremov_caleydoscope, "afremov_caleydoscope"));
        styles.add(new CustomDrawable(R.drawable.rhombus, "rhombus"));
        styles.add(new CustomDrawable(R.drawable.monet_poppy_field, "monet_poppy_field"));
        styles.add(new CustomDrawable(R.drawable.severini_ritmo_plastico, "severini_ritmo_plastico"));
        styles.add(new CustomDrawable(R.drawable.van_gogh_starry_night, "van_gogh_starry_night"));
        styles.add(new CustomDrawable(R.drawable.munch_the_scream, "munch_the_scream"));
        styles.add(new CustomDrawable(R.drawable.nolan_the_trial, "nolan_the_trial"));
        styles.add(new CustomDrawable(R.drawable.cross, "cross"));

        isVisibleQuality = false;
        isVisibleStyleChoose = false;
        isLangChooseVisible = false;

        QualityViewText = findViewById(R.id.quality_text);

        QualityViewLow = findViewById(R.id.quality_low);
        QualityViewHigh = findViewById(R.id.quality_high);
        QualityViewMiddle = findViewById(R.id.quality_middle);
        QualityViewChoose = findViewById(R.id.quality_choose);





        ChooseLangText = findViewById(R.id.lang_text);
        EngText = findViewById(R.id.eng_choose);
        RusText = findViewById(R.id.rus_choose);
        LangChoose = findViewById(R.id.choose_lang);

        stylesView = findViewById(R.id.choose_default_style_rv);
        chooseStyleText = findViewById(R.id.default_style_cell);


        ImageView image_preview = findViewById(R.id.image_style_preview);
        final StyleAdapter styleAdapter = new StyleAdapter(styles, image_preview);

        stylesView.setLayoutManager(new GridLayoutManager(this, 1));
        stylesView.setHasFixedSize(true);
        stylesView.setAdapter(styleAdapter);



        stylesView.setVisibility(View.VISIBLE);





        qualityHeight = QualityViewChoose.getLayoutParams().height;
        chooseLangHeight = LangChoose.getLayoutParams().height;
        styleHeight = stylesView.getLayoutParams().height;


        QualityViewChoose.getLayoutParams().height = 0;
        QualityViewChoose.requestLayout();
        LangChoose.getLayoutParams().height = 0;
        LangChoose.requestLayout();
        stylesView.getLayoutParams().height = 0;
        stylesView.requestLayout();


        EngText.setBackgroundResource(R.drawable.main_activity_button);
        QualityViewHigh.setBackgroundResource(R.drawable.main_activity_button);



        if (lang == 0){
            setEnglish();
        } else {
            setRussian();
        }

        chooseStyleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVisibleStyleChoose){
                    stylesView.getLayoutParams().height = 0;
                    stylesView.requestLayout();

                    isVisibleStyleChoose = false;
                    stylesView.setVisibility(View.INVISIBLE);
                } else{
                    stylesView.getLayoutParams().height = styleHeight;
                    stylesView.requestLayout();

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

        ChooseLangText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLangChooseVisible){
                    LangChoose.getLayoutParams().height = 0;
                    LangChoose.requestLayout();

                    LangChoose.setVisibility(View.INVISIBLE);
                    isLangChooseVisible = false;
                } else{
                    LangChoose.getLayoutParams().height = chooseLangHeight;
                    LangChoose.requestLayout();

                    LangChoose.setVisibility(View.VISIBLE);
                    isLangChooseVisible = true;
                }
            }
        });


        EngText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EngText.setBackgroundResource(R.drawable.main_activity_button);
                RusText.setBackgroundResource(R.color.menuBackground);
                //CameraActivity.isVideo = false;
                setEnglish();
                lang = 0;
            }
        });

        RusText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EngText.setBackgroundResource(R.color.menuBackground);
                RusText.setBackgroundResource(R.drawable.main_activity_button);
                //CameraActivity.isVideo = true;
                setRussian();
                lang = 1;
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



    void setEnglish(){
        QualityViewText.setText(R.string.quality_eng);
        QualityViewLow.setText(R.string.quality_low_eng);
        QualityViewHigh.setText(R.string.quality_high_eng);
        QualityViewMiddle.setText(R.string.quality_medium_eng);


        ChooseLangText.setText(R.string.choose_lang_text_eng);
        chooseStyleText.setText(R.string.style_choose_eng);

    }

    void setRussian(){
        QualityViewText.setText(R.string.quality_rus);
        QualityViewLow.setText(R.string.quality_low_rus);
        QualityViewHigh.setText(R.string.quality_high_rus);
        QualityViewMiddle.setText(R.string.quality_medium_rus);


        ChooseLangText.setText(R.string.choose_lang_text_rus);
        chooseStyleText.setText(R.string.style_choose_rus);
    }
}
