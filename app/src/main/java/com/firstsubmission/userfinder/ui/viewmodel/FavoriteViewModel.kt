package com.firstsubmission.userfinder.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.firstsubmission.userfinder.data.model.Entity
import com.firstsubmission.userfinder.data.response.localdb.DataAccessObject
import com.firstsubmission.userfinder.data.response.localdb.LocalDatabase

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    private var dao: DataAccessObject?
    private var database: LocalDatabase? = LocalDatabase.getDatabase(application)

    init{
        dao = database?.favoriteUserDao()
    }

    fun getFavoriteUser(): LiveData<List<Entity>>?{
        return dao?.getFavoriteUser()
    }


}