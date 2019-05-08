package ai.fritz.itransfer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.DisplayMetrics;
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


public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ImageViewHolder> {

    private static int numberItems;
    public static List<URI> Uris;
    private int numHolders;
    public static int imageWidth;



    public ImageListAdapter(List<URI> uris) {
        numberItems = uris.size();
        Uris = uris;
        numHolders = 0;

    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {

        final Context context = viewGroup.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.row_layout, viewGroup, false);

        ImageViewHolder viewHolder = new ImageViewHolder(view);







        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                RecyclerView recycler = viewGroup.findViewById(R.id.rv_images);



                int pic_id = recycler.getChildLayoutPosition(v);
                Uri uri = Uri.parse(ImageListAdapter.getUri(pic_id).toString());
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                intent.setType("image/*");
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION |
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                startActivity(context, Intent.createChooser(intent, "Share image using"),
                        intent.getExtras());
                return true;
            }
        });



        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                RecyclerView recycler = viewGroup.findViewById(R.id.rv_images);
                int pic_id = recycler.getChildLayoutPosition(v);
                Intent intent = new Intent(context, FullImage.class);
                intent.putExtra("pic_id", pic_id);
                intent.putExtra("is_shared", false);
                //crossingActivity(context, intent, intent.getExtras());
                startActivity(context, intent, intent.getExtras());
                //Intent intent = new Intent(Intent.ACTION_VIEW,
                //        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //intent.setAction(Intent.ACTION_VIEW);
                //intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION |
                 //       Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                //Uri uri = Uri.parse(ImageListAdapter.getUri(pic_id).getPath());
                //intent.setDataAndType(uri,
                //        "image/*");
                ///try{
                 //   crossingActivity(context, intent, intent.getExtras());
                //} catch(Exception ex){
                    //
                //}

                //Toast toast = Toast.makeText(context,
                //       String.valueOf(recycler.getChildLayoutPosition(v)), Toast.LENGTH_SHORT);
                //toast.show();

            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder imageViewHolder, int i) {
        imageViewHolder.bind(i);


//        imageViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                RecyclerView recycler = viewGroup.findViewById(R.id.rv_images);
//                int pic_id = recycler.getChildLayoutPosition(v);
//                Intent intent = new Intent(context, FullImage.class);
//                intent.putExtra("pic_id", pic_id);
//                //crossingActivity(context, intent, intent.getExtras());
//                crossingActivity(context, intent, intent.getExtras());
//            }
//        });


    }

    public static URI getUri(int index){
        try {
            return Uris.get(index);
        } catch (Exception ex){
            //
        }
        return null;
    }

    public static int getUriCount(){
        return Uris.size();
    }


    @Override
    public int getItemCount() {
        return numberItems;
    }

    class ImageViewHolder extends ViewHolder {

        ImageView imageView;


        public ImageViewHolder(View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.icon_thumbnail);



            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(imageWidth, imageWidth);

            imageView.setLayoutParams(layoutParams);

           // imageView.setMaxHeight();
            //textView = itemView.findViewById(R.id.image_name);
        }

        void bind (int listIndex) {
            //imageView.setImageResource(R.drawable.horses_on_seashore);

            Bitmap bitmap = null;
            //try{
            //    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            //} catch (Exception e){
            //
            //}



            Picasso.get()
                    .load(Uri.parse(Uris.get(listIndex).toString()))
                    .resize(100,100)
                    //.fit()
                    .into(imageView);

//            try {
////                TODO замените здесь загрузку картинки на библиотеку PICASSO
//
//
//
//                bitmap = createScaledBitmap(Uri.parse(
//                        Uris.get(listIndex).toString()
//                ).getPath(), 100, 100);
//            }catch (Exception e){
//                //
//            }
//            imageView.setImageBitmap(bitmap);


            //imageView.setImageURI(Uri.parse(Uris.get(listIndex).toString()));
            //String[] text = Uris.get(listIndex).getPath().split("/");
            //textView.setText(text[text.length-1]);
            String text = Uris.get(listIndex).getPath();

        }
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


}