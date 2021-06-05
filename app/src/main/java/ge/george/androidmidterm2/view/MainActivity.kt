package ge.george.androidmidterm2.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.asLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.recyclerview.widget.RecyclerView
import ge.george.androidmidterm2.adapter.CategoryAdapter
import ge.george.androidmidterm2.adapter.ProductPagingAdapter
import ge.george.androidmidterm2.databinding.ActivityMainBinding
import ge.george.androidmidterm2.db.dao.ProductDao
import ge.george.androidmidterm2.db.room.MyDatabase
import ge.george.androidmidterm2.model.Category
import ge.george.androidmidterm2.model.Product
import ge.george.androidmidterm2.paging_source.CategoryPagingSource
import ge.george.androidmidterm2.paging_source.ProductPagingSource

class MainActivity : BaseActivity<ActivityMainBinding>(),
    ProductPagingAdapter.OnProductClickListener, CategoryAdapter.OnCategoryClickListener {

    private val productAdapter = ProductPagingAdapter(this)
    private val categoryAdapter = CategoryAdapter(this)

    private lateinit var productDao: ProductDao

    override fun getBinding(inflater: LayoutInflater) = ActivityMainBinding.inflate(inflater)

    override fun onReady(savedInstanceState: Bundle?) {
        productDao = MyDatabase.getInstance(this).productDao()

        binding.rvProducts.adapter = productAdapter
        binding.rvCategories.adapter = categoryAdapter

        // SwipeToRefresh-მა ხელი რომ არ შეგვიშალოს, ჰორიზონტალურ რისექლერვიუზე გასქროლვისას
        // ეგ დროებით ითიშება <3
        binding.rvCategories.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == RecyclerView.SCROLL_STATE_DRAGGING)
                    binding.root.isEnabled = false
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                    binding.root.isEnabled = true
            }
        })

        retrieveCategories()

        if (productDao.getRecordCount() > 0) {
            fetchLocally()
        } else {
            retrieveProducts()
        }

        binding.root.setOnRefreshListener {
            if (binding.root.isRefreshing) {
                retrieveProducts()
            }
        }
    }

    private fun retrieveCategories() {
        val flow = Pager(
            PagingConfig(
                initialLoadSize = 20,
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CategoryPagingSource() }
        ).flow.asLiveData()

        flow.observe(this) {
            categoryAdapter.submitData(lifecycle, it)
        }
    }

    private fun retrieveProducts() {
        binding.root.isRefreshing = true
        val flow = Pager(
            PagingConfig(
                initialLoadSize = 20,
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ProductPagingSource() }
        ).flow.asLiveData()

        flow.observe(this) {
            productAdapter.submitData(lifecycle, it)
            binding.root.isRefreshing = false
        }
    }

    private fun fetchLocally() {
        Toast.makeText(this, "Fetching locally", Toast.LENGTH_SHORT).show()
        val recorderList = Pager(
            config = PagingConfig(
                initialLoadSize = 20,
                pageSize = 10,
                maxSize = 100,
                enablePlaceholders = true
            )
        ) {
            productDao.getAllForPaging()
        }.flow.asLiveData()

        recorderList.observe(this) {
            productAdapter.submitData(lifecycle, it)
        }
    }

    override fun onProductClicked(item: Product) {
        startActivity(Intent(this, DetailsActivity::class.java).apply {
            putExtra("product_id", item.id)
        })
    }

    override fun onCategoryClicked(item: Category) {
        AlertDialog.Builder(this)
            .setTitle("ვერა ((")
            .setMessage("ეს მოქმედება ვერ განხორციელდება რადგან ვერც მოვასწარი და არც კატეგორიების მიხედვით გაფილტვრის მხარდაჭერა არ აქვს API-ს. ((")
            .setNegativeButton("კაი რაღა ვუყო...") { _, _ ->
            }.show()
    }
}