package by.happygnom.plato.ui.screens.auth.signup_details

import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.happygnom.domain.data_interface.repository.UserRepository
import by.happygnom.domain.model.User
import by.happygnom.domain.usecase.RegisterUseCase
import by.happygnom.plato.model.AuthenticatedUser
import by.happygnom.plato.model.InputValidator
import by.happygnom.plato.util.Event
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SignUpDetailsViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _signedUp = MutableLiveData<Event<Boolean>>()
    val signedUp: LiveData<Event<Boolean>> = _signedUp

    private val _name = MutableLiveData("")
    val name: LiveData<String> = _name

    private val _surname = MutableLiveData("")
    val surname: LiveData<String> = _surname

    private val _nickname = MutableLiveData("")
    val nickname: LiveData<String> = _nickname

    private val _startDate = MutableLiveData<Date?>(null)
    val startDate: LiveData<Date?> = _startDate

    private val _sex = MutableLiveData("Male")
    val sex: LiveData<String> = _sex

    fun setName(name: String) {
        _name.value = name
    }

    fun setSurname(surname: String) {
        _surname.value = surname
    }

    fun setNickname(nickname: String) {
        _nickname.value = nickname
    }

    fun setStartDate(date: Date?) {
        _startDate.value = date
    }

    fun setSex(sex: String?) {
        _sex.value = sex
    }

    private val _errors = MutableLiveData<SignUpErrors?>(null)
    val errors: LiveData<SignUpErrors?> = _errors

    fun validateInput() {
        _errors.value = getInputErrorsOrNull()
    }

    private fun getInputErrorsOrNull(): SignUpErrors? {
        val nameErrorId = InputValidator.getFullNameErrorIdOrNull(name.value ?: "")
        val surnameErrorId = InputValidator.getFullNameErrorIdOrNull(surname.value ?: "")
        val nicknameErrorId = InputValidator.getNicknameErrorIdOrNull(nickname.value ?: "")
        val startDateErrorId = InputValidator.getDateErrorIdOrNull(startDate.value)

        val errors = SignUpErrors(
            nameErrorId,
            surnameErrorId,
            nicknameErrorId,
            startDateErrorId,
        )

        return if (listOf(
                nameErrorId,
                surnameErrorId,
                nicknameErrorId,
                startDateErrorId,
            ).all { it == null }
        )
            null
        else
            errors
    }

    fun registerUser() {
        val registerUseCase = RegisterUseCase(userRepository)
        val sex = calculateSex()
        val user = User(
            Firebase.auth.currentUser?.uid!!,
            name.value!!,
            surname.value!!,
            nickname.value,
            sex,
            startDate.value!!,
            null
        )
        registerUseCase.user = user

//        _isLoading.value = true

        registerUseCase.executeAsync {
            onSuccess {
                AuthenticatedUser.defineUser(userRepository = userRepository)
                _signedUp.value = Event(true)
            }
            onFailure {
//                it
            }
            onComplete {
//                _isLoading.value = false
            }
        }
    }

    private fun calculateSex(): Int {
        return when (sex.value) {
            "Male" -> Sex.MALE.number
            "Female" -> Sex.FEMALE.number
            else -> Sex.OTHER.number
        }
    }
}
