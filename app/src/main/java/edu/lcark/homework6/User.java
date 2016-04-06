package edu.lcark.homework6;

import android.content.ContentValues;
import android.provider.BaseColumns;

/**
 * Created by Greg on 3/31/2016.
 */
public class User implements BaseColumns {

    private String mName;

    public static final String TABLE_NAME = "Users";
    public static final String COLUMN_NAME_NAME = "Username";

    public User(String name){
        mName = name;
    }

    public String getUsername() {
        return mName;
    }

    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_NAME, mName);
        return contentValues;
    }
}
