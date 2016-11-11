package com.connect.calyser.calyserconnect;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Iterator;


public class ListConnections extends AppCompatActivity {
    //
    ArrayList<String> Connections = new ArrayList<String>();
    ArrayAdapter<String> mAdapter = null;
    ListView mListView = null;
    Dialog mDialog = null;
    EditText mConnection = null;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    //
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //this.optionsMenu = menu;
        //MenuInflater inflater = getMenuInflater(); //ERROR<-----------
        //
        System.out.println("Creating Menu");
        //
        getMenuInflater().inflate(R.menu.menulistconnections, menu);
        //
        //return super.onCreateOptionsMenu(menu); // in Fragment cannot be applied <------------
        return true;
    }
    //

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings_add_connection) {
      //
            LayoutInflater layoutInflater = LayoutInflater.from(this);
            mDialog  = new Dialog(this); // Context, this, etc.
            View promptView = layoutInflater.inflate(R.layout.enter_new_connection, null);
            mDialog.setContentView(promptView);
            //
            final EditText mConnectionView = (EditText) promptView.findViewById(R.id.new_connection);
            mConnectionView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                //
                @Override
                public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                    System.out.println("Calyser Done " + id);
                    //
                    if (id == R.id.connections || id == EditorInfo.IME_NULL) {
                        System.out.println("Calyser Done Done ");
                        //Snackbar.make(getCurrentFocus(), "ACTION DONE", Snackbar.LENGTH_LONG)
                        //        .setAction("Action", null).show();
                        //
                        String IPadress = mConnectionView.getText().toString();
                        //
                        AddConnection(IPadress);
                        //
                        return true;
                        //
                    }
                    return false;
                }
            });
            //
            mDialog.show();
            //
            return true;
        }
//
        return super.onOptionsItemSelected(item);
        //
    }
    //
    @Override
    public void onResume() {
        super.onResume();
        //
        UpdateUI();
        //
    }

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_connections);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_connections);
        setSupportActionBar(toolbar);
        //
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_connections);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
//
        //
        UpdateUI();
        //
        // Item Click Listener for the listview

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View container, int position, long id) {
//
//                System.out.print("Button pressed");
//
                String selectedItem = (String) parent.getItemAtPosition(position);
//
                Toast.makeText(getApplicationContext(), " Pressed " + selectedItem, Toast.LENGTH_SHORT).show();
            }
        };

        mListView = (ListView) findViewById(R.id.connections);
        mListView.setOnItemClickListener(itemClickListener);
        //
        /*
        setContentView(R.layout.enter_new_connection);
        mConnection = (EditText) findViewById(R.id.new_connection);
        System.out.println("mConnection "+mConnection);
        //
        mConnection.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                //
                Toast.makeText(getApplicationContext(), " Pressed " + keyEvent, Toast.LENGTH_SHORT).show();
                //
                return true;
                //
            }
            //
        });
        */
        //



        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
    //
    public void AddConnection(String IPadress){
        //
        SingletonCalyser.addConnections(IPadress,8001);
        //
        mDialog.dismiss();
        UpdateUI();
        //
    }
    //
    private void UpdateUI() {
        //
        GetConnections();
        String[] values = new String[Connections.size()];
        Connections.toArray(values);
        //
        mAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);
        mListView = (ListView) findViewById(R.id.connections);
        mListView.setAdapter(mAdapter);
        //
    }
    //

    private void GetConnections() {
        //
        Connections.clear();
        //
        //Connections.add("192.168.1.23");
        //
        //SIngletonCalyser.addConnections("192.168.1.23", 8001);
        //SIngletonCalyser.addConnections("192.168.2.23", 8001);
        //
        final ArrayList mListConnections = SingletonCalyser.getConnections();
        //
        Iterator itr = mListConnections.iterator();
        while (itr.hasNext()) {
            Pair<String, Integer> connection = (Pair<String, Integer>) itr.next();
            //
            String ip = connection.first;
            int port = connection.second;
            //
            Connections.add(ip);
            //
        }
        //

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ListConnections Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.connect.calyser.calyserconnect/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ListConnections Page", // TODO: Define a title for the content shown.
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
}
