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
import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;


public class StyleAdapter extends RecyclerView.Adapter<StyleAdapter.ImageViewHolder> {

    private int numberItems;
    public static List<CustomDrawable> styles;
    private int numHolders;


    public StyleAdapter(List<CustomDrawable> styles_) {
        numberItems = styles_.size();
        styles = styles_;
        numHolders = 0;

    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {

        final Context context = viewGroup.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.style_cell, viewGroup, false);

        ImageViewHolder viewHolder = new ImageViewHolder(view);


        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                RecyclerView recycler = viewGroup.findViewById(R.id.choose_default_style_rv);
                int pic_id = recycler.getChildLayoutPosition(v);

            }
        });

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


            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(100, 100);

            imageView.setLayoutParams(layoutParams);

            // imageView.setMaxHeight();
            //textView = itemView.findViewById(R.id.image_name);
        }

        void bind(int listIndex) {
            Picasso.get().load(styles.get(listIndex).id)
                    .resize(100,100)
                    //.fit()
                    .into(imageView);
            textView.setText(styles.get(listIndex).name);

        }
    }

}