package ge.george.androidmidterm2.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MyRetrofit {

    private const val BASE_URL = "https://gorest.co.in/public-api/"
    private var instance: Retrofit? = null

    fun getInstance(): Retrofit = instance ?: Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getProductService() = getInstance().create(ProductService::class.java)

}