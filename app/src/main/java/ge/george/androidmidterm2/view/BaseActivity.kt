package ge.george.androidmidterm2.view

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    private var _binding: VB? = null
    val binding: VB
        get() = _binding!!

    private val progress = MyProgressDialog()

    abstract fun getBinding(inflater: LayoutInflater): VB?

    abstract fun onReady(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getBinding(layoutInflater)
        setContentView(binding.root)
        onReady(savedInstanceState)
    }

    fun showProgress() {
        if (!progress.isAdded)
            progress.show(supportFragmentManager, "progress")
    }

    fun hideProgress() {
        if (progress.isAdded)
            progress.dismissAllowingStateLoss()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}