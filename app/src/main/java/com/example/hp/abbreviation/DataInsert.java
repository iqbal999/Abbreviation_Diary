package com.example.hp.abbreviation;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DataInsert extends AppCompatActivity {

    MyDBHelper myDBHelper;
    EditText short_name, full_name;
    String s_name,f_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_insert);

        myDBHelper = new MyDBHelper(this);
        SQLiteDatabase sqLiteDatabase = myDBHelper.getWritableDatabase();
        short_name = findViewById(R.id.short_name);
        full_name = findViewById(R.id.full_name);


    }

    public void save(View view) {
        s_name = short_name.getText().toString();
        f_name = full_name.getText().toString();

        long rowId = myDBHelper.insert(s_name,f_name);

        if(rowId == -1){
            Toast.makeText(this, "Unsuccessful", Toast.LENGTH_SHORT).show();
        }else if (rowId >0){
            Toast.makeText(this, "Row "+rowId+" is successfully inserted", Toast.LENGTH_SHORT).show();
        }


    }

    /*public void display(View view) {
        Cursor cursor = myDBHelper.displayData();
        if(cursor.getCount() == 0){
            showData("Error","No data found");
        }
        StringBuffer stringBuffer = new StringBuffer();

        while (cursor.moveToNext()){
            stringBuffer.append("ID: "+cursor.getString(0)+"\n");
            stringBuffer.append("SHORT NAME: "+cursor.getString(1)+"\n");
            stringBuffer.append("FULL NAME: "+cursor.getString(2)+"\n\n");


        }

        showData("ResultSet",stringBuffer.toString());
    }*/

    public void showData(String title, String data){

        startActivity(new Intent(this,DisplayData.class));



        /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(data);
        builder.setCancelable(true);
        builder.show();*/

    }
}
