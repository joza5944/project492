/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 * */
package com.example.project.projectapp.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {

	private static final String TAG = SQLiteHandler.class.getSimpleName();

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "project";

	// Login table name
	private static final String TABLE_USER = "user_data";

	// Login Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name_user";
	private static final String KEY_EMAIL = "email_user";
	private static final String KEY_PASSWORD = "passwoed";
	private static final String KEY_SEX = "sex_user";
	private static final String KEY_FIST = "fist_user";
	private static final String KEY_LAST = "last_user";
	private static final String KEY_CAREER = "career";
	private static final String KEY_UID = "uid";
	private static final String KEY_CREATED_AT = "created_at";

	public SQLiteHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
				+ KEY_EMAIL + " TEXT UNIQUE," + KEY_UID + " TEXT,"
				+ KEY_PASSWORD +"TEXT," +KEY_FIST +"TEXT," +KEY_LAST +"TEXT"
				+ KEY_SEX +"TEXT" +KEY_CAREER +"TEXT"
				+ KEY_CREATED_AT + " TEXT" + ")";
		db.execSQL(CREATE_LOGIN_TABLE);

		Log.d(TAG, "Database tables created");
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

		// Create tables again
		onCreate(db);
	}

	/**
	 * Storing user details in database
	 * */
	public void addUser(String name_user, String email_user, String uid, String created_at ,String passwoed ,String fist_user ,
						String last_user , String sex_user , String created) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, name_user); // Name
		values.put(KEY_EMAIL, email_user); // Email
		values.put(KEY_PASSWORD, passwoed);//password
		values.put(KEY_FIST, fist_user);//fist
		values.put(KEY_LAST, last_user);//last
		values.put(KEY_SEX, sex_user);//sex
		values.put(KEY_CAREER, created);//created
		values.put(KEY_UID, uid); // Email
		values.put(KEY_CREATED_AT, created_at); // Created At

		// Inserting Row
		long id = db.insert(TABLE_USER, null, values);
		db.close(); // Closing database connection

		Log.d(TAG, "New user inserted into sqlite: " + id);
	}

	/**
	 * Getting user data from database
	 * */
	public HashMap<String, String> getUserDetails() {
		HashMap<String, String> user = new HashMap<String, String>();
		String selectQuery = "SELECT  * FROM " + TABLE_USER;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {
			user.put("name_user", cursor.getString(1));
			user.put("email_user", cursor.getString(2));
			user.put("password", cursor.getString(3));
			user.put("fist_user", cursor.getString(4));
			user.put("last_user", cursor.getString(5));
			user.put("sex_user", cursor.getString(6));
			user.put("created", cursor.getString(7));
			user.put("uid", cursor.getString(8));
			user.put("created_at", cursor.getString(9));
		}
		cursor.close();
		db.close();
		// return user
		Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

		return user;
	}

	/**
	 * Re crate database Delete all tables and create them again
	 * */
	public void deleteUsers() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(TABLE_USER, null, null);
		db.close();

		Log.d(TAG, "Deleted all user info from sqlite");
	}

}
