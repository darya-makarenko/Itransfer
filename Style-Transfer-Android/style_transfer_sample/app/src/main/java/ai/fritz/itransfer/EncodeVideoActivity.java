package ai.fritz.itransfer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import org.jcodec.api.FrameGrab;
import org.jcodec.common.AndroidUtil;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Picture;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import wseemann.media.FFmpegMediaMetadataRetriever;

public class EncodeVideoActivity extends AppCompatActivity {

    private Intent intent;
    private int index;
    private static List<URI> video_uris;
    private ImageView view;
    private int cycle;
    private int frame_index;
    private List<Bitmap> BitmapArray;
    private int index_image_preview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encode_video);


        intent = getIntent();
        // if video shared then index = -1
        index = intent.getIntExtra("video_index", -1);

        if (index != -1){

        } else {

        }



        //File file = new File(video_uris.get(index));

        File dir_movies = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        video_uris = getVideoUri(dir_movies);
        Bitmap bitmap = null;
        //final MediaMetadataRetriever media = new MediaMetadataRetriever();
        //media.setDataSource(video_uris.get(0).getPath());


        view = findViewById(R.id.video_preview_image);
        cycle = 0; frame_index = 0;
        MediaMetadataRetriever media = new MediaMetadataRetriever();

        ///FFmpegMediaMetadataRetriever media = new FFmpegMediaMetadataRetriever();
        media.setDataSource(video_uris.get(0).getPath());
        BitmapArray = new ArrayList<Bitmap>();

        /*for (int i = 0; i < 1000000; i+=41666){
            bitmap = media.getFrameAtTime(i);
            BitmapArray.add(bitmap);
        }*/


        MediaPlayer mp = MediaPlayer.create(getBaseContext(), Uri.parse(video_uris.get(0).toString()));

        int millis = mp.getDuration();

        for(int i=1000000;i<2000000;i+=200000)
        {
            bitmap = media.getFrameAtTime(i,MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
            BitmapArray.add(bitmap);
        }

        index_image_preview = 0;
        new CountDownTimer(1000, 40) {

            public void onTick(long millisUntilFinished){
                view.setImageBitmap(BitmapArray.get(index_image_preview++));
            }
            public void onFinish() {

            }
        }.start();


        /*new CountDownTimer(3000, 300) {
            Bitmap bitmap;

            public void onTick(long millisUntilFinished) {
                bitmap = media.getFrameAtTime(100000*(frame_index++));
                BitmapArray.add(bitmap);
                view.setImageBitmap(bitmap);
                //cycle++;
                //if (cycle == 3) {
                //    cycle = 0;
                //    view.setImageBitmap(bitmap);
               // }




            }
            public void onFinish() {
                Bitmap bit = BitmapArray.get(0);
                Toast toast = Toast.makeText(getApplicationContext(), "Image saved!", Toast.LENGTH_SHORT);
                toast.show();
            }
        }.start();


        //view.setImageBitmap(bitmap);
        //VideoReadThread thread = new VideoReadThread(video_uris.get(0), view);
        //thread.start();

/*
        BitmapArray = new ArrayList<Bitmap>();
        int N = 50;
        video_uris = getVideoUri(dir_movies);
        File file = new File(video_uris.get(0));
        try {
            Thread thread = new Thread();

        } catch(Exception ex){
            //
        }
        Bitmap temp = BitmapArray.get(0);
        ImageView view = findViewById(R.id.video_preview_image);
        view.setImageBitmap(temp);
        */
    }

/*
    int frame_index = 0;
    FrameGrab grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(file));
    Picture picture;
            while ((frame_index < N) && (null != (picture = grab.getNativeFrame()))) {
        BitmapArray.add(AndroidUtil.toBitmap(picture));
        //System.out.println(picture.getWidth() + "x" + picture.getHeight() + " " + picture.getColor());
        frame_index++;
    }*/


    public void setVideUris(List<URI> uris){
        video_uris = uris;
    }

    List<URI> getVideoUri(File dir){
        List<URI> uris = new ArrayList<URI>();
        String[] okFileExtensions =  new String[] {"mp4"};
        for (File file: dir.listFiles()){
            for (String extension : okFileExtensions) {
                if (file.getName().toLowerCase().endsWith(extension)) {
                    uris.add(file.toURI());
                }

                if (file.isDirectory()){
                    uris.addAll(getVideoUri(file));
                    break;
                }
            }
        }
        return uris;
    }
}
