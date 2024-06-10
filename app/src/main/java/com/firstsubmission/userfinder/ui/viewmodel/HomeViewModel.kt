package com.firstsubmission.userfinder.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.firstsubmission.userfinder.data.response.UserResponse
import com.firstsubmission.userfinder.data.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel(){

    private val listUsers = MutableLiveData<ArrayList<UserResponse>>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _onFailure = MutableLiveData<Boolean>()
    val onFailure : LiveData<Boolean> = _onFailure

    companion object {
        private const val TAG = "MainViewModel"
    }

    init {
        findUsers()
    }

    fun findUsers(){
        _onFailure.value = false
        _isLoading.value = true



        ApiClient.apiInstance.getUsers().enqueue(object : Callback<ArrayList<UserResponse>> {
            override fun onResponse(
                call: Call<ArrayList<UserResponse>>,
                response: Response<ArrayList<UserResponse>>
            ) {
                _isLoading.value = false
                _onFailure.value = false
                if(response.isSuccessful){
                    listUsers.postValue(response.body())
                }

            }

            override fun onFailure(call: Call<ArrayList<UserResponse>>, t: Throwable) {
                _isLoading.value = false
                _onFailure.value = true
                Log.e(TAG, "onFailure : ${t.message}")

            }

        })
    }

    fun getListUsers(): LiveData<ArrayList<UserResponse>> {
        return listUsers
    }
}