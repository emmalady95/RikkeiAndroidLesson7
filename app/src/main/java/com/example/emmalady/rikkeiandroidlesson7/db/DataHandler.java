package com.example.emmalady.rikkeiandroidlesson7.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.emmalady.rikkeiandroidlesson7.model.Contact;

import java.util.ArrayList;

/**
 * Created by Emma Nguyen on 27/10/2017.
 */

public class DataHandler extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ContactManager3.db";
    public static final String DATABASE_TABLE_NAME = "Contact";
    public static final String CONTACT_COLUMN_ID = "id";
    public static final String CONTACT_COLUMN_NAME = "contactName";
    public static final String CONTACT_COLUMN_NUMBER = "contactNumber";

    public static final String CREATE_TABLE = "CREATE TABLE " + DATABASE_TABLE_NAME +" ( "
            + CONTACT_COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + CONTACT_COLUMN_NAME +" TEXT, "
            + CONTACT_COLUMN_NUMBER +" INTEGER)";

    public DataHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public boolean insertData(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACT_COLUMN_NAME, contact.getContactName());
        contentValues.put(CONTACT_COLUMN_NUMBER,contact.getContactNumber());
        db.insert(DATABASE_TABLE_NAME, null, contentValues);
        return true;
    }

    public ArrayList<Contact> getAllData(){
        ArrayList<Contact> contactList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + DATABASE_TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false){
            int id = cursor.getInt(cursor.getColumnIndex(CONTACT_COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(CONTACT_COLUMN_NAME));
            int number = cursor.getInt(cursor.getColumnIndex(CONTACT_COLUMN_NUMBER));

            Contact contact = new Contact(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
            contactList.add(contact);
            cursor.moveToNext();
        }
        return contactList;
    }

    public boolean deleteData(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + DATABASE_TABLE_NAME +" WHERE id = " + id;
        db.execSQL(query);
        return true;
    }

    public boolean updateData(int id, String name, int number){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + DATABASE_TABLE_NAME +" SET id = " + id +
                " contactName = " + name +
                " contactNumber " + number +
                " WHERE id = " + id ;
        db.execSQL(query);
        return true;
    }

}

