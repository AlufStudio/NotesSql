package com.mohak.notessql;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by mohak on 27/12/15.
 */
public class sql extends SQLiteOpenHelper {

    static final String TABLENAME = "MOHAK";
    static final String DATABASE_NAME = "mohakdatabase";
    static final int DATABASE_VERSION=1;
    private  Context context;
    static final String UID = "_id";
    static final String NAME = "Name";
    private static final String CREATE_TABLE="CREATE TABLE MOHAK ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR(255));";

    public sql(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        try {
            sqLiteDatabase.execSQL(CREATE_TABLE);
            Toast.makeText(context,"Created",Toast.LENGTH_SHORT).show();
        }catch (SQLException e)
        {
            Toast.makeText(context,""+e,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldvr, int newvr) {

    }
}
