package ge.george.androidmidterm2.model


data class CategoryResponse(
    var code: Int,
    var data: List<Category>,
    var meta: Meta?
)