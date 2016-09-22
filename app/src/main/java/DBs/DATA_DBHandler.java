package DBs; /**
 * Created by martin on 15.01.2016.
 */
//
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//
public class DATA_DBHandler extends SQLiteOpenHelper {
    //
    // DB Data
    //
    public static final int DATA_DATABASE_VERSION = 1;
    public static final String DATA_DATABASE_NAME = "data.db";
    public static final String TABLE_DATA = "UserData";
    //
    public static final String DATA_COLUMN_ID = "_id";
    public static final String DATA_COLUMN_JSON = "DATA_JSON";
    //
    private String [] FileNames;
    //
    public DATA_DBHandler(Context context,
                          SQLiteDatabase.CursorFactory factory, String DBName, Integer DBVersion) {
        super(context, DBName, factory, DBVersion);
    //
    }
    //
    @Override
    public void onCreate(SQLiteDatabase db) {
        //
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " +
                TABLE_DATA + "("
                + DATA_COLUMN_ID + " INTEGER PRIMARY KEY," + DATA_COLUMN_JSON
                + " TEXT )";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }
//
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);
        onCreate(db);
    }
    //

    public String[] GetDBFileNames(){
    //
        FileNames = new String[]{"Files"};
    //
        String query = "Select " + DATA_COLUMN_JSON + " FROM " + TABLE_DATA ;
        //
        SQLiteDatabase db = this.getReadableDatabase();
        //
        Cursor cursor = db.rawQuery(query, null);
        //
        db.close();
        //
        return FileNames;
        //
    }

}
