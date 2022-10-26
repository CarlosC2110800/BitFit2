package com.example.bitfit2

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import androidx.room.Entity

@Entity(tableName = "exercise_table")
data class ExerciseSql(
    @PrimaryKey(autoGenerate = true) val id: Long = 0, // Don't really need to worry of id
    @ColumnInfo(name = "exerciseDone") val exerciseComplete: String?,
    @ColumnInfo(name = "exerciseTime") val exerciseTiming: Double?
)
