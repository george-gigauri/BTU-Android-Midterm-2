package ge.george.androidmidterm2.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import ge.george.androidmidterm2.databinding.ActivityDetailsBinding
import ge.george.androidmidterm2.model.ProductResponse
import ge.george.androidmidterm2.network.MyRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsActivity : BaseActivity<ActivityDetailsBinding>() {

    private var productId: Int = 0

    override fun getBinding(inflater: LayoutInflater): ActivityDetailsBinding =
        ActivityDetailsBinding.inflate(inflater)

    override fun onReady(savedInstanceState: Bundle?) {
        productId = intent.getIntExtra("product_id", 0)
        binding.back.setOnClickListener { finish() }
        loadDetails()
    }

    private fun loadDetails() {
        showProgress()
        MyRetrofit.getProductService().getProduct(productId)
            .enqueue(object : Callback<ProductResponse> {
                @SuppressLint("SetTextI18n")
                override fun onResponse(
                    call: Call<ProductResponse>,
                    response: Response<ProductResponse>
                ) {
                    response.body()?.let {
                        binding.tvTitle.text = it.data.name
                        binding.tvDescription.text = it.data.description
                        binding.tvPrice.text = it.data.price + "₾"

                        Glide.with(this@DetailsActivity)
                            .asBitmap()
                            .load(it.data.image)
                            .into(binding.ivImage)
                    }

                    hideProgress()
                }

                override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                    hideProgress()
                }
            })
    }
}