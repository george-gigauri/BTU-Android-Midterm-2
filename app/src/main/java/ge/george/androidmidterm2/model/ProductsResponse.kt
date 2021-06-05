package ge.george.androidmidterm2.model


data class ProductsResponse(
    var code: Int,
    var data: List<Product>,
    var meta: Meta?
)