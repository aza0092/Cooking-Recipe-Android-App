package dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import models.Recipe;

/**
 * Created by Abdullah Alali ZOUAG on 4/28/2018.
 */
public class RecipeDAO {

    private SQLiteDatabase db;

    private IngredientDAO ingredientDAO;
    private DirectionDAO directionDAO;

    public RecipeDAO(SQLiteDatabase db) {
        this.db = db;

        ingredientDAO = new IngredientDAO(db);
        directionDAO = new DirectionDAO(db);
    }

    public long insert(Recipe recipe) {
        if (recipe.getIngredients() == null || recipe.getDirections() == null)
            throw new IllegalStateException("Cannot insert recipe: the recipe is incomplete.");

        long newRecipeId = insert(recipe.getName(), recipe.getCategory(), recipe.getDescription(), recipe.getImagePath());
        Log.i("DAO", "Inserted new recipe : " + newRecipeId);
        Log.i("DAO", "New recipe has " + recipe.getDirections().size() + " directions.");
        recipe.getIngredients()
                .forEach(ingredient -> {
                    ingredient.setRecipeId(newRecipeId);
                    ingredientDAO.insert(ingredient);
                    Log.i("DAO", "Inserted " + ingredient);
                });
        recipe.getDirections()
                .forEach(direction -> {
                    direction.setRecipeId(newRecipeId);
                    directionDAO.insert(direction);
                    Log.i("DAO", "Inserted " + direction);
                });

        return newRecipeId;
    }

    private long insert(String name, String category, String description, String imagePath) {
        ContentValues values = new ContentValues();
        values.put(Config.KEY_NAME, name);
        values.put(Config.KEY_CATEGORY, category);
        values.put(Config.KEY_DESCRIPTION, description);
        values.put(Config.KEY_IMAGE_PATH, imagePath);
        return db.insert(Config.TABLE_NAME, null, values);
    }

    public List<Recipe> selectAllByCategory(String category) {
        List<Recipe> recipes = new ArrayList<>();
        try (Cursor cursor = db.query(Config.TABLE_NAME,
                new String[]{Config.KEY_ID, Config.KEY_NAME, Config.KEY_CATEGORY, Config.KEY_DESCRIPTION, Config.KEY_IMAGE_PATH},
                Config.KEY_CATEGORY + " = ?", new String[]{category}, null, null, null)) {
            if (cursor.moveToFirst()) {
                do {
                    Recipe recipe = new Recipe(
                            cursor.getLong(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4));
                    recipe.setIngredients(ingredientDAO.selectAllByRecipeId(recipe.getId()));
                    recipe.setDirections(directionDAO.selectAllByRecipeId(recipe.getId()));
                    recipes.add(recipe);

                } while (cursor.moveToNext());
            }
        }

        Log.i("DAO", "RecipeDAO returning: " + recipes);
        return recipes;
    }

    public void update(Recipe recipe) {
        ingredientDAO.deleteAllByRecipeId(recipe.getId());
        directionDAO.deleteAllByRecipeId(recipe.getId());
        recipe.getIngredients()
                .forEach(ingredient -> {
                    ingredient.setRecipeId(recipe.getId());
                    ingredientDAO.insert(ingredient);
                });
        recipe.getDirections()
                .forEach(direction -> {
                    direction.setRecipeId(recipe.getId());
                    directionDAO.insert(direction);
                });

        ContentValues values = new ContentValues();
        values.put(Config.KEY_NAME, recipe.getName());
        values.put(Config.KEY_CATEGORY, recipe.getCategory());
        values.put(Config.KEY_DESCRIPTION, recipe.getDescription());
        values.put(Config.KEY_IMAGE_PATH, recipe.getImagePath());
        db.update(Config.TABLE_NAME, values, Config.KEY_ID + "=" + recipe.getId(), null);
    }

    public boolean deleteById(long id) {
        ingredientDAO.deleteAllByRecipeId(id);
        directionDAO.deleteAllByRecipeId(id);
        return db.delete(Config.TABLE_NAME, Config.KEY_ID + "=" + id, null) > 0;
    }

    public static class Config {
        public static final String TABLE_NAME = "recipes";
        public static final String KEY_ID = "id";
        public static final String KEY_NAME = "name";
        public static final String KEY_CATEGORY = "category";
        public static final String KEY_DESCRIPTION = "description";
        public static final String KEY_IMAGE_PATH = "imagePath";

        public static final String CREATE_TABLE_STATEMENT =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        KEY_NAME + " TEXT NOT NULL, " +
                        KEY_CATEGORY + " TEXT NOT NULL, " +
                        KEY_DESCRIPTION + " TEXT NOT NULL, " +
                        KEY_IMAGE_PATH + " TEXT NOT NULL)";
    }
}
