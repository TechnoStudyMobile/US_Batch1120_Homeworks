package ustun.muharrem.weatherforecast.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ustun.muharrem.weatherforecast.data.ForecastContainer
import ustun.muharrem.weatherforecast.utilities.FORECAST_ROOM_DATABASE_NAME

@Database(entities = [ForecastContainer::class], version = 1)
@TypeConverters(ForecastListConverters::class)
abstract class ForecastDatabase : RoomDatabase() {

    abstract fun getForecastContainerDao(): ForecastContainerDao

    companion object {
        @Volatile
        private var INSTANCE: ForecastDatabase? = null
        fun getDatabase(application: Application): ForecastDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    application.applicationContext,
                    ForecastDatabase::class.java,
                    FORECAST_ROOM_DATABASE_NAME
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}