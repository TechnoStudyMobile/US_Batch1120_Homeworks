package ustun.muharrem.weatherforecast.utilities

import android.text.format.DateFormat
import ustun.muharrem.weatherforecast.utilities.SharedPrefs.application
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    fun getDateText(valid_date: String, position: Int): String {
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(valid_date)
        return when (position) {
            0 -> SimpleDateFormat("EEEE, MMMM d", Locale.getDefault()).format(date!!)
            in 1..6 -> SimpleDateFormat("EEEE", Locale.getDefault()).format(date!!)
            else -> SimpleDateFormat("EEE, MMM d", Locale.getDefault()).format(date!!)
        }
    }

    fun getTimeText(epochTime: Long): String {
        val is24HourFormat = DateFormat.is24HourFormat(application)
        val formatPattern = if (is24HourFormat) "H:mm" else "h:mm a"
        val time = Date(epochTime * 1000)
        return SimpleDateFormat(formatPattern, Locale.getDefault()).format(time)
    }
}