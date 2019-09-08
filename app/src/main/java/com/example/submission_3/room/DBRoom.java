package com.example.submission_3.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.submission_3.dao.Moviedao;
import com.example.submission_3.dao.TVdao;
import com.example.submission_3.moviespackage.Movie;
import com.example.submission_3.tvshowpackage.TVShow;

@Database(entities = {Movie.class, TVShow.class}, version = 2, exportSchema = false)
public abstract class DBRoom extends RoomDatabase {
    private static DBRoom dbRoom;

    public static DBRoom getInstance(Context context) {
        if (dbRoom == null) {
            dbRoom = Room.databaseBuilder(context, DBRoom.class, "db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return dbRoom;
    }

    public abstract Moviedao movieDao();

    public abstract TVdao tvDao();


}
