package by.happygnom.plato.ui.screens.auth.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {
    var currentUser by mutableStateOf<FirebaseUser?>(null)
        private set

    @JvmName("assignCurrentUser")
    fun setCurrentUser(user: FirebaseUser?) {
        currentUser = user
    }
}