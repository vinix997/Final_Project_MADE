package com.example.submission_3.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import com.example.submission_3.moviespackage.Movie;

import java.util.List;

@Dao
public interface MovieDAO {
    @Insert
    long addMovie(Movie movie);

    @Delete
    int delMovie(Movie movie);

    @Update
    int updateMovie(Movie movie);

    @Query("SELECT * FROM Movie WHERE id = :id")
    Movie findMovieById(int id);

    @Query("SELECT * FROM Movie")
    Cursor getFavouriteMovieCursor();

    @Query("SELECT * FROM Movie")
    List<Movie> getFavouriteMovieList();

    @Query("DELETE FROM Movie WHERE id = :id")
    int deleteById(long id);
}
