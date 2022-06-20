package by.happygnom.plato.ui.screens.routes.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import by.happygnom.domain.data_interface.repository.CommentsRepository
import by.happygnom.domain.data_interface.repository.RoutesRepository
import by.happygnom.domain.model.Comment
import by.happygnom.domain.model.Route
import by.happygnom.domain.usecase.*
import by.happygnom.plato.navigation.ArgNames
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

    private val _manualLikesCountChange = MutableLiveData(0)
    val manualLikesCountChange: LiveData<Int> = _manualLikesCountChange

    private val _isBookmarked = MutableLiveData(false)
    val isBookmarked: LiveData<Boolean> = _isBookmarked

    private val _isSent = MutableLiveData(false)
    val isSent: LiveData<Boolean> = _isSent

    private val _manualSendsCountChange = MutableLiveData(0)
    val manualSendsCountChange: LiveData<Int> = _manualSendsCountChange

    init {
        loadRouteDetails()
    }

    fun loadRouteDetails(forceUpdate: Boolean = false) {
        loadRoute(forceUpdate)
        loadRouteInteractions()
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

    private fun loadRouteInteractions() {
        val getRouteBookmarkUseCase = GetRouteInteractionsUseCase(routesRepository)
        getRouteBookmarkUseCase.inputRouteId = routeId
        _isLoading.value = true

        getRouteBookmarkUseCase.executeAsync {
            onSuccess {
                _isLiked.value = it.isLiked
                _isBookmarked.value = it.isBookmarked
                _isSent.value = it.isSent

                _manualLikesCountChange.value = 0
                _manualSendsCountChange.value = 0
            }
            onFailure {
                it
            }
            onComplete {
                _isLoading.value = false
            }
        }
    }

    private fun loadLatestComments() {
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

    fun setLiked(isLiked: Boolean) {
        _isLiked.value = isLiked
        _manualLikesCountChange.value = _manualLikesCountChange.value!! + if (isLiked) 1 else -1

        val setLikeUseCase = SetRouteLikeUseCase(routesRepository)
        setLikeUseCase.inputRouteId = routeId
        setLikeUseCase.isLiked = isLiked
        setLikeUseCase.executeAsync {}
    }

    fun setBookmarked(isBookmarked: Boolean) {
        _isBookmarked.value = isBookmarked

        val setBookmarkedUseCase = SetRouteBookmarkUseCase(routesRepository)
        setBookmarkedUseCase.inputRouteId = routeId
        setBookmarkedUseCase.isBookmarked = isBookmarked
        setBookmarkedUseCase.executeAsync {}
    }

    fun setSent(isSent: Boolean) {
        _isSent.value = isSent
        _manualSendsCountChange.value = _manualSendsCountChange.value!! + if (isSent) 1 else -1

        val setSentUseCase = SetRouteSentUseCase(routesRepository)
        setSentUseCase.inputRouteId = routeId
        setSentUseCase.isSent = isSent
        setSentUseCase.executeAsync {}
    }
}
