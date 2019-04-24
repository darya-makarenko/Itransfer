package ai.fritz.itransfer;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StyleFragment.OnStyleChoiceListener} interface
 * to handle interaction events.
 * Use the {@link StyleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StyleFragment extends Fragment {

    private static final String ARG_1 = "mActiveStyle";

    private int mActiveStyle;

    private OnStyleChoiceListener mListener;

    private View view;

    public StyleFragment() {

    }

    //Use this factory method to create a new instance
    public static StyleFragment newInstance(int param1) {
        StyleFragment fragment = new StyleFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mActiveStyle = getArguments().getInt(ARG_1);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_style, container, false);
        configureImageButtons();
        return view;
    }

    public void onStyleChosen(int numStyle) {
        if (mListener != null) {
            mListener.onStyleChoice(numStyle);
        }
    }

    private void configureImageButtons(){
        LinearLayout layout = view.findViewById(R.id.filter_layout);
        for (int i = 0; i < 10; i++) {
            ImageView imageView = new ImageView(view.getContext());
            imageView.setId(i);
            imageView.setImageBitmap(BitmapFactory.decodeResource(
                    getResources(), getDrawablePicFromIndex(i)));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            int height = getResources().getDisplayMetrics().heightPixels / 5;
            int width = getResources().getDisplayMetrics().widthPixels / 3;
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
            imageView.setLayoutParams(layoutParams);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActiveStyle = v.getId();
                    onStyleChosen(mActiveStyle);
                    view.setVisibility(View.INVISIBLE);
                }
            });
            layout.addView(imageView);
        }
    }

    private int getDrawablePicFromIndex(int picNumber){
        int picture = R.drawable.lichtenstein_bicentennial;
        switch (picNumber){
            case (0):
                picture = R.drawable.lichtenstein_bicentennial;
                break;
            case (1):
                picture = R.drawable.picasso_femmes;
                break;
            case (2):
                picture = R.drawable.kutter_clown;
                break;
            case (3):
                picture = R.drawable.horses_on_seashore;
                break;
            case (4):
                picture = R.drawable.afremov_caleydoscope;
                break;
            case (5):
                picture = R.drawable.rhombus;
                break;
            case (6):
                picture = R.drawable.monet_poppy_field;
                break;
            case (7):
                picture = R.drawable.severini_ritmo_plastico;
                break;
            case (8):
                picture = R.drawable.van_gogh_starry_night;
                break;
            case (9):
                picture = R.drawable.munch_the_scream;
                break;
            case (10):
                picture = R.drawable.nolan_the_trial;
                break;
        }
        return picture;
    }

    public void setVisible(){
        view.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStyleChoiceListener) {
            mListener = (OnStyleChoiceListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnStyleChoiceListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnStyleChoiceListener {
        void onStyleChoice(int numStyle);
    }
}
