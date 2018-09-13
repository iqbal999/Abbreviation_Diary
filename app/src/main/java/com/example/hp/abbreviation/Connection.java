package com.example.hp.abbreviation;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Connection extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "abbreviation";
    private static String DB_PATH = "/data/data/com.example.hp.abbreviation/databases/";
    private static String DB_NAME = "alphabet.db";
    private SQLiteDatabase sqLiteDatabase;
    private Context context = null;

    public Connection(Context context) {
        super(context, DB_NAME, null, 10);
        this.context = context;
    }

    public void createDataBase() {
        boolean dbExixts = checkDataBase();
        if (dbExixts) {

        } else {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }

    private void copyDataBase() {
        try {
            //Open your local db as the input stream
            InputStream inputStream = context.getAssets().open(DB_NAME);

            // Path to the just created empty db
            String outFileName = DB_PATH + DB_NAME;

            //Open the empty db as the output stream
            OutputStream outputStream = new FileOutputStream(outFileName);

            //transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;

            while ((length = inputStream.read(buffer)) > 0) {

                outputStream.write(buffer, 0, length);
            }

            //Close the streams
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        }catch (IOException ex){

        }


    }

    public boolean checkDataBase() {
        SQLiteDatabase tempDB = null;

        try {
            String myPath = DB_PATH + DB_NAME;
            tempDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

        } catch (Exception e) {

        }

        if (tempDB != null) {
            tempDB.close();
        }

        return tempDB != null ? true : false;

    }

    public void openDataBase() {
        String myPath = DB_PATH + DB_NAME;
        sqLiteDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

    }

    @Override
    public synchronized void close() {
        if (sqLiteDatabase != null)
            sqLiteDatabase.close();
        super.close();
    }

    public void ejecutar() {

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public Cursor displayData(String data) {
        Cursor cursor = null;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] sqlSelect = {"_id", "short_name", "full_name"};

        qb.setTables(TABLE_NAME);

        if (data.length() == 1) {
            cursor = qb.query(sqLiteDatabase, sqlSelect, "short_name LIKE ?", new String[]{data + "%"}, null, null, "short_name ASC");
        } else if (data.length() > 1) {
            cursor = qb.query(sqLiteDatabase, sqlSelect, "short_name = ?", new String[]{data}, null, null, null);
        }
        return cursor;
    }
}
