package com.example.facom_notes.database;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.facom_notes.User;

import java.util.ArrayList;

public class DBHelperUser extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "user.db";
    private static final String TABLE_NAME = "user";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_EMAIL = "email";
    private static final String COL_USER = "user";
    private static final String COL_PASS = "password";
    SQLiteDatabase db;
    private static final String TABLE_CREATE="create table "+TABLE_NAME+
            "("+COL_ID+" integer primary key autoincrement, "+
            COL_NAME+" text not null, "+COL_EMAIL+" text not null, " +
            COL_USER+" text not null, "+COL_PASS+" text not null);";

    public DBHelperUser(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db = db;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion) {
        String query = "DROP TABLE IF EXISTS "+TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }
    public void insertUser(User u){
        db = this.getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(COL_NAME, u.getName());
            values.put(COL_EMAIL, u.getEmail());
            values.put(COL_USER,u.getUser());
            values.put(COL_PASS, u.getPassword());
            db.insertOrThrow(TABLE_NAME,null,values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
    }
    public String getPass(String user){
        db = this.getReadableDatabase();
        String query = String.format("SELECT %s FROM %s WHERE %s = ?",
                COL_PASS, TABLE_NAME, COL_USER);
        String passWord = "não encontrado";
        db.beginTransaction();
        try {
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(user)});
            try {
                if (cursor.moveToFirst()) {
                    passWord = cursor.getString(0);
                    db.setTransactionSuccessful();
                }
            } finally {
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
            }
        }catch (Exception e) {
            Log.d(TAG, "Error while trying to add or update user");
        } finally {
            db.endTransaction();
        }
        return passWord;
    }
    public ArrayList<User> searchUsers(){
        String[] coluns={ COL_ID, COL_NAME,COL_EMAIL,COL_USER, COL_PASS};
        Cursor cursor = getReadableDatabase().query(TABLE_NAME,
                coluns,null,null,null,
                null,"upper(nome)",null);
        ArrayList<User> listUser = new ArrayList<User>();
        while(cursor.moveToNext()){
            User u = new User();
            u.setId(cursor.getInt(0));
            u.setName(cursor.getString(1));
            u.setEmail(cursor.getString(2));
            u.setUser(cursor.getString(3));
            u.setPassword(cursor.getString(4));
            listUser.add(u);
        }
        return listUser;
    }
    public long deleteUser(User user) {
        long retornoBD;
        db = this.getWritableDatabase();
        String[] args = {String.valueOf(user.getId())};
        retornoBD=db.delete(TABLE_NAME, COL_ID+"=?",args);
        return retornoBD;
    }
    public long updateUser(User u){
        long retornoBD;
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME,u.getName());
        values.put(COL_EMAIL,u.getEmail());
        values.put(COL_USER, u.getUser());
        values.put(COL_PASS,u.getPassword());
        String[] args = {String.valueOf(u.getId())};
        retornoBD=db.update(TABLE_NAME,values,"id=?",args);
        db.close();
        return retornoBD;
    }
}