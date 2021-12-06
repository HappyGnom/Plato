package by.happygnom.plato.ui.screens.routes.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import by.happygnom.domain.data_interface.repository.RoutesRepository
import by.happygnom.domain.model.Route
import by.happygnom.domain.usecase.GetRoutesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RoutesListViewModel @Inject constructor(
    private val routesRepository: RoutesRepository,
) : ViewModel() {

    private val _routes = MutableLiveData<List<Route>>(listOf())
    val routes: LiveData<List<Route>> = _routes

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        loadRoutes()
    }

    fun loadRoutes() {
        val getRoutesUseCase = GetRoutesUseCase(routesRepository)
        _isLoading.value = true

        getRoutesUseCase.executeAsync {
            onSuccess {
                _routes.postValue(it)
            }
            onFailure {
                it
            }
            onComplete {
                _isLoading.value = false
            }
        }
    }
}
