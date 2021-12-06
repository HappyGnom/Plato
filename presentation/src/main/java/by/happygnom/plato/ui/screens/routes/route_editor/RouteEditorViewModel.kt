package by.happygnom.plato.ui.screens.routes.route_editor

import android.graphics.drawable.BitmapDrawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import by.happygnom.domain.data_interface.repository.RoutesRepository
import by.happygnom.domain.usecase.GetRouteByIdUseCase
import by.happygnom.plato.model.GradeLevels
import by.happygnom.plato.model.InputValidator
import by.happygnom.plato.util.toBase64
import coil.ImageLoader
import coil.request.ImageRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RouteEditorViewModel @Inject constructor(
    private val routesRepository: RoutesRepository,
    private val imageLoader: ImageLoader,
    private val imageRequestBuilder: ImageRequest.Builder,
    savedState: SavedStateHandle
) : ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _existingRouteId = MutableLiveData<Long?>(null)
    val existingRouteId: LiveData<Long?> = _existingRouteId

    private val _pictureBase64 = MutableLiveData<String?>(null)
    val pictureBase64: LiveData<String?> = _pictureBase64

    private val _gradeLevel = MutableLiveData(0)
    val gradeLevel: LiveData<Int> = _gradeLevel

    private val _holdsColor = MutableLiveData("")
    val holdsColor: LiveData<String> = _holdsColor

    private val _setterName = MutableLiveData("")
    val setterName: LiveData<String> = _setterName

    private val _setDate = MutableLiveData<Date?>(null)
    val setDate: LiveData<Date?> = _setDate

    private val _tags = MutableLiveData("")
    val tags: LiveData<String> = _tags

    private val _errors = MutableLiveData<RouteEditorErrors?>(null)
    val errors: LiveData<RouteEditorErrors?> = _errors

    init {
        val routeId = savedState.get<Long>("existing_route_id")
        if (routeId != null && routeId >= 0)
            loadRoute(routeId)
    }

    private fun loadRoute(routeId: Long) {
        val getRouteByIdUseCase = GetRouteByIdUseCase(routesRepository)
        getRouteByIdUseCase.inputRouteId = routeId
        _isLoading.value = true

        getRouteByIdUseCase.executeAsync {
            onSuccess { route ->
                _existingRouteId.value = route.id
                route.pictureUrl?.let { setPicture(it) }
                _gradeLevel.value = route.gradeLevel
                _holdsColor.value = route.holdsColor
                _setterName.value = route.setterName
                _setDate.value = route.setDate
                _tags.value = route.tags.joinToString()
            }
            onFailure {
                it
            }
            onComplete {
                _isLoading.value = false
            }
        }
    }

    fun setPicture(pictureUri: String) {
        val imageRequest = imageRequestBuilder
            .data(pictureUri)
//            .placeholder(R.drawable.placeholder_route)
//            .error(R.drawable.placeholder_route)
            .target { result ->
                val base64 = (result as BitmapDrawable?)?.bitmap?.toBase64()
                _pictureBase64.value = base64
            }
            .build()

        imageLoader.enqueue(imageRequest)
    }

    fun setGradeLevel(gradeLevel: Int) {
        if (gradeLevel < GradeLevels.LOWEST_GRADE || gradeLevel > GradeLevels.HIGHEST_GRADE)
            return

        _gradeLevel.value = gradeLevel
    }

    fun setHoldsColor(color: String) {
        _holdsColor.value = color
    }

    fun setSetterName(name: String) {
        _setterName.value = name
    }

    fun setSetDate(date: Date) {
        _setDate.value = date
    }

    fun setTags(tags: String) {
        _tags.value = tags
    }

    fun validateInput() {
        _errors.value = getInputErrorsOrNull()
    }

    private fun getInputErrorsOrNull(): RouteEditorErrors? {
        val pictureErrorId = InputValidator.getPictureBase64ErrorIdOrNull(pictureBase64.value)
        val holdsColorErrorId = InputValidator.getHoldsColorErrorIdOrNull(holdsColor.value ?: "")
        val setterNameErrorId = InputValidator.getFullNameErrorIdOrNull(setterName.value ?: "")
        val setDateErrorId = InputValidator.getDateErrorIdOrNull(setDate.value)
        val tagsErrorId = InputValidator.getTagsErrorIdOrNull(tags.value ?: "")

        val errors = RouteEditorErrors(
            pictureErrorId,
            holdsColorErrorId,
            setterNameErrorId,
            setDateErrorId,
            tagsErrorId
        )

        return if (listOf(
                pictureErrorId, holdsColorErrorId,
                setterNameErrorId, setDateErrorId,
                tagsErrorId
            ).all { it == null }
        )
            null
        else
            errors
    }
}
