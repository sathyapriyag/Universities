package com.learn.universities.model.repository


import com.learn.universities.model.data.UniversityListData
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryName

interface APIService {

     @GET("search?country=United+States")
     suspend fun getUniversities(): List<UniversityListData>

     @GET("search?")
     suspend fun getData(@Query(value="country",encoded=false)parameterValue:String):List<UniversityListData>

     companion object
     {
          var BASE_URL = "http://universities.hipolabs.com/"



               var retrofitService: APIService? = null
               fun getInstance() : APIService {
                    if (retrofitService == null) {
                         val retrofit = Retrofit.Builder()
                              .baseUrl(BASE_URL)
                              .addConverterFactory(GsonConverterFactory.create())
                              .build()
                         retrofitService = retrofit.create(APIService::class.java)
                    }
                    return retrofitService!!
               /*return Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    // we need to add converter factory to
                    // convert JSON object to Java object
                    .build()*/
          }
     }
}

