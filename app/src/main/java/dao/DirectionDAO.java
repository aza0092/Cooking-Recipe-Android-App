package dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import models.Direction;

/**
 * Created by Abdullah Alali ZOUAG on 4/28/2018.
 */
public class DirectionDAO {

    private SQLiteDatabase db;

    public DirectionDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public void insert(Direction direction) {
        insert(direction.getBody(), direction.getRecipeId());
    }

    public void insert(String direction, long recipeId) {
        ContentValues values = new ContentValues();
        values.put(Config.KEY_BODY, direction);
        values.put(Config.KEY_RECIPE_ID, recipeId);
        db.insert(Config.TABLE_NAME, null, values);
    }

    public List<Direction> selectAllByRecipeId(long recipeId) {
        List<Direction> directions = new ArrayList<>();
        try (Cursor cursor = db.query(Config.TABLE_NAME,
                new String[]{Config.KEY_ID, Config.KEY_BODY, Config.KEY_RECIPE_ID},
                Config.KEY_RECIPE_ID + " = ?", new String[]{recipeId + ""}, null, null, null)) {
            if (cursor.moveToFirst()) {
                do {
                    directions.add(new Direction(
                            cursor.getLong(0), cursor.getString(1), cursor.getLong(2)));
                } while (cursor.moveToNext());
            }
        }

        Log.i("DAO", "DirectionDAO returning: " + directions + " for recipe ID: " + recipeId);
        return directions;
    }

    public boolean deleteAllByRecipeId(long recipeId) {
        return db.delete(Config.TABLE_NAME, Config.KEY_RECIPE_ID + "=" + recipeId, null) > 0;
    }

    public static class Config {
        public static final String TABLE_NAME = "directions";
        public static final String KEY_ID = "id";
        public static final String KEY_BODY = "body";
        public static final String KEY_RECIPE_ID = "recipeId";

        public static final String CREATE_TABLE_STATEMENT =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        KEY_BODY + " TEXT NOT NULL, " +
                        KEY_RECIPE_ID + " TEXT NOT NULL, " +
                        "FOREIGN KEY(" + KEY_RECIPE_ID + ") REFERENCES " + RecipeDAO.Config.TABLE_NAME + "(" + RecipeDAO.Config.KEY_ID + "))";
    }
}
