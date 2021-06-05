package ge.george.androidmidterm2.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ge.george.androidmidterm2.model.Category
import ge.george.androidmidterm2.network.MyRetrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CategoryPagingSource : PagingSource<Int, Category>() {
    override fun getRefreshKey(state: PagingState<Int, Category>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Category> {
        try {
            val nextPageNumber = params.key ?: 1
            val response = withContext(Dispatchers.IO) {
                MyRetrofit.getProductService().getCategories(page = nextPageNumber).execute()
            }

            return if (response.isSuccessful) {
                if (response.body() != null) {
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