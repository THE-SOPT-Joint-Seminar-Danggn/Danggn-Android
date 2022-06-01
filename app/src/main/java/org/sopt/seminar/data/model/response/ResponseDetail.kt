package org.sopt.seminar.data.model.response

import org.sopt.seminar.presentation.read.screens.User

data class ResponseDetail(
    val message: String,
    val status: Int,
    val success: Boolean,
    val data: Data
) {
    data class Data(
        val category: String,
        val content: String,
        val createdAt: String,
        val image: List<String>,
        val isLiked: Boolean,
        val isPriceSuggestion: String,
        val onSale: Int,
        val price: String,
        val title: String,
        val view: Int,
        val user: User
    )
}