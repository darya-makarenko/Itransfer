package ai.fritz.itransfer;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.widget.ImageView;

import org.jcodec.api.FrameGrab;
import org.jcodec.common.AndroidUtil;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Picture;

import java.io.File;
import java.net.URI;
import java.util.List;

public class VideoReadThread  extends Thread{

    private File video_file;
    private ImageView preview;
    private List<Bitmap> BitmapArray;

    public VideoReadThread(URI uri, ImageView view){
        video_file = new File(uri);
        preview = view;
    }

    @Override
    public void run(){
        int frame_index = 0;
        int N = 2;
        try{

            /*FrameGrab grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(video_file));
            Picture picture;
            while ((frame_index < N) && (null != (picture = grab.getNativeFrame()))) {
                Bitmap bitmap = AndroidUtil.toBitmap(picture);
                BitmapArray.add(bitmap);
                preview.setImageBitmap(bitmap);
                //System.out.println(picture.getWidth() + "x" + picture.getHeight() + " " + picture.getColor());
                frame_index++;
            }*/
        } catch (Exception ex){
            //
        }
    }





}
