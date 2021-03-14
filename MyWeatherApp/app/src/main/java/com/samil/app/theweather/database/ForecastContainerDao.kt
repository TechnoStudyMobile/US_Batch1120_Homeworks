package com.samil.app.theweather.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.samil.app.theweather.model.ForecastContainer

@Dao
interface ForecastContainerDao {

    @Query("SELECT * FROM forecastContainers LIMIT 1")
    fun getForecastContainer(): LiveData<ForecastContainer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(forecastContainer: ForecastContainer)

    @Query("DELETE FROM forecastContainers")
    fun deleteAll()
}