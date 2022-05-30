package org.sopt.seminar.data.model.response

data class ResponseCreate(
    val status : Int,
    val message : String,
    val success : Boolean,
    val data : Data
) {
    data class Data(
        val id : String
    )
}
