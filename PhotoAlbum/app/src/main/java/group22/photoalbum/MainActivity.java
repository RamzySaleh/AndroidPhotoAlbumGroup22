package group22.photoalbum;

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.Menu;
import android.view.MenuItem;
import android.app.AlertDialog;
import android.widget.EditText;
import android.text.InputType;
import android.content.DialogInterface;
import android.content.Context;
import java.util.ArrayList;
import android.content.Intent;
import group22.photoalbum.R;

public class MainActivity extends AppCompatActivity {

    final Context context = this;
    String userResponse;
    static ArrayList<Album> albums = new ArrayList<Album>();
    ListView albumNames;
    ArrayAdapter<Album> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        albumNames = (ListView)findViewById(R.id.photo_list);
        albumNames.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        albumNames.setSelection(0);

        albumNames.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            FloatingActionButton edit = (FloatingActionButton) findViewById(R.id.edit);
            FloatingActionButton delete = (FloatingActionButton) findViewById(R.id.delete);
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int position2 = position;
                albumNames.requestFocusFromTouch();
                albumNames.setSelection(position);
                edit.setVisibility(View.VISIBLE);
                delete.setVisibility(View.VISIBLE);
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override

                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Edit album name "+arrayAdapter.getItem(position2).getName());

                        final EditText input = new EditText(context);
                        input.setInputType(InputType.TYPE_CLASS_TEXT);
                        builder.setView(input);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if(findAlbumName(input.getText().toString().toLowerCase()) != -1){
                                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                                    alert.setTitle("Duplicate");
                                    alert.setMessage("Album name already exists. Try again");
                                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            return;
                                        }
                                    });
                                    alert.show();
                                }
                                else {
                                    userResponse = input.getText().toString();
                                    albums.get(position2).setName(userResponse);
                                    albumNames.setAdapter(arrayAdapter);
                                    edit.setVisibility(View.INVISIBLE);
                                    delete.setVisibility(View.INVISIBLE);
                                }
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                edit.setVisibility(View.INVISIBLE);
                                delete.setVisibility(View.INVISIBLE);
                                dialog.cancel();
                            }
                        });
                        builder.show();
                    }
                });
                delete.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Are you sure you want to delete album "+arrayAdapter.getItem(position2).getName()+"?");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                albums.remove(position2);
                                albumNames.setAdapter(arrayAdapter);
                                edit.setVisibility(View.INVISIBLE);
                                delete.setVisibility(View.INVISIBLE);
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                edit.setVisibility(View.INVISIBLE);
                                delete.setVisibility(View.INVISIBLE);
                                dialog.cancel();
                            }
                        });
                        builder.show();
                    }
                });
                return true;
            }
        });


        albumNames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ThumbnailViewActivity.class);
                intent.putExtra("index", position);
                startActivity(intent);
            }

        });






        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Enter album name.");

                final EditText input = new EditText(context);
                input.setInputType(InputType.TYPE_CLASS_TEXT);

                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(input.getText().toString().trim().isEmpty()){
                            AlertDialog.Builder alert = new AlertDialog.Builder(context);
                            alert.setTitle("Invalid");
                            alert.setMessage("Must input at least one character");
                            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    return;
                                }
                            });
                            alert.show();

                        }
                        else if(findAlbumName(input.getText().toString().toLowerCase()) != -1){
                            AlertDialog.Builder alert = new AlertDialog.Builder(context);
                            alert.setTitle("Duplicate");
                            alert.setMessage("Album name already exists. Try again");
                            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    return;
                                }
                            });
                            alert.show();
                        }
                        else {
                            userResponse = input.getText().toString();
                            Album albumToAdd = new Album(userResponse);
                            albums.add(albumToAdd);
                            arrayAdapter = new ArrayAdapter<Album>(context, R.layout.album, albums);
                            albumNames.setAdapter(arrayAdapter);
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

            }
        });
    }

    public int findAlbumName(String name){

        for(int i = 0; i < albums.size(); i++) {
            if(albums.get(i).getName().equalsIgnoreCase(name)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
