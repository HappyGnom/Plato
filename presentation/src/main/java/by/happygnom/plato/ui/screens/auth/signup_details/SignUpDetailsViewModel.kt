package by.happygnom.plato.ui.screens.auth.signup_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.happygnom.plato.model.InputValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SignUpDetailsViewModel @Inject constructor() : ViewModel() {
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
}