package com.learn.ranjitsihota.popularmovies;
import android.database.sqlite.*;
import android.content.Context;
import android.content.ContentValues;
import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;


/*
 *
 *
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "movies";

    // Contacts table name
    private static final String TABLE_CONTACTS = "favorites";

    // Contacts Table Columns names
    private static final String MOVIE_ID = "MOVIE_ID";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + MOVIE_ID + " INTEGER"  + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    void addFavorite(Favorite favorite) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MOVIE_ID, favorite.getID());
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }


    public ArrayList<Favorite> getAllFavorites() {
        ArrayList<Favorite> contactList = new ArrayList<Favorite>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Favorite contact = new Favorite();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    public void deleteFavorite(Favorite favorite) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, MOVIE_ID + " = ?",
                new String[] { String.valueOf(favorite.getID()) });
        db.close();

    }
}
