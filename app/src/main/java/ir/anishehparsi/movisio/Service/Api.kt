package ir.anishehparsi.movisio.Service


import android.util.Log

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ir.anishehparsi.movisio.Model.ConfigResponse
import ir.anishehparsi.movisio.Model.Images
import ir.anishehparsi.movisio.Model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request


suspend fun getMovieData(): List<Result>? {
    return withContext(Dispatchers.IO) {

        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://api.themoviedb.org/3/movie/upcoming?")
            .get()
            .addHeader("accept", "application/json")
            .addHeader(
                "Authorization",
                "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI5YjE2Yzc3M2RiZmRhNDVmMjYyYTVmY2JkMTdkYjk0NCIsIm5iZiI6MTc0MjczODU3MC41MDEsInN1YiI6IjY3ZTAxNDhhMzBlNjVlN2JlZWM3MWZhMiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.H1c82i0f2Bo-5iJt2VgmkTjOn0WcLQmS8CJbOfF7GBc"
            )
            .build()

        val response = client.newCall(request).execute()


        val json = response.body?.string()
        Log.d("API Response", json ?: "Response is null")

        val jsonObject: Map<String, Any> =
            Gson().fromJson(json, object : TypeToken<Map<String, Any>>() {}.type)
        val resultsJson = Gson().toJson(jsonObject["results"])
        val listType = object : TypeToken<List<Result>>() {}.type
        return@withContext Gson().fromJson(resultsJson, listType)

    }
}


suspend fun getMovieImage(): Images? {
    return withContext(Dispatchers.IO) {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://api.themoviedb.org/3/configuration")
            .get()
            .addHeader("accept", "application/json")
            .addHeader(
                "Authorization",
                "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI5YjE2Yzc3M2RiZmRhNDVmMjYyYTVmY2JkMTdkYjk0NCIsIm5iZiI6MTc0MjczODU3MC41MDEsInN1YiI6IjY3ZTAxNDhhMzBlNjVlN2JlZWM3MWZhMiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.H1c82i0f2Bo-5iJt2VgmkTjOn0WcLQmS8CJbOfF7GBc"
            )
            .build()

        val response = client.newCall(request).execute()
        val json = response.body?.string()

        Log.d("API Image Response", json ?: "Response is null")

        val parsedData: ConfigResponse? = Gson().fromJson(json, ConfigResponse::class.java)
        Log.d("ParsedData", "Parsed data: $parsedData")
        // بررسی اینکه parsedData null نباشه و images هم موجود باشه
        return@withContext parsedData?.images

    }
}