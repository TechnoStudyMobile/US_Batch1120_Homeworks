package com.crnkic.weatherapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.crnkic.weatherapp.model.ForecastContainer
import kotlinx.coroutines.flow.Flow

@Dao
interface ForecastContainerDao {
    @Query("SELECT * FROM forecastContainerTable LIMIT 1")
    fun getForecastContainer() : ForecastContainer
//    fun getForecastContainer() : LiveData<ForecastContainer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(forecastContainer: ForecastContainer)

    @Query("DELETE FROM forecastContainerTable")
    fun deleteAll()
}