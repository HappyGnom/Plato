package by.happygnom.plato.ui.screens.user

import android.content.Intent
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.lifecycle.ViewModel
import by.happygnom.plato.AuthActivity
import by.happygnom.plato.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor() : ViewModel() {
}
