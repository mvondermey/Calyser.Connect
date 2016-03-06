package com.connect.calyser.calyserconnect;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

public class Logging extends AppCompatActivity {

    View.OnClickListener mOnClickListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logging);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Refresh", Snackbar.LENGTH_LONG)
                        .setAction("Action", mOnClickListener).show();
            }
        });
        //
        RefreshOutput();
        //
        mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefreshOutput();
            }
        };


    }


    private void RefreshOutput(){
        //
        TextView mLogging = (TextView) findViewById(R.id.logging_text);
        mLogging.append("\n");
        mLogging.append("Appending text");
        //
        System.out.println("Logging.RefreshOutput.CalyserFileWriter.1");
        //
        CalyserFileWriter calyserFileWriter = new CalyserFileWriter().GetFileWriter(this.getApplicationContext());
        //
        System.out.println("Logging.RefreshOutput.CalyserFileWriter.2");
        //
        calyserFileWriter.displayOutput(mLogging);
        //
    }

}
