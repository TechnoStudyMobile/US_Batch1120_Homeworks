package ustun.muharrem.weatherforecast.utilities

// Backend related
const val API_KEY = "4851d18a868d4d029f16f37d0962382e"
// TODO: Remove API Key before uploading the final version to Github!!! And warn in the README file.
const val METRIC_QUERY_PARAM_VALUE = "M"
const val IMPERIAL_QUERY_PARAM_VALUE = "I"
const val RESPONSE_PARSING_ERROR_MESSAGE = "Backend forecast parsing was unsuccessful!"

// Shared Preferences related
const val SHARED_PREFERENCES_NAME = "shared preference name"
const val IS_CELSIUS_DEFAULT_VALUE = true
const val IS_CELSIUS_SETTING_KEY = "is metric setting key"
const val NUMBER_OF_DAYS_SETTING_KEY = "number of days setting key"
const val IS_NOTIFICATIONS_ENABLED = "is notifications enabled"
const val LANG_CODE_SETTINGS_KEY = "language settings key"
const val LAST_EPOCH_TIME_KEY = "last epoch time key"
const val LONGITUDE_KEY = "longitude key"
const val LATITUDE_KEY = "latitude key"

//Room Database related
const val FORECAST_ROOM_DATABASE_NAME = "forecast database"
const val THREE_HOUR_EPOCH_TIME: Long = 3 * 60 * 60 * 1000
const val DB_IS_EMPTY_MESSAGE = "No saved forecast container in the database"

// Location related
const val ONE_KM_DISTANCE: Float = 1000f

// General
const val MY_LOG = "WeatherApp"