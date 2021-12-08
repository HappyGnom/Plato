package by.happygnom.plato.ui.screens.user.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.happygnom.domain.data_interface.repository.UserRepository
import by.happygnom.domain.model.User
import by.happygnom.domain.usecase.GetUserUseCase
import by.happygnom.domain.usecase.UpdateUserUseCase
import by.happygnom.plato.R
import by.happygnom.plato.model.AuthenticatedUser
import by.happygnom.plato.model.InputValidator
import by.happygnom.plato.ui.screens.auth.signup_details.Sex
import by.happygnom.plato.ui.screens.auth.signup_details.SignUpErrors
import by.happygnom.plato.util.Event
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val user: User? = AuthenticatedUser.get()

    private val _saved = MutableLiveData<Event<Boolean>>()
    val saved: LiveData<Event<Boolean>> = _saved

    private val _name = MutableLiveData(user?.name ?: "")
    val name: LiveData<String> = _name

    private val _surname = MutableLiveData(user?.surname ?: "")
    val surname: LiveData<String> = _surname

    private val _nickname = MutableLiveData(user?.nickname ?: "")
    val nickname: LiveData<String> = _nickname

    private val _startDate = MutableLiveData<Date?>(user?.startDate)
    val startDate: LiveData<Date?> = _startDate

    private val _sex = MutableLiveData(sexToString(user?.sex) ?: "Male")
    val sex: LiveData<String> = _sex

    private val _photoUrl = MutableLiveData(user?.pictureUrl)
    val photoUrl: LiveData<String?> = _photoUrl

    private fun sexToString(sex: Int?): String? {
        return when (sex) {
            0 -> "Male"
            1 -> "Female"
            2 -> "Other"
            else -> null
        }
    }


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

    fun setSex(sex: String) {
        _sex.value = sex
    }

    fun setPhotoUrl(url: String?) {
        _photoUrl.value = url
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

    fun updateUser() {
        val updateUseCase = UpdateUserUseCase(userRepository)
        val sex = calculateSex()
        val user = User(
            Firebase.auth.currentUser?.uid!!,
            name.value!!,
            surname.value!!,
            nickname.value,
            sex,
            startDate.value!!,
            photoUrl.value,
            )
        updateUseCase.user = user

//        _isLoading.value = true

        updateUseCase.executeAsync {
            onSuccess {
                AuthenticatedUser.defineUser(userRepository = userRepository, forceUpdate = true)
                _saved.value = Event(true)
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