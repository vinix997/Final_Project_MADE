package com.example.submission_3.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.submission_3.ListMovieGenre;
import com.example.submission_3.ListTVGenre;

import java.util.List;

@Dao
public interface GenreDAO {
    @Insert
    long addGenres(ListMovieGenre listMovieGenre);

    @Delete
    int delGenres(ListMovieGenre listMovieGenre);

    @Update
    int updateGenres(ListMovieGenre listMovieGenre);

    @Query("SELECT * FROM Genre")
    List<ListMovieGenre> getGenreList();

    @Query("SELECT * FROM Gen")
    List<ListTVGenre> getTvGenre();
}
