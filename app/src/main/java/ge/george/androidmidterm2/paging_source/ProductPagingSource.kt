package ge.george.androidmidterm2.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ge.george.androidmidterm2.db.room.MyDatabase
import ge.george.androidmidterm2.model.Product
import ge.george.androidmidterm2.network.MyRetrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductPagingSource : PagingSource<Int, Product>() {

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        try {
            val nextPageNumber = params.key ?: 1
            val response = withContext(Dispatchers.IO) {
                MyRetrofit.getProductService().getProducts(page = nextPageNumber).execute()
            }

            return if (response.isSuccessful) {
                if (response.body() != null) {

                    MyDatabase.getInstance()
                        ?.productDao()
                        ?.insert(response.body()!!.data)

                    LoadResult.Page(
                        response.body()!!.data,
                        if (nextPageNumber == 1) null else nextPageNumber - 1,
                        if (!response.body()!!.data.isNullOrEmpty()) nextPageNumber + 1 else null
                    )
                } else {
                    LoadResult.Error(Exception(response.message()))
                }
            } else {
                LoadResult.Error(Exception(""))
            }

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}