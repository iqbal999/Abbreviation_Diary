package com.example.hp.abbreviation;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class DisplayData extends AppCompatActivity {

    ListView listView;
    //MyDBHelper myDBHelper;
    Connection myDBHelper;
    String search_value;

    String[] alphabet = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l"
            , "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);
        search_value = getIntent().getStringExtra("letter");

        listView = findViewById(R.id.listView);
        myDBHelper = new Connection(this);

        loadData();


    }


    public void loadData() {
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor;

        cursor = myDBHelper.displayData(search_value);


        if (cursor.getCount() == 0) {

            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        } else {

            while (cursor.moveToNext()) {

                list.add(cursor.getString(1) + ":\t" + cursor.getString(2));

            }

        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.tv, list);
        listView.setAdapter(adapter);


    }
}
