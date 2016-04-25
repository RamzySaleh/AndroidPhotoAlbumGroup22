package group22.photoalbum;

import android.os.Bundle;
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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thumbnail_view);

        gridView = (GridView) findViewById(R.id.gridView);

        ThumbnailAdapter adapter = new ThumbnailAdapter(this, getPhotos());

        gridView.setAdapter(adapter);

        int index = getIntent().getIntExtra("index", 0);
        currentAlbum = MainActivity.albums.get(index);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Allow user to select photo

                // Add to current album

            }
        });
    }

    private ArrayList getPhotos(){
        int index = getIntent().getIntExtra("index", 0);
        return MainActivity.albums.get(index).getPhotos();
    }


}
