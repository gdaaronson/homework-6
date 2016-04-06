package edu.lcark.homework6;

import android.content.ContentValues;
import android.provider.BaseColumns;

/**
 * Created by Greg on 3/31/2016.
 */
public class Location implements BaseColumns {

    private double mLat;

    private double mLng;

    private String mName;

    private String mNote;

    private String mUser;

    public static final String TABLE_NAME = "Locations";
    public static final String COL_LAT = "Latitude";
    public static final String COL_LONG = "Longitude";
    public static final String COL_NAME = "Name";
    public static final String COL_NOTES = "Notes";
    public static final String COL_USER = "User";

    public Location(double lat, double lng, String user){
        mLat = lat;
        mLng = lng;
        mUser = user;
    }

    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_LAT, mLat);
        contentValues.put(COL_LONG, mLng);
        contentValues.put(COL_NAME, mName);
        contentValues.put(COL_NOTES, mNote);
        contentValues.put(COL_USER, mUser);
        return contentValues;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public void setNote(String mNote) {
        this.mNote = mNote;
    }
}
