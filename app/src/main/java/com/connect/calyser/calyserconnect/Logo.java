package com.connect.calyser.calyserconnect;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class Logo extends AppCompatActivity {

    private Server mServer;
    private Client mClient;
    private Discovery mDiscovery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //
        //
        ImageView image = (ImageView) findViewById(R.id.logo);
        image.setImageResource(R.mipmap.ic_launcher);
        //
        SIngletonCalyser.addPort(5000);
        SIngletonCalyser.addPort(5001);
        //
        SIngletonCalyser.addConnections("10.0.2.2",8081);
        /*
        mClient = new Client();
        mClient.startClient(this);
        */
        //
        mServer = new Server();
        mServer.startServer(this);
        //
        //
        //mDiscovery = new Discovery();
        //mDiscovery.SayHi(this);
        //
        // Now start Activity
        //
        System.out.println("Start Activity");
        //
        Intent myIntent = new Intent(this, PhotoActivity.class);
        this.startActivity(myIntent);
        //
    }

}
