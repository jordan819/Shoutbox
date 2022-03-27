package com.example.shoutbox


import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface JsonApi {

    @GET("/shoutbox/messages")
    fun getComments(): Call<List<FetchedComment>>

    companion object {
        const val BASE_URL = "http://tgryl.pl"
    }

    @POST("/shoutbox/message")
    fun addComment(@Body body: AddComment): Call<FetchedComment>
    
}