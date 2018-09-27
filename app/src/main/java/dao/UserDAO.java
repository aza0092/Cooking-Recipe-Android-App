package dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import adapters.DatabaseAdapter;
import models.Ingredient;
import models.User;

public class UserDAO {

    private SQLiteDatabase db;

    public UserDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public void insert(User user) {
        ContentValues values = new ContentValues();
        values.put(Config.KEY_USERNAME, user.getUsername());
        values.put(Config.KEY_FULLNAME, user.getFullname());
        values.put(Config.KEY_EMAIL, user.getEmail());
        values.put(Config.KEY_PASSWORD, user.getPassword());

        insert(values);
    }

    private long insert(ContentValues values) {
        return db.insert(Config.TABLE_NAME, null, values);
    }

    public User getUserByEmailAndPassword(String email, String password) {
        try (Cursor cursor = db.query(
                true,
                Config.TABLE_NAME,
                new String[]{Config.KEY_ID, Config.KEY_USERNAME, Config.KEY_FULLNAME, Config.KEY_EMAIL, Config.KEY_PASSWORD},
                Config.KEY_EMAIL + "=? AND " + Config.KEY_PASSWORD + "=?",
                new String[]{email, password},
                null, null, null, null)) {
            return extractUserFromCursor(cursor);
        }
    }

    private User extractUserFromCursor(Cursor cursor) {
        if (cursor.moveToFirst()) {
            return new User(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
            );
        }

        return null;
    }

    public static class Config {
        public static final String TABLE_NAME = "users";
        public static final String KEY_ID = "id";
        public static final String KEY_USERNAME = "username";
        public static final String KEY_FULLNAME = "fullname";
        public static final String KEY_EMAIL = "email";
        public static final String KEY_PASSWORD = "password";

        public static final String CREATE_TABLE_STATEMENT =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        KEY_USERNAME + " TEXT NOT NULL, " +
                        KEY_FULLNAME + " TEXT NOT NULL, " +
                        KEY_EMAIL + " TEXT NOT NULL, " +
                        KEY_PASSWORD + " TEXT NOT NULL)";
    }
}
