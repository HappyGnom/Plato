package by.happygnom.plato.ui.screens.main

import androidx.lifecycle.ViewModel
import by.happygnom.domain.data_interface.repository.TagsRepository
import by.happygnom.domain.usecase.GetAllTagsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val tagsRepository: TagsRepository
) : ViewModel() {

    init {
        updateStaticData()
    }

    private fun updateStaticData() {
        val getAllTagsUseCase = GetAllTagsUseCase(tagsRepository)
        getAllTagsUseCase.inputForceUpdate = true

        getAllTagsUseCase.executeAsync {
            onSuccess {
                it
            }
            onFailure {
                it
            }
            onComplete {

            }
        }
    }
}
