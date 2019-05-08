package ai.fritz.itransfer;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {

    private float downX = 0, upX = 0;
    private float downY = 0, upY = 0;
    private List<URI> Uris;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void onResume() {
        super.onResume();
        ImageListAdapter imagesAdapter;

        File dir_pic = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File dir_dcim = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);

        Uris = getImagesUri(dir_pic);
        Uris.addAll(getImagesUri(dir_dcim));
        imagesAdapter = new ImageListAdapter(Uris);
        RecyclerView imagesList;
        ImageListAdapter.imageWidth = getResources().getDisplayMetrics().widthPixels / 3;
        setContentView(R.layout.activity_gallery);

        imagesList = findViewById(R.id.rv_images);


        //LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        imagesList.setLayoutManager(layoutManager);

        imagesList.setLayoutManager(new GridLayoutManager(this,3));
        imagesList.setHasFixedSize(true);
        imagesList.setAdapter(imagesAdapter);
    }



    List<URI> getImagesUri(File dir){
        List<URI> uris = new ArrayList<URI>();
        String[] okFileExtensions =  new String[] {"jpg", "png", "gif","jpeg"};
        for (File file: dir.listFiles()){
            for (String extension : okFileExtensions) {
                if (file.getName().toLowerCase().endsWith(extension)) {
                    uris.add(file.toURI());
                }

                if (file.isDirectory()){
                    uris.addAll(getImagesUri(file));
                    break;
                }
            }
        }
        return uris;
    }
}
