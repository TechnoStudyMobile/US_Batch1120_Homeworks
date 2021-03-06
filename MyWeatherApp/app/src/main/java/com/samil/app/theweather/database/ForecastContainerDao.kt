package com.samil.app.theweather.database

import androidx.room.*
import com.samil.app.theweather.model.ForecastContainer

@Dao
interface ForecastContainerDao {

    @Query("SELECT * FROM forecastContainers")
    //@Query("SELECT * FROM forecastContainers ORDER BY id DESC LIMIT 1 ")
    fun getForecastContainer(): List<ForecastContainer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(forecastContainer: ForecastContainer)

    @Query("DELETE FROM forecastContainers")
    fun deleteAll()
}