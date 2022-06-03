package org.sopt.seminar.data.api

import org.sopt.seminar.data.model.request.RequestDetailOnSail
import org.sopt.seminar.data.model.response.RequestCreate
import org.sopt.seminar.data.model.response.ResponseDetail
import org.sopt.seminar.data.model.response.ResponseDetailLike
import org.sopt.seminar.data.model.response.ResponseDetailOnSale
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ReadService {
    @GET("feed/{itemId}")
    fun getReadInfo(
        @Path("itemId") itemId: String
    ): Call<ResponseDetail>

    @PUT("feed/like/{productId}")
    fun putReadLike(
        @Path("productId") productId: String
    ): Call<ResponseDetailLike>

    @PUT("feed/on-sale")
    fun putReadOnSale(
        @Body body: RequestDetailOnSail
    ) : Call<ResponseDetailOnSale>
}