package DBs; /**
 * Created by martin on 15.01.2016.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//
public class COMMANDS_DBHandler extends SQLiteOpenHelper {
    //
    //DB Username Password
    //
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "commands6.db";
    public static final String TABLE_COMMANDS = "Commands";
    //
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_COMMANDS_RECEIVED = "COMMANDS_RECEIVED";
    public static final String COLUMN_COMMANDS_SENT = "COMMANDS_SENT";
    public static final String COLUMN_COMMANDS_RESULT = "COMMANDS_RESULT";
    public static final String COLUMN_COMMANDS_TOSEND = "COMMANDS_TOSEND";

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
                + COLUMN_COMMANDS_SENT + " TEXT,"
                + COLUMN_COMMANDS_TOSEND + " TEXT,"
                + COLUMN_COMMANDS_RESULT + " TEXT"
                + ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);
        System.out.println("Calyser.COMMANDS_DBHandler " + CREATE_PRODUCTS_TABLE);
    }

    //
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMANDS);
        System.out.println("Calyser.COMMANDS_DBHandler.onUpgrade");
        onCreate(db);
    }

    //
    private void DumpDB() {
        System.out.println(" Calyser.DUMPDB.Start **************************** ");
        String mQuery = "SELECT * FROM " + TABLE_COMMANDS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor mCur = db.rawQuery(mQuery, new String[]{});
        mCur.moveToFirst();
        while (!mCur.isAfterLast()) {
            String received = mCur.getString(mCur.getColumnIndex(COLUMN_COMMANDS_RECEIVED));
            String sent = mCur.getString(mCur.getColumnIndex(COLUMN_COMMANDS_SENT));
            String tosend = mCur.getString(mCur.getColumnIndex(COLUMN_COMMANDS_TOSEND));
            String result = mCur.getString(mCur.getColumnIndex(COLUMN_COMMANDS_RESULT));
            System.out.println(" Received= " + received + " result= " + result + " sent= " + sent + " tosend= "+tosend);
            mCur.moveToNext();
        }
        db.close();
        //
        System.out.println(" Calyser.DUMPDB.END **************************** ");
        //
    }

    //
    public void WriteOneMessageToSend(String message) {
        //
        String insert = "INSERT INTO " + TABLE_COMMANDS + "(" + COLUMN_COMMANDS_TOSEND + ")" +
                " VALUES(" + "'" + message + "'" + ")";
        //
        System.out.println("Calyser.StoreSent.Insert " + insert);
        //
        SQLiteDatabase db = this.getWritableDatabase();
        //
        // Insert the new row, returning the primary key value of the new row
        //
        System.out.println("Calyser.Store to DB");
        //
        db.execSQL(insert);
        db.close();
        //
        System.out.println("Calyser.Message stored in DB");
        //
    }

    //
    public String ReadOneMessageToSend() {
        //
        System.out.println("Calyser.ReadOneMessageToSend");
        //
        String mQuery = "SELECT * FROM " + TABLE_COMMANDS + " WHERE " + COLUMN_COMMANDS_SENT + " IS NULL";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor mCur = db.rawQuery(mQuery, new String[]{});
        mCur.moveToFirst();
        //
        String received = mCur.getString(mCur.getColumnIndex(COLUMN_COMMANDS_RECEIVED));
        String sent = mCur.getString(mCur.getColumnIndex(COLUMN_COMMANDS_SENT));
        String tosend = mCur.getString(mCur.getColumnIndex(COLUMN_COMMANDS_TOSEND));
        String result = mCur.getString(mCur.getColumnIndex(COLUMN_COMMANDS_RESULT));
        System.out.println(" Received= " + received + " result= " + result + " sent= " + sent+ " tosend= "+tosend);
        //
        db.close();
        //
        return tosend;
        //
    }
    //
    public void SetMessageToSent(String message){
        //
        //DumpDB();
        //
        String mQuery = " UPDATE " + TABLE_COMMANDS +
                " SET "  + COLUMN_COMMANDS_SENT + " = " + "'" + message + "'"+
                " WHERE " + COLUMN_COMMANDS_TOSEND + " = " + "'" + message + "'"
                ;
                //
        System.out.println("Query "+mQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(mQuery);
        System.out.println("Query executed");
        //
    }
    //
    public void StoreSent(String mSent) {
        //
        System.out.println("Calyser.StoreSent");
        //
        String insert = "INSERT INTO "+TABLE_COMMANDS+ "("+ COLUMN_COMMANDS_SENT +")"+
                " VALUES("+"'"+mSent+"'"+")";
        //
        System.out.println("Calyser.StoreSent.Insert "+insert);
        //
        SQLiteDatabase db = this.getWritableDatabase();
        // Insert the new row, returning the primary key value of the new row
        //
        System.out.println("Calyser.Store to DB");
        //
        db.execSQL(insert);
        db.close();
        //
        System.out.println("Calyser.Message stored in DB");
        //
        //DumpDB();
        //
    }
    //
    public void StoreReceived(String mReceived) {
        //
        System.out.println("Calyser.StoreReceived");
        //
        String insert = "INSERT INTO "+TABLE_COMMANDS+ "("+ COLUMN_COMMANDS_RECEIVED +")"+
                " VALUES("+"'"+mReceived+"'"+")";
        //
        //System.out.println("Calyser.StoreReceived.Insert "+insert);
        //
        SQLiteDatabase db = this.getWritableDatabase();
        // Insert the new row, returning the primary key value of the new row
        //
        //System.out.println("Calyser.Store to DB");
        //
        db.execSQL(insert);
        db.close();
        //
        //System.out.println("Calyser.Message stored in DB");
        //
        //DumpDB();
        //
    }
    /*  */

}
