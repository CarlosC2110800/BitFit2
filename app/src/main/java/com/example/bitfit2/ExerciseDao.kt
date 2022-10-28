package com.example.bitfit2

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Query("SELECT * FROM exercise_table")
    fun getAll(): Flow<List<ExerciseSql>>

    @Insert
    fun insertAll(exercises: List<ExerciseSql>)  // Add multiple in one go

    @Insert
    fun insert(exercise: ExerciseSql)  // If we are adding one roll at a time

    @Query("DELETE FROM exercise_table")
    fun deleteAll()
}