package by.happygnom.plato.ui.screens.routes.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import by.happygnom.domain.model.Route
import by.happygnom.domain.model.mockRoutes
import by.happygnom.plato.model.GradeLevels
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RouteDetailsViewModel @Inject constructor() : ViewModel() {

    private val _route = MutableLiveData<Route>()
    val route: LiveData<Route> = _route

    val gradeName = Transformations.map(route) {
        GradeLevels.gradeLevelToScaleString(
            it.gradeLevel, GradeLevels.GradeScale.FONT_SCALE
        )
    }

    private val _isLiked = MutableLiveData<Boolean>(false)
    val isLiked: LiveData<Boolean> = _isLiked

    private val _isProjected = MutableLiveData<Boolean>(false)
    val isProjected: LiveData<Boolean> = _isProjected

    private val _isSent = MutableLiveData<Boolean>(false)
    val isSent: LiveData<Boolean> = _isSent

    fun loadRoute(routeId: String) {
        _route.value = mockRoutes.find { it.id == routeId }
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
