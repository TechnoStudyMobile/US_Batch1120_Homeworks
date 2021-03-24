package ustun.muharrem.weatherforecast.utilities

import ustun.muharrem.weatherforecast.R

object DrawableManager {
    inline fun <reified T : Class<R.drawable>> T.getImageId(resourceName: String): Int {
        return try {
            val idField = getDeclaredField(resourceName)
            idField.getInt(idField)
        } catch (e: Exception) {
            e.printStackTrace()
            -1
        }
    }
}