package by.happygnom.plato.ui.screens.routes.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import by.happygnom.domain.data_interface.repository.CommentsRepository
import by.happygnom.domain.data_interface.repository.RoutesRepository
import by.happygnom.domain.model.Comment
import by.happygnom.domain.model.Route
import by.happygnom.domain.usecase.GetCommentsUseCase
import by.happygnom.domain.usecase.GetRouteByIdUseCase
import by.happygnom.plato.ui.navigation.ArgNames
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RouteDetailsViewModel @Inject constructor(
    private val routesRepository: RoutesRepository,
    private val commentsRepository: CommentsRepository,
    savedState: SavedStateHandle
) : ViewModel() {

    private val routeId = savedState.get<Long>(ArgNames.ROUTE_ID) ?: -1

    private val _route = MutableLiveData<Route>()
    val route: LiveData<Route> = _route

    private val _latestComments = MutableLiveData<List<Comment>>()
    val latestComments: LiveData<List<Comment>> = _latestComments

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isLiked = MutableLiveData(false)
    val isLiked: LiveData<Boolean> = _isLiked

    private val _isProjected = MutableLiveData(false)
    val isProjected: LiveData<Boolean> = _isProjected

    private val _isSent = MutableLiveData(false)
    val isSent: LiveData<Boolean> = _isSent

    init {
        loadRouteDetails()
    }

    fun loadRouteDetails(forceUpdate: Boolean = false){
        loadRoute(forceUpdate)
        loadLatestComments()
    }

    private fun loadRoute(forceUpdate: Boolean = false) {
        val getRouteByIdUseCase = GetRouteByIdUseCase(routesRepository)
        getRouteByIdUseCase.inputRouteId = routeId
        getRouteByIdUseCase.inputForceUpdate = forceUpdate
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

    fun loadLatestComments() {
        val getCommentsUseCase = GetCommentsUseCase(commentsRepository)
        getCommentsUseCase.inputRouteId = routeId
        getCommentsUseCase.inputCount = 3
        _isLoading.value = true

        getCommentsUseCase.executeAsync {
            onSuccess {
                _latestComments.postValue(it)
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
