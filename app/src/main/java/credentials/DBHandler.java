package credentials; /**
 * Created by martin on 15.01.2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
//
import java.net.PasswordAuthentication;
//
public class DBHandler extends SQLiteOpenHelper {
    //
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "password.db";
    private static final String TABLE_PASSWORD = "UserCresentials";
    //
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSSWORD = "password";
    //
    public static final int UserNameDoesNotExist = 0;
    public static final int WrongPassword = 1;
    public static final int PasswordCorrect = 2;
    //
    public DBHandler(Context context,
                     SQLiteDatabase.CursorFactory factory, PasswordAuthentication mPasswordAuthentication) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }
    //
    //
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " +
                TABLE_PASSWORD + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_USERNAME
                + " TEXT," + COLUMN_PASSSWORD + " TEXT" + ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }
//
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PASSWORD);
        onCreate(db);
    }
    //
    public int ResultMatch(PasswordAuthentication mPasswordAuthentication) {
        //
        int result = WrongPassword;
        //
        String mUsername = String.valueOf(mPasswordAuthentication.getUserName());
        String mPassword = String.valueOf(mPasswordAuthentication.getPassword());
        String DBPassword = findPassword(mUsername);
        //
        Log.d("ResultMatch","Username="+mUsername);
        Log.d("ResultMatch","Password="+mPassword);
        Log.d("ResultMatch","PasswordDB="+DBPassword);
        //
        if ( DBPassword.isEmpty()) {
            result = UserNameDoesNotExist;
        } else
            if (mPassword.equals(DBPassword)) result = PasswordCorrect;

        //
        return result;
        //
    }
    //
    public void WriteRecord(PasswordAuthentication mPasswordAuthentication){
        //
        String mUsername = String.valueOf(mPasswordAuthentication.getUserName());
        String mPassword = String.valueOf(mPasswordAuthentication.getPassword());
        //
        Log.d("WRITERECORD","Username="+mUsername);
        Log.d("WRITERECORD","Password="+mPassword);
        //
        String query = "INSERT INTO "+TABLE_PASSWORD+ "("+COLUMN_USERNAME+","+COLUMN_PASSSWORD+")"+
                " VALUES("+"'"+mUsername+"'"+","+"'"+mPassword+"'"+")";
        //
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME,mUsername);
        values.put(COLUMN_PASSSWORD,mPassword);
        //
        SQLiteDatabase db = this.getWritableDatabase();
        //
        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                TABLE_PASSWORD, null,values
        );
        //
        db.close();
        //
        System.out.println("Query="+query);
        System.out.println("Output of Insert="+newRowId);
        //
    }
    //
    public String findPassword(String username) {
        //
        String query = "Select * FROM " + TABLE_PASSWORD + " WHERE " + COLUMN_USERNAME + " =  \"" + username + "\"";
        //
        SQLiteDatabase db = this.getReadableDatabase();
        //
        Cursor cursor = db.rawQuery(query, null);
        //
        String mPassword = "";
        //
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            mPassword = cursor.getString(2);
            System.out.println("String 0 ="+cursor.getString(0)+" String 1 ="+cursor.getString(1));
            cursor.close();
        }
        //
        db.close();
        //
        return mPassword;
        //
    }
}
