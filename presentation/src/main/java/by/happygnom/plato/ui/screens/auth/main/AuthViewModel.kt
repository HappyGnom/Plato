package by.happygnom.plato.ui.screens.auth.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.happygnom.domain.model.User
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {

    private val _isSignedIn: MutableLiveData<Boolean> = MutableLiveData(false)
    val signedIn: LiveData<Boolean> = _isSignedIn

    private val _loading: MutableLiveData<Boolean> = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _error: MutableLiveData<String> = MutableLiveData("")
    val error: LiveData<String> = _error

    fun signInWithEmailAndPassword(email: String, password: String) = viewModelScope.launch {
        _loading.value = true
        try {
            Firebase.auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    val curUser = Firebase.auth.currentUser
                    curUser?.let {
                        _isSignedIn.value = true
                    }
                } else if (it.isCanceled) {
//                    _error.value = "Invalid emails or password"
                }
            }.await()
        } catch (e: Exception) {
            Log.e("email sign in error", e.toString())
            _error.value = e.message
        }
        finally {
            _loading.value = false
        }
    }

    fun signUpWithEmailAndPassword(email: String, password: String, passwordConfirm: String) = viewModelScope.launch {
        _loading.value = true
        if (password != passwordConfirm) {
            _error.value = "Passwords doesn't match"
        }
        else {
            try {
                Firebase.auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val curUser = Firebase.auth.currentUser
                            curUser?.let { _isSignedIn.value = true }
                        } else if (it.isCanceled) {
//                    _error.value = "Passwords doesn't match"
                        }
                    }.await()
            } catch (e: Exception) {
                Log.e("email sign up error", e.toString())
                _error.value = e.message
            }
        }
        _loading.value = false
    }

    fun signIn(email: String, displayName: String) {
        _isSignedIn.value = true
        _loading.value = false
    }

    fun firebaseAuthWithGoogle(idToken: String) = viewModelScope.launch {
        _loading.value = true
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        Firebase.auth.signInWithCredential(credential).await()
    }
}
