package edu.lcark.homework6;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Greg on 4/5/2016.
 */
public class SQLHelperLocation extends SQLiteOpenHelper {

    private static final String DB_NAME = "thing2.db";
    private static SQLHelperLocation sInstance;

    public static SQLHelperLocation getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SQLHelperLocation(context.getApplicationContext(), DB_NAME, null, 1);
        }
        return sInstance;
    }

    public SQLHelperLocation(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Location.TABLE_NAME + " ( " +
                Location._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Location.COL_LAT + " REAL, " +
                Location.COL_LONG + " REAL, " +
                Location.COL_NAME + " TEXT, " +
                Location.COL_NOTES + " TEXT, " +
                Location.COL_USER + " TEXT )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertLocation(Location location) {
        getWritableDatabase().insert(Location.TABLE_NAME, null, location.getContentValues());
    }
}
