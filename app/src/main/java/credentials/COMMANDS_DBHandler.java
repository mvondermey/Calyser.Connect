package credentials; /**
 * Created by martin on 15.01.2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.net.PasswordAuthentication;

//
public class COMMANDS_DBHandler extends SQLiteOpenHelper {
    //
    //DB Username Password
    //
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "commands.db";
    public static final String TABLE_COMMANDS = "Commands";
    //
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_COMMANDS_RECEIVED = "COMMANDS_RECEIVED";
    public static final String COLUMN_COMMANDS_SENT = "COMMANDS_SENT";
    public static final String COLUMN_COMMANDS_RESULT = "COMMANDS_RESULT";
    //
    public COMMANDS_DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    //
    @Override
    public void onCreate(SQLiteDatabase db) {
        //
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " +
                TABLE_COMMANDS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_COMMANDS_RECEIVED + " TEXT,"
                + COLUMN_COMMANDS_SENT + " TEXT "
                + COLUMN_COMMANDS_RECEIVED + "TEXT"
                + ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }
//
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMANDS);
        onCreate(db);
    }
    //
    private void DumpDB() {
        String mQuery = "SELECT * FROM "+TABLE_COMMANDS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor mCur = db.rawQuery(mQuery, new String[]{});
        mCur.moveToFirst();
        while ( !mCur.isAfterLast()) {
            String received  = mCur.getString(mCur.getColumnIndex(COLUMN_COMMANDS_RECEIVED));
            String result    = mCur.getString(mCur.getColumnIndex(COLUMN_COMMANDS_RESULT));
            String sent      = mCur.getString(mCur.getColumnIndex(COLUMN_COMMANDS_SENT));
            System.out.println(" Received = "+received);
            mCur.moveToNext();
        }
        db.close();
        //

        //
    }
    //
    public void StoreReceived(String mReceived) {
        //
        System.out.println("Calyser.StoreReceived");
        //
        String insert = "INSERT INTO "+TABLE_COMMANDS+ "("+ COLUMN_COMMANDS_RECEIVED +")"+
                " VALUES("+"'"+"TEST"+"'"+")";
        //
        System.out.println("Calyser.StoreReceived.Insert "+insert);
        //
        SQLiteDatabase db = this.getWritableDatabase();
        // Insert the new row, returning the primary key value of the new row
        //
        System.out.println("Store to DB");
        //
        db.execSQL(insert);
        db.close();
        //
        System.out.println("Calyser.Message stored in DB");
        //
        DumpDB();
        //
    }
    /*  */

}
