package com.example.submission_3.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.submission_3.tvshowpackage.TVShow;

import java.util.List;

@Dao
public interface TVdao {
    @Query("SELECT * FROM TVShow WHERE id = :id")
    TVShow findTvById(int id);

    @Insert
    long addTv(TVShow tvShow);

    @Delete
    int delTv(TVShow tvShow);

    @Query("SELECT * FROM TVShow")
    List<TVShow> getFavouriteTvList();
}
