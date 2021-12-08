package by.happygnom.plato.model

import androidx.annotation.StringRes
import by.happygnom.domain.model.RoutesFilter
import by.happygnom.plato.R

object SharedRouteFiltration {
    var currentFilter: RoutesFilter = RoutesFilter.Default
    @StringRes
    var currentDisplayedRoutesNameId: Int = R.string.all_set
}
