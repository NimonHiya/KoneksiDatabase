package com.example.koneksidatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "biodatadiri.db";
    private static final int DATABASE_VERSION = 1;

    // Tabel Biodata
    private static final String TABLE_BIODATA = "biodata";
    private static final String COLUMN_NO = "no";
    private static final String COLUMN_NAMA = "nama";
    private static final String COLUMN_TGL = "tgl";
    private static final String COLUMN_JK = "jk";
    private static final String COLUMN_ALAMAT = "alamat";

    // Tabel Users
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createBiodataTable = "CREATE TABLE " + TABLE_BIODATA + " (" +
                COLUMN_NO + " INTEGER PRIMARY KEY, " +
                COLUMN_NAMA + " TEXT, " +
                COLUMN_TGL + " TEXT, " +
                COLUMN_JK + " TEXT, " +
                COLUMN_ALAMAT + " TEXT);";

        String createUsersTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_USERNAME + " TEXT PRIMARY KEY, " +
                COLUMN_PASSWORD + " TEXT);";

        Log.d("Data", "onCreate: " + createBiodataTable);
        Log.d("Data", "onCreate: " + createUsersTable);

        // Create tables
        db.execSQL(createBiodataTable);
        db.execSQL(createUsersTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BIODATA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // Fungsi untuk melakukan registrasi
    public boolean register(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);

        // Menyimpan data ke dalam tabel users
        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result != -1; // Jika insert berhasil, result > -1
    }

    // Fungsi untuk login
    public boolean login(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " +
                COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});
        boolean isUserValid = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return isUserValid;
    }
}
