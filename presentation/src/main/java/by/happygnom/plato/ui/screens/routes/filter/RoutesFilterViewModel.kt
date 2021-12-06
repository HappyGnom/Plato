package by.happygnom.plato.ui.screens.routes.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.happygnom.domain.model.RoutesFilter
import by.happygnom.plato.model.GradeLevels
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RoutesFilterViewModel @Inject constructor() : ViewModel() {

    private val _category = MutableLiveData<String?>(null)
    val category: LiveData<String?> = _category

    private val _gradeLevelFrom = MutableLiveData(GradeLevels.LOWEST_GRADE)
    val gradeLevelFrom: LiveData<Int> = _gradeLevelFrom

    private val _gradeLevelTo = MutableLiveData(GradeLevels.HIGHEST_GRADE)
    val gradeLevelTo: LiveData<Int> = _gradeLevelTo

    private val _setterName = MutableLiveData<String?>(null)
    val setterName: LiveData<String?> = _setterName

    private val _setDateFrom = MutableLiveData<Date?>(null)
    val setDateFrom: LiveData<Date?> = _setDateFrom

    private val _setDateTo = MutableLiveData<Date?>(null)
    val setDateTo: LiveData<Date?> = _setDateTo

    private val _tags = MutableLiveData("")
    val tags: LiveData<String> = _tags

    private val _includeTakenDown = MutableLiveData(false)
    val includeTakenDown: LiveData<Boolean> = _includeTakenDown

    fun setCategory(category: String){
        _category.value = category
    }

    fun setGradeLevelFrom(gradeLevel: Int) {
        if (gradeLevel < GradeLevels.LOWEST_GRADE || gradeLevel > GradeLevels.HIGHEST_GRADE)
            return

        _gradeLevelFrom.value = gradeLevel
        if (gradeLevelTo.value!! < gradeLevel)
            _gradeLevelTo.value = gradeLevel
    }

    fun setGradeLevelTo(gradeLevel: Int) {
        if (gradeLevel < GradeLevels.LOWEST_GRADE || gradeLevel > GradeLevels.HIGHEST_GRADE)
            return

        _gradeLevelTo.value = gradeLevel
        if (gradeLevelFrom.value!! > gradeLevel)
            _gradeLevelFrom.value = gradeLevel
    }

    fun setSetterName(name:String){
        _setterName.value = name
    }

    fun setSetDateFrom(date: Date) {
        _setDateFrom.value = date

        if (setDateTo.value != null && setDateTo.value!! < date)
            _setDateTo.value = date
    }

    fun clearSetDateFrom() {
        _setDateFrom.value = null
    }

    fun setSetDateTo(date: Date) {
        _setDateTo.value = date

        if (setDateFrom.value != null && setDateFrom.value!! > date)
            _setDateFrom.value = date
    }

    fun clearSetDateTo() {
        _setDateTo.value = null
    }

    fun setIncludeTakenDown(includeTakenDown: Boolean) {
        _includeTakenDown.value = includeTakenDown
    }

    fun setTags(tags: String) {
        _tags.value = tags
    }

    fun applyFilter(){

    }
}
