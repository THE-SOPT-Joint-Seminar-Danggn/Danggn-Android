package org.sopt.seminar

data class ResponseFeed(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: List<Data>
) {
    data class Data(
        val id: String,
        val title: String,
        val region: String,
        val image: String,
        val price: Int,
        val createdAt: String
    )
}