package com.example.capstoneplayground.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.capstoneplayground.DB.AppDatabase;
import com.example.capstoneplayground.speedResults;

import java.util.List;

@Dao
public interface speedResultsdao {
    @Insert
    void insert(speedResults... speedResult);
    @Update
    void update(speedResults... speedResult);
    @Delete
    void delete(speedResults... speedResult);
    @Query("SELECT * FROM "+ AppDatabase.RESULTS_TABLE + " ORDER BY down DESC")
    List<speedResults> getSpeedResults();
    @Query("SELECT * FROM "+ AppDatabase.RESULTS_TABLE + " WHERE id = :uid")
    speedResults getSpeedResultById(int uid);
}
