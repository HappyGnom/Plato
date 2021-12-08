package by.happygnom.plato.ui.screens.auth.signup_details

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import by.happygnom.plato.MainActivity
import by.happygnom.plato.R
import by.happygnom.plato.ui.elements.LabeledLogo
import by.happygnom.plato.ui.elements.RadioButtonHorizontal
import by.happygnom.plato.ui.elements.button.DatePickerButton
import by.happygnom.plato.ui.elements.button.TealFilledButton
import by.happygnom.plato.ui.elements.inputs.InputTextFieldBox
import by.happygnom.plato.ui.navigation.AuthenticationScreen
import by.happygnom.plato.util.showDatePickerDialog
import by.happygnom.plato.util.toFormattedDateString
import com.google.accompanist.insets.statusBarsPadding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.*

@Composable
fun SignUpDetailsScreen(viewModel: SignUpDetailsViewModel, navController: NavController) {
    val context = LocalContext.current

    val errors by viewModel.errors.observeAsState()
    val user = Firebase.auth.currentUser
    val name by viewModel.name.observeAsState("")
    val surname by viewModel.surname.observeAsState("")
    val nickname by viewModel.nickname.observeAsState("")
    val startDate by viewModel.startDate.observeAsState(null)
    val sex by viewModel.sex.observeAsState("Male")

    val signedUp by viewModel.signedUp.observeAsState()

    fun updateUi() {
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
    }

    signedUp?.getContentIfNotHandled()?.let {
        updateUi()
    }

    Box(modifier = Modifier.fillMaxSize(1f)) {
        Column(
            modifier = Modifier
                .fillMaxSize(1f)
                .statusBarsPadding()
        ) {

            Divider(
                Modifier
                    .fillMaxWidth(1f)
                    .width(1.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize(1f)
                    .padding(40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                LabeledLogo()

                Text(
                    text = stringResource(R.string.profile_info_label),
                    modifier = Modifier.padding(top = 16.dp),
                    style = MaterialTheme.typography.h2
                )
                InputTextFieldBox(
                    text = user?.displayName ?: name,
                    onValueChange = viewModel::setName,
                    label = stringResource(R.string.user_name),
                    hint = stringResource(id = R.string.user_name),
                    error = errors?.nameErrorId?.let { stringResource(id = it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp)
                )

                InputTextFieldBox(
                    text = surname,
                    onValueChange = viewModel::setSurname,
                    label = stringResource(R.string.user_surname),
                    hint = stringResource(id = R.string.user_surname),
                    error = errors?.surnameErrorId?.let { stringResource(id = it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )

                InputTextFieldBox(
                    text = nickname,
                    onValueChange = viewModel::setNickname,
                    label = stringResource(R.string.nickname),
                    hint = stringResource(id = R.string.nickname),
                    error = errors?.nicknameErrorId?.let { stringResource(id = it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )

                DatePickerButton(
                    text = if (startDate == null) "" else startDate!!.toFormattedDateString(),
                    hint = stringResource(R.string.start_date),
                    label = stringResource(R.string.start_date),
                    onClick = {
                        val initialCalendar = Calendar.getInstance()
                        startDate?.let { initialCalendar.time = it }

                        showDatePickerDialog(context, initialCalendar) { calendar ->
                            viewModel.setStartDate(calendar.time)
                        }
                    },
                    error = errors?.startDateErrorId?.let { stringResource(id = it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    onClear = { viewModel.setStartDate(null) }
                )

                val radioOptions: List<String> = listOf("Male", "Female", "Other")

                RadioButtonHorizontal(
                    label = "Sex",
                    radioOptions = radioOptions,
                    modifier = Modifier.padding(top = 8.dp),
                    onOptionSelected = {
                        viewModel.setSex(it)
                    },
                    selectedOption = sex
                )

                TealFilledButton(
                    text = stringResource(R.string.continueText),
                    onClick = {
                        viewModel.validateInput()
                        if (errors == null) {
                            viewModel.registerUser()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(top = 32.dp)
                )
            }
        }
    }
}
