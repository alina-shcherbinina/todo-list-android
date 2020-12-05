package com.example.shoppinglist;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText valueText, artist, title, date1;
    TextView sumText, lastvalueText;
    SQLiteDatabase db;
    DBHelper dbHelper;
    SQLiteDatabase todoDB;
    ListView todolist; // не забудьте привязать переменную (findViewById)
    SimpleCursorAdapter adapter;

    Cursor tunes;
    String[] playlist_fields;
    int[] views = { R.id.id, R.id.artist, R.id.title, R.id.date1 };

    TextView total;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todolist = findViewById(R.id.todolist);
        artist = findViewById(R.id.year);
        title  = findViewById(R.id.title);
        date1  = findViewById(R.id.date1);

        total = findViewById(R.id.total);

        DBHelper helper = new DBHelper(this);
        todoDB = helper.getWritableDatabase();

        tunes = todoDB.rawQuery("SELECT * FROM playlist", null);

        playlist_fields = tunes.getColumnNames();

        adapter = new SimpleCursorAdapter(this, R.layout.todo_item, tunes, playlist_fields, views, 0 );
        todolist.setAdapter(adapter);


        total.setText("In total: " + Integer.valueOf(todolist.getAdapter().getCount()));

    }


    public void onClick(View v)
    {
        String artist1 = artist.getText().toString();
        String title1 = title.getText().toString();
        String date_s = date1.getText().toString();
    // INSERT INTO table (column1,column2 ,..)
    //VALUES( value1,	value2 ,...);
        ContentValues cv = new ContentValues();
        cv.put("title", title1);
        cv.put("artist", artist1);
        cv.put("date1", date_s);
        todoDB.insert(dbHelper.TABLE_NAME, null, cv);

        tunes = todoDB.rawQuery("SELECT * FROM playlist", null);

        adapter = new SimpleCursorAdapter(this, R.layout.todo_item, tunes, playlist_fields, views, 0 );
        todolist.setAdapter(adapter);

        total.setText("In total: " + Integer.valueOf(todolist.getAdapter().getCount()));
    }

}