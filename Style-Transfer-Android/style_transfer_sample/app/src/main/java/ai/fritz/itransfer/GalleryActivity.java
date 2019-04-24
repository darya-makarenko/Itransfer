package ai.fritz.itransfer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class GalleryActivity extends AppCompatActivity {

    private RecyclerView imagesList;
    private CustomArrayAdapter imagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        imagesList = findViewById(R.id.rv_images);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        imagesList.setLayoutManager(layoutManager);
        imagesList.setHasFixedSize(true);

        imagesAdapter = new CustomArrayAdapter();
        imagesList.setAdapter(imagesAdapter);

    }
}
