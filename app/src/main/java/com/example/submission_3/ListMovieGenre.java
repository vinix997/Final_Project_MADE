package com.example.submission_3;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "Genre")
public class ListMovieGenre implements Parcelable {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private Integer id;
    @ColumnInfo(name = "name")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
    }

    public ListMovieGenre() {
    }

    protected ListMovieGenre(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
    }

    public static final Parcelable.Creator<ListMovieGenre> CREATOR = new Parcelable.Creator<ListMovieGenre>() {
        @Override
        public ListMovieGenre createFromParcel(Parcel source) {
            return new ListMovieGenre(source);
        }

        @Override
        public ListMovieGenre[] newArray(int size) {
            return new ListMovieGenre[size];
        }
    };
}
