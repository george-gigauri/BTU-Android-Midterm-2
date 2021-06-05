package ge.george.androidmidterm2.db.dao

import androidx.paging.PagingSource
import androidx.room.*
import ge.george.androidmidterm2.model.Product

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(products: List<Product>)

    @Delete
    fun delete(product: Product)

    @Query("SELECT * FROM products_table ORDER BY id")
    fun getAllForPaging(): PagingSource<Int, Product>

    @Query("SELECT COUNT(*) FROM products_table")
    fun getRecordCount(): Int
}