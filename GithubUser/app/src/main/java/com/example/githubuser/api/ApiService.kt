package com.example.githubuser.api

import com.example.githubuser.GithubResponse
import com.example.githubuser.ItemsItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Headers("Authorization: token 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")
    @GET("search/users")
    fun getUsername(
        @Query("q") q: String
    ) : Call<GithubResponse>

    @Headers("Authorization: token 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")
    @GET("users/{login}")
    fun getDetailUser(
        @Path("login") login: String
    ) : Call<UserDetailResponse>

    @Headers("Authorization: token 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")
    @GET("users/{login}/followers")
    fun getFollowers(
        @Path("login") login: String
    ): Call<List<ItemsItem>>

    @Headers("Authorization: token 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")
    @GET("users/{login}/following")
    fun getFollowing(
        @Path("login") login: String
    ): Call<List<ItemsItem>>
}