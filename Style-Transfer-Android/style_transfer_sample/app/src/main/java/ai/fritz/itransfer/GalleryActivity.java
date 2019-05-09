package ai.fritz.itransfer;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {

    private float downX = 0, upX = 0;
    private float downY = 0, upY = 0;
    private List<URI> Uris;
    private static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;
    private static final String PERMISSION_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final int PERMISSIONS_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (!hasPermission()) {
            requestPermission();
            finish();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        ImageListAdapter imagesAdapter;

        File dir_dcim = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File dir_pic = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);


        Uris = getImagesUri(dir_dcim);
        try {
            Uris.addAll(getImagesUri(dir_pic));
        } catch(Exception ex){
        //
        }

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



    @Override
    public void onRequestPermissionsResult(
            final int requestCode, final String[] permissions, final int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    requestPermission();
                }
            }
        }
    }

    private boolean hasPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(PERMISSION_STORAGE) == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (shouldShowRequestPermissionRationale(PERMISSION_STORAGE)) {
                Toast.makeText(GalleryActivity.this, "Storage permission are required for this demo", Toast.LENGTH_LONG).show();
            }
            requestPermissions(new String[]{PERMISSION_STORAGE}, PERMISSIONS_REQUEST);
        }
    }
}
