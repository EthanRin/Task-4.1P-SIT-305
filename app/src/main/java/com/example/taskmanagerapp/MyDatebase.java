package com.example.taskmanagerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Date;

public class MyDatebase extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "TaskManager.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "task_lists";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "task_title";
    private static final String COLUMN_DESCRIPTION = "task_desc";
    private static final String COLUMN_DATE = "task_date";
    MyDatebase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =  "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "  +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_DATE + " TEXT DEFAULT (strftime('%Y-%m-%d', 'now')))";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addTask(String title, String description, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_DATE, date);
        long result = db.insert(TABLE_NAME, null, values);
        if(result == -1){
            Toast.makeText(context, "Insert Failed", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Insert Successful", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateData(String row_id, String title, String description, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_DATE, date);
        long result= db.update(TABLE_NAME, values, "_id = ?", new String[]{row_id});
        Log.i("Result", String.valueOf(result));
        Log.i("Id:", row_id);
        Log.i("Title:", COLUMN_TITLE);
        Log.i("Title:", title);
        if (result == -1) {
            Toast.makeText(context, "Update Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Update Successful", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id = ?", new String[]{row_id});
        if (result == -1){
            Toast.makeText(context, "Delete Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Delete Successful", Toast.LENGTH_SHORT).show();
        }
    }
}
