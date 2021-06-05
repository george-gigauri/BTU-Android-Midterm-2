package ge.george.androidmidterm2.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import ge.george.androidmidterm2.db.MyTypeConverters

@Entity(tableName = "products_table")
@TypeConverters(MyTypeConverters::class)
data class Product(
    var categories: List<ProductCategory>,
    var description: String,
    @SerializedName("discount_amount")
    var discountAmount: String,
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var image: String,
    var name: String,
    var price: String,
    var status: Boolean
)