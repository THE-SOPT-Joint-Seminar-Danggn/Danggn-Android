package org.sopt.seminar.data.api

import org.sopt.seminar.data.model.response.RequestCreate
import org.sopt.seminar.data.model.response.ResponseCreate
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface CreateService {
    @POST("feed")
    fun registerProduct(
        @Body body: RequestCreate
    ): Call<ResponseCreate>
}