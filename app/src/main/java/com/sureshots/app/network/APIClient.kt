package com.sureshots.app.network

import android.content.Context
import android.net.ConnectivityManager
import com.sureshots.app.constant.IS_DEBUG_ON
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by Innovins-21 on 022, 22-Jun-18.
 */

object APIClient {

     const val baseUrl = "http://192.168.1.113/staging/php/webmandi/"

    const val baseUrl2 = "https://api.goprocessing.in/"
    //const val baseUrl = "http://shareittofriends.com/demo/webmandi/"
    const val API_VERSION = "app/v0" // api version dir, do not add / in the end
    // const val API_VERSION = "app/v1" // api version dir, do not add / in the end

    private var retrofit: Retrofit? = null

    /*internal*/
    val retrofitInstance: Retrofit
        get() {
            if (retrofit == null) {
                val builder = OkHttpClient.Builder()

                /*builder.connectTimeout(15L, TimeUnit.SECONDS)
                    .readTimeout(15L, TimeUnit.SECONDS)
                    .writeTimeout(15L, TimeUnit.SECONDS)*/

                if (IS_DEBUG_ON) {
                    val httpLoggingInterceptor = HttpLoggingInterceptor()
                    // Can be Level.BASIC, Level.HEADERS, or Level.BODY
                    // See http://square.github.io/okhttp/3.x/logging-interceptor/ to see the options.
                    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                    //builder.networkInterceptors().add(httpLoggingInterceptor);
                    builder.addNetworkInterceptor(httpLoggingInterceptor)
                }

                builder.addInterceptor(ErrorInterceptor())

                val okClient = builder.build()

                retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl2)
                    //.addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(MoshiConverterFactory.create())
                    .client(okClient)
                    .build()
            }

            return retrofit as Retrofit
        }

    val apiInterface: APIInterface
        get() = retrofitInstance.create(APIInterface::class.java)


    /*Helper Methods*/
    fun isNetworkConnected(context: Context): Boolean {
        val cm: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo.isConnected
    }

}
