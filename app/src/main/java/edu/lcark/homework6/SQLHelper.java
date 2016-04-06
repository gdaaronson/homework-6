package edu.lcark.homework6;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Greg on 3/31/2016.
 */
public class SQLHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "thing.db";
    private static SQLHelper sInstance;

    public SQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static SQLHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SQLHelper(context.getApplicationContext(), DB_NAME, null, 1);
        }

        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + User.TABLE_NAME + " ( " +
                        User._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        User.COLUMN_NAME_NAME + " TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertUser(User user) {
        getWritableDatabase().insert(User.TABLE_NAME, null, user.getContentValues());
    }
}
