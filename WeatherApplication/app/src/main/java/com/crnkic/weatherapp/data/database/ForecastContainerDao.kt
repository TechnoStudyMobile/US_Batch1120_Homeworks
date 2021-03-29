package com.crnkic.weatherapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.crnkic.weatherapp.data.model.ForecastContainer

@Dao
interface ForecastContainerDao {
    @Query("SELECT * FROM forecastContainerTable LIMIT 1")
    fun getForecastContainer() : ForecastContainer?
//    fun getForecastContainer() : LiveData<ForecastContainer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(forecastContainer: ForecastContainer)

    @Query("DELETE FROM forecastContainerTable")
    fun deleteAll()
}