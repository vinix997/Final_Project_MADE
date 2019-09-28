package com.example.submission_3.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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
