package by.happygnom.plato.ui.screens.routes.list

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.happygnom.domain.data_interface.repository.RoutesRepository
import by.happygnom.domain.model.Route
import by.happygnom.domain.model.RoutesFilter
import by.happygnom.domain.usecase.GetRoutesUseCase
import by.happygnom.plato.R
import by.happygnom.plato.model.SharedRouteFiltration
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RoutesListViewModel @Inject constructor(
    private val routesRepository: RoutesRepository,
) : ViewModel() {
    private val _displayedRoutesNameId = MutableLiveData(R.string.all_set)
    val displayedRoutesNameId: LiveData<Int> = _displayedRoutesNameId

    private val _routes = MutableLiveData<List<Route>>(listOf())
    val routes: LiveData<List<Route>> = _routes

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        loadRoutes()
    }

    fun setRoutesFilterAndFetch(
        routesFilter: RoutesFilter,
        @StringRes displayedRoutesNameId: Int = R.string.all_set
    ) {
        setRoutesFilter(routesFilter, displayedRoutesNameId)
        loadRoutes()
    }

    fun setRoutesFilter(
        routesFilter: RoutesFilter,
        @StringRes displayedRoutesNameId: Int = R.string.all_set
    ) {
        SharedRouteFiltration.currentDisplayedRoutesNameId = displayedRoutesNameId
        SharedRouteFiltration.currentFilter = routesFilter
    }

    fun loadRoutes(forceUpdate: Boolean = false) {
        val getRoutesUseCase = GetRoutesUseCase(routesRepository)
        getRoutesUseCase.inputRoutesFilter = SharedRouteFiltration.currentFilter
        getRoutesUseCase.inputForceUpdate = forceUpdate
        _isLoading.value = true

        getRoutesUseCase.executeAsync {
            onSuccess {
                _displayedRoutesNameId.postValue(SharedRouteFiltration.currentDisplayedRoutesNameId)
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
