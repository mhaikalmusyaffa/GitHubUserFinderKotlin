package com.firstsubmission.userfinder.data.retrofit

import com.firstsubmission.userfinder.data.response.DetailUserResponse
import com.firstsubmission.userfinder.data.response.SearchUserResponse
import com.firstsubmission.userfinder.data.response.UserResponse
import com.firstsubmission.userfinder.utils.Config
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    @Headers("Authorization: token ${Config.GITHUB_TOKEN}")
    fun getUsers(): Call<ArrayList<UserResponse>>

    @GET("search/users")
    @Headers("Authorization: token ${Config.GITHUB_TOKEN}")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<SearchUserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token ${Config.GITHUB_TOKEN}")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token ${Config.GITHUB_TOKEN}")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<UserResponse>>

    @GET("users/{username}/following")
    @Headers("Authorization: token ${Config.GITHUB_TOKEN}")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<UserResponse>>

}