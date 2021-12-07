package by.happygnom.plato.ui.screens.user.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.happygnom.domain.model.Route
import by.happygnom.domain.usecase.GetRoutesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor() : ViewModel() {
    private val _routes = MutableLiveData<List<Route>>(listOf())
    val routes: LiveData<List<Route>> = _routes

}
