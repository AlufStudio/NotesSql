package com.mohak.notessql;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Addnote3 extends AppCompatActivity {

    private String val;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnote3);
        Button button = (Button) findViewById(R.id.button2);
        Button button2 = (Button) findViewById(R.id.button3);
        final EditText editText = (EditText) findViewById(R.id.editme);
        Intent intent = getIntent();
        val = intent.getStringExtra("val");
        editText.setText(val);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sql s = new sql(Addnote3.this, sql.DATABASE_NAME, null, sql.DATABASE_VERSION);
                SQLiteDatabase sqLiteDatabase = s.getWritableDatabase();
                sqLiteDatabase.delete(sql.TABLENAME, sql.NAME + "=?", new String[]{val});
                Toast.makeText(Addnote3.this, "Deletion success", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Addnote3.this, Addnote.class));
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newval = editText.getText().toString();
                Log.d("newval",newval );
                sql s = new sql(Addnote3.this, sql.DATABASE_NAME, null, sql.DATABASE_VERSION);
                SQLiteDatabase sqLiteDatabase = s.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put(sql.NAME, newval);
                String [] x = {
                        val};
                sqLiteDatabase.update(sql.TABLENAME,contentValues,sql.NAME + "=?",x);
                Toast.makeText(Addnote3.this,"Update success",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Addnote3.this, Addnote.class));

            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Addnote3.this,Addnote.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_addnote3, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
