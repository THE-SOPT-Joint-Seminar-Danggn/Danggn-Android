package org.sopt.seminar

import retrofit2.Call
import retrofit2.http.GET

interface FeedService {
    @GET("feed")
    fun getFeedInfo(): Call<ResponseFeed>
}