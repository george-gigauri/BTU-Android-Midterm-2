package ge.george.androidmidterm2.network

import ge.george.androidmidterm2.model.CategoryResponse
import ge.george.androidmidterm2.model.ProductResponse
import ge.george.androidmidterm2.model.ProductsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductService {

    @GET("products")
    fun getProducts(
        @Query("page") page: Int = 1
    ): Call<ProductsResponse>

    @GET("products/{id}")
    fun getProduct(
        @Path("id") id: Int
    ): Call<ProductResponse>

    @GET("categories")
    fun getCategories(
        @Query("page") page: Int = 1
    ): Call<CategoryResponse>
}