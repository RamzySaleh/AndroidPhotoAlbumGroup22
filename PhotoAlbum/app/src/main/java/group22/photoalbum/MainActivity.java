package group22.photoalbum;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import android.widget.Spinner;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    final Context context = this;
    String userResponse;
    ListView albumNames;
    ArrayAdapter<Album> arrayAdapter;
    public static PhotoAlbum pa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        pa = PhotoAlbum.loadFromDisk();
        if(pa == null) {
            Log.i("save","nothing found!");
            pa = new PhotoAlbum();
        }
        Log.i("save","on create started!");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView toolbar = (TextView) findViewById(R.id.toolbar_title);

        toolbar.setText("Albums");

        albumNames = (ListView)findViewById(R.id.photo_list);
        albumNames.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        albumNames.setSelection(0);
        arrayAdapter = new ArrayAdapter<Album>(context, R.layout.album, PhotoAlbum.albums);
        albumNames.setAdapter(arrayAdapter);


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
                                    PhotoAlbum.albums.get(position2).setName(userResponse);
                                    PhotoAlbum.saveToDisk(pa);
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
                                PhotoAlbum.albums.remove(position2);
                                PhotoAlbum.saveToDisk(pa);
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

        FloatingActionButton search = (FloatingActionButton) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.searchphotos_dialog);
                dialog.setTitle("Find Photos by Tag");

                Button searchTag = (Button) dialog.findViewById(R.id.dialogSearch);
                Button cancel = (Button) dialog.findViewById(R.id.dialogCancel);
                searchTag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText tagValue = (EditText) dialog.findViewById(R.id.tagValue);
                        Spinner tagType = (Spinner) dialog.findViewById(R.id.dialog_spinner);
                        if(tagValue.getText().toString().trim().isEmpty()){
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
                        else {
                            String type = tagType.getSelectedItem().toString();
                            String value = tagValue.getText().toString();

                            if(type.equals("location")){
                                for(int i = 0; i < pa.albums.size(); i++){
                                    for(int x = 0; x < pa.albums.get(i).getPhotos().size(); x++){
                                        //create new method for tags with location key under photo
                                    }
                                }
                            }
                            else {
                                for(int i = 0; i < pa.albums.size(); i++){
                                    for(int x = 0; x < pa.albums.get(i).getPhotos().size(); x++){
                                        //create new method for tags with person key under photo
                                    }
                                }
                            }

                        }

                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Enter album name.");
                FloatingActionButton edit = (FloatingActionButton) findViewById(R.id.edit);
                FloatingActionButton delete = (FloatingActionButton) findViewById(R.id.delete);
                edit.setVisibility(View.INVISIBLE);
                delete.setVisibility(View.INVISIBLE);

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
                            PhotoAlbum.albums.add(albumToAdd);
                            PhotoAlbum.saveToDisk(pa);
                            arrayAdapter = new ArrayAdapter<Album>(context, R.layout.album, PhotoAlbum.albums);
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

        for(int i = 0; i < PhotoAlbum.albums.size(); i++) {
            if(PhotoAlbum.albums.get(i).getName().equalsIgnoreCase(name)){
                return i;
            }
        }
        return -1;
    }



}
