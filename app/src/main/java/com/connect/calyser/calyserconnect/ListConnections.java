package com.connect.calyser.calyserconnect;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class ListConnections extends AppCompatActivity {
//
    ArrayList<String> Connections = new ArrayList<String>();
    ArrayAdapter<String> mAdapter = null;
    ListView mListView = null;
//
@Override
public boolean onCreateOptionsMenu(Menu menu) {
    //this.optionsMenu = menu;
    //MenuInflater inflater = getMenuInflater(); //ERROR<-----------
    getMenuInflater().inflate(R.menu.menu, menu);
    //return super.onCreateOptionsMenu(menu); // in Fragment cannot be applied <------------
    return true;
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
        setContentView(R.layout.activity_list_contacts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
        //
        // Setting the item click listener for the listview
        mListView.setOnItemClickListener(itemClickListener);
        //
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
        mListView = (ListView) findViewById(R.id.contactlist);
        mListView.setAdapter(mAdapter);
        //

    }
    //

    private void GetConnections() {
    //
    Connections.clear();
        //
        Connections.add("192.168.1.23");
        //

    }
}
