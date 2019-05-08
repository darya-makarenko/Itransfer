package ai.fritz.itransfer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.net.URI;
import java.util.ArrayList;

import static android.support.v4.content.ContextCompat.startActivity;

public class RecieveShareActivity extends AppCompatActivity {

    private Intent intent;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recieve_share);

        intent = getIntent();
        imageUri = getSharedImage();

        intent = new Intent(this, FullImage.class);
        ImageListAdapter.Uris = new ArrayList<URI>();

        URI uri = null;
        try {
            uri = new URI(imageUri.toString());
        } catch(Exception ex){
            //
        }
        ImageListAdapter.Uris.add(uri);
        intent.putExtra("pic_id", 0);
        intent.putExtra("is_shared", true);
        //crossingActivity(context, intent, intent.getExtras());
        Context context = RecieveShareActivity.this;


        startActivity(intent, intent.getExtras());
    }

    private Uri getSharedImage(){
        String action = intent.getAction();
        String type = intent.getType();

        Uri uri = null;
        if ((Intent.ACTION_SEND.equals(action) && type != null) &&
                (type.startsWith("image/"))){
            uri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
            if (uri != null) {
                Toast toast = Toast.makeText(getApplicationContext(), uri.toString(), Toast.LENGTH_SHORT);
                toast.show();
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        if ((Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) &&
                (type.startsWith("image/"))){

        }
        return uri;
    }
}

