package by.happygnom.plato.ui.screens.routes.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import by.happygnom.domain.data_interface.repository.RoutesRepository
import by.happygnom.domain.model.Route
import by.happygnom.domain.usecase.GetRouteByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RouteDetailsViewModel @Inject constructor(
    private val routesRepository: RoutesRepository,
    savedState: SavedStateHandle
) : ViewModel() {

    private val _route = MutableLiveData<Route>()
    val route: LiveData<Route> = _route

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isLiked = MutableLiveData(false)
    val isLiked: LiveData<Boolean> = _isLiked

    private val _isProjected = MutableLiveData(false)
    val isProjected: LiveData<Boolean> = _isProjected

    private val _isSent = MutableLiveData(false)
    val isSent: LiveData<Boolean> = _isSent

    init {
        val routeId = savedState.get<Long>("route_id") ?: -1
        loadRoute(routeId)
    }

    private fun loadRoute(routeId: Long) {
        val getRouteByIdUseCase = GetRouteByIdUseCase(routesRepository)
        getRouteByIdUseCase.inputRouteId = routeId
        _isLoading.value = true

        getRouteByIdUseCase.executeAsync {
            onSuccess {
                _route.postValue(it)
            }
            onFailure {
                it
            }
            onComplete {
                _isLoading.value = false
            }
        }
    }

    fun setLiked(value: Boolean) {
        _isLiked.value = value
    }

    fun setProjected(value: Boolean) {
        _isProjected.value = value
    }

    fun setSent(value: Boolean) {
        _isSent.value = value
    }
}
