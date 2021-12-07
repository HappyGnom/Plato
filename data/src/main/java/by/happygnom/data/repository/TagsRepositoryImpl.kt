package by.happygnom.data.repository

import by.happygnom.data.dao.TagsDao
import by.happygnom.data.network.TagsGateway
import by.happygnom.domain.data_interface.repository.TagsRepository
import by.happygnom.domain.model.Tag

class TagsRepositoryImpl(
    private val tagsGateway: TagsGateway,
    private val tagsDao: TagsDao
) : TagsRepository {

    override suspend fun getAllTags(forceUpdate: Boolean): List<Tag> {
        return tagsDao.ensureIsNotEmpty(forceUpdate).getAll().map { it.toDomain() }
    }

    private suspend fun TagsDao.ensureIsNotEmpty(forceUpdate: Boolean = false) = apply {
        if (this.count() == 0L || forceUpdate) {
            updateAllTags()
        }
    }

    override suspend fun updateAllTags() {
        val tags = tagsGateway.getAllTags()
        tagsDao.insert(tags)
    }
}
