package com.example.submission_3.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.submission_3.tvshowpackage.TVShow;

import java.util.List;

@Dao
public interface TvDAO {
    @Query("SELECT * FROM TVShow WHERE id = :id")
    TVShow findTvById(int id);

    @Insert
    long addTv(TVShow tvShow);

    @Delete
    int delTv(TVShow tvShow);

    @Query("SELECT * FROM TVShow")
    List<TVShow> getFavouriteTvList();
}
