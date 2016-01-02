package com.mohak.notessql;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import java.util.ArrayList;

public class Addnote extends AppCompatActivity {

    RecyclerView recyclerView;
    private SQLiteDatabase db;
    private String strings;
    private ArrayList<String> arrayList;
     private sql s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnote);
        SharedPreferences preferences = getSharedPreferences("Val", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (preferences.getBoolean("ok", true)) {
            startActivity(new Intent(Addnote.this, Addnote2.class));
            editor.putBoolean("ok", false);
            editor.apply();
        } else {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle("Notes using Sql");
            final EditText editText = (EditText) findViewById(R.id.edittex);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    search(charSequence,db);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            s = new sql(Addnote.this, sql.DATABASE_NAME, null, sql.DATABASE_VERSION);
            db = s.getWritableDatabase();
            add();
        }


    }

    private void search(CharSequence val, SQLiteDatabase db) {
        String[] arr = {sql.UID, sql.NAME};
        Cursor cursor = db.query(sql.TABLENAME, arr, null, null, null, null, null);
        ArrayList<String> string = new ArrayList<>();
        while (cursor.moveToNext()) {
            int i = cursor.getInt(cursor.getColumnIndex(sql.UID));
            String name = cursor.getString(cursor.getColumnIndex(sql.NAME));
            if (name.toLowerCase().contains(val.toString().toLowerCase()))
                string.add(name);
        }
        cursor.close();
        Adapter dap = new Adapter(this, string);
        recyclerView.setAdapter(dap);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void add() {
        Intent i = getIntent();
        strings = i.getStringExtra("key");
        Log.d("val", "" + strings);
        if (strings != null) {
            ContentValues values = new ContentValues();
            values.put(sql.NAME, strings);
            long x = db.insert(sql.TABLENAME, null, values);
            if (x < 0)
                Toast.makeText(Addnote.this, "ERROR", Toast.LENGTH_SHORT).show();
            else if (x > 0)
                Toast.makeText(Addnote.this, "ADDED", Toast.LENGTH_SHORT).show();
            display();

        } else {
            display();
        }

    }

    private void display() {

        String[] arr = {sql.UID, sql.NAME};
        Cursor cursor = db.query(sql.TABLENAME, arr, null, null, null, null, null);
        arrayList = new ArrayList<>();
        while (cursor.moveToNext()) {
            int i = cursor.getInt(cursor.getColumnIndex(sql.UID));
            String name = cursor.getString(cursor.getColumnIndex(sql.NAME));
            arrayList.add(name);
        }
        recyclerView = (RecyclerView) findViewById(R.id.rec);
        recyclerView.setAdapter(new Adapter(this, arrayList));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cursor.close();

    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_addnote, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.next) {
            startActivity(new Intent(Addnote.this, Addnote2.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
