package ai.fritz.itransfer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomArrayAdapter extends RecyclerView.Adapter<CustomArrayAdapter.ImageViewHolder> {

    private static int numberItems;

    public CustomArrayAdapter(){
        numberItems = 10;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_layout, viewGroup, false);

        ImageViewHolder viewHolder = new ImageViewHolder(view);

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

        public ImageViewHolder(View itemView){
            super(itemView);

            imageView = itemView.findViewById(R.id.icon_thumbnail);
            textView = itemView.findViewById(R.id.image_name);

        }

        void bind (int listIndex){
            imageView.setImageResource(R.drawable.horses_on_seashore);
            textView.setText("aaa");
        }

    }



}