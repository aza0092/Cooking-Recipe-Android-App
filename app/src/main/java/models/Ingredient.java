package models;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {
    private long id;
    private String name;
    private long recipeId;

    public Ingredient(String name) {
        this.name = name;
    }

    public Ingredient(long id, String name, long recipeId) {
        this(name);
        this.id = id;
        this.recipeId = recipeId;
    }

    private Ingredient(Parcel in) {
        id = in.readLong();
        name = in.readString();
        recipeId = in.readLong();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getRecipeId() {
        return recipeId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRecipeId(long recipeId) {
        this.recipeId = recipeId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(name);
        parcel.writeLong(recipeId);
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {

        @Override
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", recipeId=" + recipeId +
                '}';
    }
}
