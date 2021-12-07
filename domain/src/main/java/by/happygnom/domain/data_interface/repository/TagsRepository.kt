package by.happygnom.domain.data_interface.repository

import by.happygnom.domain.model.Tag

interface TagsRepository {

    suspend fun getAllTags(forceUpdate: Boolean = false): List<Tag>

    suspend fun updateAllTags()
}
