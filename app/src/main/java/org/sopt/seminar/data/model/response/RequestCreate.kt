package org.sopt.seminar.data.model.response

data class RequestCreate(
    val imageCount : Int,
    val title : String,
    val category : String,
    val price : Int,
    val contents : String,
    val isPriceSuggestion : Boolean
)
