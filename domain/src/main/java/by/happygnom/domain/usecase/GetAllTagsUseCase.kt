package by.happygnom.domain.usecase

import by.happygnom.domain.data_interface.repository.TagsRepository
import by.happygnom.domain.model.Tag

class GetAllTagsUseCase(private val tagsRepository: TagsRepository) :
    UseCase<List<Tag>>("GetAllTagsUseCase") {

    var inputForceUpdate: Boolean = false

    override suspend fun performTask(): List<Tag> {
        return tagsRepository.getAllTags(inputForceUpdate)
    }
}
