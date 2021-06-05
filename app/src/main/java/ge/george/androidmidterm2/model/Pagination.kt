package ge.george.androidmidterm2.model


import com.google.gson.annotations.SerializedName

data class Pagination(
    var limit: Int,
    var page: Int,
    var pages: Int,
    var total: Int
)