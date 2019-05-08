package ai.fritz.itransfer;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageSaver implements Runnable {

    private final Bitmap mImage;
    private StringContainer savedImage;

    ImageSaver(Bitmap image, StringContainer savedImg) {
        mImage = image;
        savedImage = savedImg;
    }

    @Override
    public void run() {
        String root = Environment.getExternalStorageDirectory().toString();
        //File myDir = new File(root + "/saved_images");
        File myDir = new File(
                Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES
                ),
                "Itransferred_images"
        );
        myDir.mkdirs();

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filename = "Styled_" + timestamp + ".jpg";

        File file = new File(myDir, filename);
        if (file.exists()) file.delete();
        try{
            FileOutputStream out = new FileOutputStream(file);
            mImage.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            savedImage.string = file.getAbsolutePath();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}