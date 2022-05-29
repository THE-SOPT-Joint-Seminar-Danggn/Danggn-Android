package org.sopt.seminar

data class ResponseFeed(
    val id: String,
    val title: String,
    val region: String,
    val image: String,
    val price: Int,
    val createdAt: String
)