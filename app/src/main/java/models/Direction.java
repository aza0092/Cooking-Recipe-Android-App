package models;

import android.os.Parcel;
import android.os.Parcelable;

public class Direction implements Parcelable {
    private long id;
    private String body;
    private long recipeId;

    public Direction(String body) {
        this.body = body;
    }

    public Direction(long id, String body, long recipeId) {
        this.id = id;
        this.body = body;
        this.recipeId = recipeId;
    }

    private Direction(Parcel in) {
        id = in.readLong();
        body = in.readString();
        recipeId = in.readLong();
    }

    public long getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public long getRecipeId() {
        return recipeId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setBody(String body) {
        this.body = body;
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
        parcel.writeString(body);
        parcel.writeLong(recipeId);
    }

    public static final Creator<Direction> CREATOR = new Creator<Direction>() {

        @Override
        public Direction createFromParcel(Parcel source) {
            return new Direction(source);
        }

        @Override
        public Direction[] newArray(int size) {
            return new Direction[size];
        }
    };

    @Override
    public String toString() {
        return "Direction{" +
                "id=" + id +
                ", body='" + body + '\'' +
                ", recipeId=" + recipeId +
                '}';
    }
}
