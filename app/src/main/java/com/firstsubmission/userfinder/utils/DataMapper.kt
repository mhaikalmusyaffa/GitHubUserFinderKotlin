package com.firstsubmission.userfinder.utils

import com.firstsubmission.userfinder.data.model.Entity
import com.firstsubmission.userfinder.data.response.UserResponse

object DataMapper {
    fun mapResponseToEntity(response: UserResponse): Entity{
        return Entity(
            response.id,
            response.username,
            response.avatarUrl,
            response.htmlUrl
        )
    }

    fun mapResponsesToEntities(responses: List<UserResponse>): List<Entity>{
        return responses.map {
            mapResponseToEntity(it)
        }
    }
}