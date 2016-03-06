package com.connect.calyser.calyserconnect;

import android.content.Context;
import android.os.Environment;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 * Created by martin on 21.02.2016.
 */
public class CalyserFileWriter {
    /**
     * Created by martin on 21.02.2016.
     */



    private static Context mContext = null;

    private static String FileName = "Log.txt";

    private static File LogFile = null;

    public CalyserFileWriter GetFileWriter(Context pContext)  {
        System.out.println("CalyserWriter.GetFileWriter");
        mContext = pContext;
        createFileOnDevice();
//
        return this;
    }

    private void createFileOnDevice() {
                    /*
                     * Function to initially create the log file and it also writes the time of creation to file.
                     */
        System.out.println("Create.OpenFile "+mContext.getFilesDir()+"/"+FileName);
        //
        LogFile = new File(mContext.getFilesDir(),FileName);
        try {
            LogFile.createNewFile();
        } catch (IOException e) {
            System.out.println("CalyserWriter.createFileOnDevice");
            e.printStackTrace();
        }
    }


    public void writeToFile(String message) {
//
        System.out.println("File.Logged message " + message + "\n");
//
        FileWriter LogWriter = null;
        try {
            LogWriter = new FileWriter(LogFile, true);
        } catch (IOException e) {
            System.out.println("CalyserWriter.CreateFileOnDevice.FileWriter");
            e.printStackTrace();
        }
        //
        System.out.println("Create.Open File " + mContext.getFilesDir() + "/" + FileName);
        //
        BufferedWriter out = null;
        //
        if (LogWriter != null) {
            out = new BufferedWriter(LogWriter);
            try {
                out.write("Logged \n");
            } catch (IOException e) {
                System.out.println("CalyserWriter.createFileOnDevice.BufferedWriter");
                e.printStackTrace();
            }
//
            System.out.println("File.Logged ");
//
            try {
                out.close();
            } catch (IOException e) {
                System.out.println("CalyserWriter.createFileOnDevice.out");
                e.printStackTrace();
            }
            //
            try {
                out.write(message + "\n");
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //
    public void displayOutput(TextView mTextView)
    {
        StringBuilder text = new StringBuilder();
//
        System.out.println("Display.Open File "+mContext.getFilesDir()+"/"+FileName);
//
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(new File(mContext.getFilesDir(),FileName)));
        } catch(IOException e){
            System.out.println("CalyserFileWriter.BufferedReader");
            e.printStackTrace();
        }
//
        String line;
        if ( br != null ) {
            try {
                while ((line = br.readLine()) != null) {
                    text.append(line);
                    System.out.println("Calyser.File " + line);
                    text.append('\n');
                }
            } catch ( IOException e ) {
                System.out.println("CalyserFileWriter.readline");
                e.printStackTrace();
            }
            //
            mTextView.setText(text);
        }
        //
    }


}

