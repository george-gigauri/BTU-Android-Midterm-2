package ge.george.androidmidterm2.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ge.george.androidmidterm2.model.ProductCategory

object MyTypeConverters {

    @TypeConverter
    fun fromListOfCategories(list: List<ProductCategory>): String =
        Gson().toJson(list)

    @TypeConverter
    fun toListOfCategories(json: String): List<ProductCategory> =
        Gson().fromJson(json, object : TypeToken<List<ProductCategory>>() {}.type)

    @TypeConverter
    fun fromProduct(product: ProductCategory): String = Gson().toJson(product)

    @TypeConverter
    fun toProduct(productJson: String): ProductCategory =
        Gson().fromJson(productJson, object : TypeToken<ProductCategory>() {}.type)
}