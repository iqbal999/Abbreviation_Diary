package com.example.hp.abbreviation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.widget.Toast;

public class MyDBHelper extends SQLiteOpenHelper {
    String[] alphabet = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l"
            , "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

    private static final String DB_NAME = "alphabet.db";
    private static final String TABLE_NAME = "abbreviation";

    private Context context;

    private static final String ID = "id";
    private static final String SHORT_NAME = "short_name";
    private static final String FULL_NAME = "full_name";


    private static final String CREATE_TABLE = "create table " + TABLE_NAME + " (" + ID + " integer primary key autoincrement," +
            SHORT_NAME + " varchar(255) ," + FULL_NAME + " varchar(255))";

    private static final String DROP_TABLE = "drop table if exists " + TABLE_NAME + " ";
    private static final String DISPLAY_DATA = "select * from " + TABLE_NAME + " order by " + SHORT_NAME + " asc";
    private static final String DISPLAY_SPEC_DATA = "select * from " + TABLE_NAME + " where " + SHORT_NAME + " like '" + "?" + "%'";
    private static final String SEARCH_DATA = "select * from " + TABLE_NAME + " where " + SHORT_NAME + "=?";


    public MyDBHelper(Context context) {
        super(context, DB_NAME, null, 5);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Toast.makeText(context, "On create method is called", Toast.LENGTH_SHORT).show();
            db.execSQL(CREATE_TABLE);
        } catch (Exception e) {
            Toast.makeText(context, "Exception " + e, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try {
            Toast.makeText(context, "On upgrade method is called", Toast.LENGTH_SHORT).show();
            db.execSQL(DROP_TABLE);
            onCreate(db);
        } catch (Exception e) {
            Toast.makeText(context, "Upgrade Exception", Toast.LENGTH_SHORT).show();

        }
    }

    public long insert(String short_name, String full_name) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SHORT_NAME, short_name);
        contentValues.put(FULL_NAME, full_name);

        long rowID = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

        return rowID;

    }

    public Cursor displayData() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(SEARCH_DATA, new String[]{"a"});

        return cursor;
    }

    public Cursor displayData(String data) {
        Cursor cursor = null;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] sqlSelect = {"id", "short_name", "full_name"};

        qb.setTables(TABLE_NAME);

        if (data.length() == 1) {
            cursor = qb.query(sqLiteDatabase, sqlSelect, "short_name LIKE ?", new String[]{data + "%"}, null, null, SHORT_NAME + " ASC");
        } else if (data.length() > 1) {
            cursor = qb.query(sqLiteDatabase, sqlSelect, "short_name = ?", new String[]{data}, null, null, null);
        }
        return cursor;
    }
}
