import android.util.Log
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

fun fetchRandomDogImageUrl(): String? {
    return try {
        val apiUrl = "https://dog.ceo/api/breeds/image/random"
        val connection = URL(apiUrl).openConnection() as HttpURLConnection
        connection.requestMethod = "GET"

        if (connection.responseCode == HttpURLConnection.HTTP_OK) {
            val response = connection.inputStream.bufferedReader().use { it.readText() }
            Log.d("DogImage", "API Response: $response")
            val json = JSONObject(response)
            json.getString("message")
        } else {
            Log.e("DogImage", "Error: API response code ${connection.responseCode}")
            null
        }
    } catch (e: Exception) {
        Log.e("DogImage", "Exception: ${e.message}", e)
        null
    }
}
