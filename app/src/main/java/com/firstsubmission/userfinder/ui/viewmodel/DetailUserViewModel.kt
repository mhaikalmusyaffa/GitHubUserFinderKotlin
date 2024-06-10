package com.firstsubmission.userfinder.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.firstsubmission.userfinder.data.response.DetailUserResponse
import com.firstsubmission.userfinder.data.response.localdb.DataAccessObject
import com.firstsubmission.userfinder.data.response.localdb.LocalDatabase
import com.firstsubmission.userfinder.data.retrofit.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {

    val user = MutableLiveData<DetailUserResponse>()

    private var dao: DataAccessObject?
    private var database: LocalDatabase? = LocalDatabase.getDatabase(application)

    init{
        dao = database?.favoriteUserDao()
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _onFailure = MutableLiveData<Boolean>()
    val onFailure: LiveData<Boolean> = _onFailure

    fun setUserDetail(username: String) {

        _onFailure.value = false
        _isLoading.value = true

        ApiClient.apiInstance
            .getUserDetail(username)
            .enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    _isLoading.value = false
                    _onFailure.value = false

                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    _isLoading.value = false
                    _onFailure.value = true
                    Log.d("Failure", t.message.toString())
                }

            })
    }

    fun getUserDetail(): LiveData<DetailUserResponse> {
        return user
    }

    fun addToFavorite(username: String, id: Int, avatarUrl: String, htmlUrl: String){
        CoroutineScope(Dispatchers.IO).launch {
            val user = com.firstsubmission.userfinder.data.model.Entity(
                id,
                username,
                avatarUrl,
                htmlUrl
            )
            dao?.addToFavorite(user)
        }
    }

    suspend fun checkUser(id: Int) = dao?.checkUser(id)

    fun removeFromFavorite(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            dao?.removeFromFavorite(id)
        }
    }
}