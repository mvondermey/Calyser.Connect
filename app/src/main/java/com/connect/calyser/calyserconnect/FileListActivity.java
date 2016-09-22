package com.connect.calyser.calyserconnect;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import DBs.DATA_DBHandler;


public class FileListActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private DATA_DBHandler mDbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //
        mDbHandler = new DATA_DBHandler(getApplicationContext(), null, DATA_DBHandler.DATA_DATABASE_NAME, DATA_DBHandler.DATA_DATABASE_VERSION);
        //
        String[] values = GetFileNames();
        //

        //String[] values = new String[]{"Android", "iPhone", "WindowsMobile",
        //        "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
        //        "Linux", "OS/2", "Android1", "Android2"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);
        //

        final ListView mListView = (ListView) findViewById(R.id.filelist);
        mListView.setAdapter(adapter);

        // Item Click Listener for the listview
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View container, int position, long id) {
//
                System.out.print("Button pressed");
//
                 String selectedItem = (String) parent.getItemAtPosition(position);
//
                Toast.makeText(getApplicationContext(), " Pressed "+selectedItem, Toast.LENGTH_SHORT).show();
            }
        };
        //
        // Setting the item click listener for the listview
        mListView.setOnItemClickListener(itemClickListener);

//

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_files);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //
        //Adding ports 5000 and 5001
        //
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "FileList Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.connect.calyser.calyserconnect/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
        //


        //
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "FileList Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.connect.calyser.calyserconnect/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    public String[] GetFileNames(){
        //
        String[] values = new String[]{"Test","Martin"};
        //
        System.out.println("CREDENTIALS_DBHandler "+mDbHandler);
        //
        //values = mDbHandler.GetDBFileNames();
        //
        return values;
    }

}
