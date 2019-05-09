package ai.fritz.itransfer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static android.support.v4.content.ContextCompat.getColorStateList;
import static android.support.v4.content.ContextCompat.getDrawable;
import static android.support.v4.content.ContextCompat.startActivity;


public class StyleAdapter extends RecyclerView.Adapter<StyleAdapter.ImageViewHolder> {

    private int numberItems;
    public static List<CustomDrawable> styles;
    private int numHolders;
    private RecyclerView  recycler;
    private List<View> recycler_view;
    private TextView view_choosen;
    private ImageView image_preview;


    public StyleAdapter(List<CustomDrawable> styles_, ImageView image_preview_) {
        numberItems = styles_.size();
        styles = styles_;
        numHolders = 0;
        recycler_view = new ArrayList<View>();
        image_preview = image_preview_;


    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {

        final Context context = viewGroup.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.style_cell, viewGroup, false);
        recycler_view.add(view);

        ImageViewHolder viewHolder = new ImageViewHolder(view);

        if (i == 0){
            view_choosen = view.findViewById(R.id.text_style);
        }
        recycler = viewGroup.findViewById(R.id.choose_default_style_rv);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           /*     int len = recycler_view.size();
                for (int i=0; i < len; i++){
                    //recycler_view.get(i).setBackgroundResource(R.color.menuBackground);
                    recycler_view.get(i).findViewById(R.id.text_style).
                            setBackgroundResource(R.color.menuBackground);
                }
                //v.setBackgroundResource(R.drawable.main_activity_button);


*/              MenuActivity.style_num = recycler.getChildLayoutPosition(v);
                image_preview.setImageResource(styles.get(MenuActivity.style_num).id);

                //view.setImageDrawable(recycler_view.get());

                //recycler.getLayoutParams().height = 0;
                //recycler.requestLayout();

                //MenuActivity.isVisibleStyleChoose = false;
               //recycler.setVisibility(View.INVISIBLE);
            }
        });
       /* view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                recycler = viewGroup.findViewById(R.id.choose_default_style_rv);
                int pic_id = recycler.getChildLayoutPosition(v);

            }
        });*/

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder imageViewHolder, int i) {
        imageViewHolder.bind(i);


    }


    @Override
    public int getItemCount() {
        return numberItems;
    }

    class ImageViewHolder extends ViewHolder {

        ImageView imageView;
        TextView textView;


        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_style);
            textView = itemView.findViewById(R.id.text_style);


            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(180, 180);

            layoutParams.leftMargin = 10;
            layoutParams.topMargin = 10;
            //layoutParams.topMargin = 10;
            imageView.setLayoutParams(layoutParams);

            // imageView.setMaxHeight();
            //textView = itemView.findViewById(R.id.image_name);
        }

        void bind(int listIndex) {



            /*
            int len = recycler_view.size();
            for (int i=0; i < len; i++){
                //recycler_view.get(i).setBackgroundResource(R.color.menuBackground);
                recycler_view.get(i).findViewById(R.id.text_style).
                        setBackgroundResource(R.color.menuBackground);
            }

            TextView view = recycler_view.get(listIndex % recycler_view.size()).
                                                findViewById(R.id.text_style);

            view.setBackgroundResource(R.drawable.main_activity_button);
            //v.setBackgroundResource(R.drawable.main_activity_button);
            //view_choosen.
            //        setBackgroundResource(R.drawable.main_activity_button);

*/



            Picasso.get().load(styles.get(listIndex).id)
                    .resize(300,300)
                    //.fit()
                    .into(imageView);

            textView.setText(styles.get(listIndex).name);


        }
    }

}