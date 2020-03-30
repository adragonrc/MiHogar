package com.alexander_rodriguez.mihogar;

import android.content.ContentValues;
import android.database.Cursor;

public class TableCursor {
    private ContentValues cv;
    private String[][] s;

    public TableCursor(Cursor c) {
        cv = new ContentValues();
        s = new String[c.getCount()][c.getColumnCount()];
        if (c.moveToFirst()) {
            for (int i = 0; i < c.getColumnCount(); i++)
                cv.put(c.getColumnName(i), i);
            do {
                for (int i = 0; i < c.getColumnCount(); i++)
                    s[c.getPosition()][i] = c.getString(i);
            } while (c.moveToNext());
        }
    }
    public String getValue(int index, String key){
        return s[index][cv.getAsInteger(key)];
    }

    public ContentValues getCv() {
        return cv;
    }

    public String[][] getS() {
        return s;
    }

    public int getCount(){
        return s.length;
    }

    public int getColumCount(){
        return cv.size();
    }

    public static ContentValues cursorToContentValues(Cursor c){
        ContentValues cv = new ContentValues();
        String [][]s = new String[c.getCount()][c.getColumnCount()];
        if (c.moveToFirst()) {
            for (int i = 0; i < c.getColumnCount(); i++)
                cv.put(c.getColumnName(i), i);
            do {
                for (int i = 0; i < c.getColumnCount(); i++)
                    s[c.getPosition()][i] = c.getString(i);
            } while (c.moveToNext());
        }
        return cv;
    }
}
