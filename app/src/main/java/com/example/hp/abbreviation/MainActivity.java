package com.example.hp.abbreviation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Connection connection;

    ImageView[] imageViews = new ImageView[27];
    String[] alphabet = {"a", "b", "c", "d","e","f","g","h","i","j","k","l"
                        ,"m","n","o","p","q","r","s","t","u","v","w","x","y","z"};

    ImageView image;
    String search_value;
    EditText editText;

    MyDBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connection = new Connection(getApplicationContext());

        connection.createDataBase();
        connection.openDataBase();

        image = findViewById(R.id.searchImg);
        editText = findViewById(R.id.search);

        for (int i = 1; i <= 26; i++) {
            imageViews[i] = new ImageView(this);
            String imgID = "iv" + i;
            int resID = getResources().getIdentifier(imgID, "id", getPackageName());
            imageViews[i] = findViewById(resID);
            imageViews[i].setOnClickListener(this);
        }

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_value = editText.getText().toString().toUpperCase();
                if(search_value.equals("")){
                    Toast.makeText(MainActivity.this, "Please write something...", Toast.LENGTH_SHORT).show();
                }else{
                    startActivity(new Intent(MainActivity.this, DisplayData.class).putExtra("letter",search_value));
                }


            }
        });



    }


    @Override
    public void onClick(View v) {

        for (int i = 1; i <= 26; i++) {
            String imgID = "iv" + i;
            int resID = getResources().getIdentifier(imgID, "id", getPackageName());

            if (v.getId() == resID) {
                startActivity(new Intent(this,DisplayData.class).putExtra("letter",alphabet[i-1]));
            }

        }

    }


}
