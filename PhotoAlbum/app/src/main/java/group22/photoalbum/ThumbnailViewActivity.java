package group22.photoalbum;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.GridView;
import android.view.Menu;
import android.widget.Adapter;
import android.view.MenuItem;
import java.util.ArrayList;
import android.graphics.Bitmap;
import android.net.Uri;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import java.io.InputStream;
import android.net.Uri;
import android.widget.ImageView;
import android.graphics.drawable.BitmapDrawable;
import java.io.File;

/**
 *
 * Group 22
 *
 * @author Sara Zayed
 * @author Ramzy Saleh
 *
 *
 */

public class ThumbnailViewActivity extends AppCompatActivity {

    private GridView gridView;
    private ThumbnailAdapter gridViewAdapter;
    private Album currentAlbum;
    private static final int SELECT_PHOTO = 1;
    private ThumbnailAdapter adapter;

    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thumbnail_view);

        gridView = (GridView) findViewById(R.id.gridView);

        adapter = new ThumbnailAdapter(this, getPhotos());

        gridView.setAdapter(adapter);

        int index = getIntent().getIntExtra("index", 0);
        currentAlbum = MainActivity.albums.get(index);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Allow user to select photo

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, SELECT_PHOTO);

                gridView.setAdapter(adapter);
                // Add to current album

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {

        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();

                    ImageView iv = new ImageView(this);

                    iv.setImageURI(selectedImage);

                    BitmapDrawable drawable = (BitmapDrawable) iv.getDrawable();
                    Bitmap selectedImageGal = drawable.getBitmap();

                    Photo photoToAdd = new Photo();
                    photoToAdd.setImage(selectedImageGal);

                    File f = new File("" + selectedImage);
                    photoToAdd.setCaption(f.getName());


                    currentAlbum.addOnePhoto(photoToAdd);
                    gridView.setAdapter(adapter);

        }
    }



    private ArrayList getPhotos(){
        int index = getIntent().getIntExtra("index", 0);
        return MainActivity.albums.get(index).getPhotos();
    }


}
