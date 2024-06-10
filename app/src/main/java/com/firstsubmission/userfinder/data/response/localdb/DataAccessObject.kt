package com.firstsubmission.userfinder.data.response.localdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.firstsubmission.userfinder.data.model.Entity

@Dao
interface DataAccessObject {
    @Insert
    suspend fun addToFavorite(userEntity: Entity)

    @Query("SELECT * FROM user_entity")
    fun getFavoriteUser(): LiveData<List<Entity>>

    @Query("SELECT count(*) FROM user_entity WHERE user_entity.id = :id")
    suspend fun checkUser(id: Int): Int

    @Query("DELETE FROM user_entity WHERE user_entity.id = :id")
    suspend fun removeFromFavorite(id : Int): Int
}
