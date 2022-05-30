package org.sopt.seminar.data.api

import org.sopt.seminar.data.model.response.ResponseFeed
import retrofit2.Call
import retrofit2.http.GET

interface FeedService {
    @GET("feed")
    fun getFeedInfo(): Call<ResponseFeed>
}