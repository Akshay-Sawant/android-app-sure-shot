package com.sureshotdiscount.app.data.api

import android.content.Context
import android.net.ConnectivityManager
import com.sureshotdiscount.app.utils.error.ErrorInterceptor
import com.sureshotdiscount.app.utils.IS_DEBUG_ON
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by Innovins-21 on 022, 22-Jun-18.
 */

object APIClient {

    const val BASE_URL = "http://shareittofriends.com/"

    const val API_VERSION = "demo/sureshotdiscount/auth" // api version dir, do not add / in the end
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
                    .baseUrl(BASE_URL)
                    //.addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(MoshiConverterFactory.create())
                    .client(okClient)
                    .build()
            }

            return retrofit as Retrofit
        }

    val apiInterface: APIInterface
        get() = retrofitInstance.create(
            APIInterface::class.java
        )


    /*Helper Methods*/
    fun isNetworkConnected(context: Context): Boolean {
        val cm: ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo.isConnected
    }

}
