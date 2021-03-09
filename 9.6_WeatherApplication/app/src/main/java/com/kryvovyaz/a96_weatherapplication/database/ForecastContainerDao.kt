package com.kryvovyaz.a96_weatherapplication.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kryvovyaz.a96_weatherapplication.model.ForecastContainer

@Dao
interface ForecastContainerDao {
    @Query("SELECT * FROM forecastContainerTable LIMIT 1") // LIMIT 1
    fun getForecastContainer() : LiveData<ForecastContainer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(forecastContainer: ForecastContainer)

    @Query("DELETE FROM forecastContainerTable")
    fun deleteAll()
}