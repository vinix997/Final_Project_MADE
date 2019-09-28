package com.example.submission_3.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.submission_3.ListMovieGenre;
import com.example.submission_3.ListTVGenre;
import com.example.submission_3.dao.GenreDAO;
import com.example.submission_3.dao.MovieDAO;
import com.example.submission_3.dao.TvDAO;
import com.example.submission_3.moviespackage.Movie;
import com.example.submission_3.tvshowpackage.TVShow;

@Database(entities = {Movie.class, TVShow.class, ListMovieGenre.class, ListTVGenre.class}, version = 100, exportSchema = false)
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

    public abstract MovieDAO movieDao();

    public abstract TvDAO tvDao();

    public abstract GenreDAO genreDao();

}
