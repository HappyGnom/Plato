package by.happygnom.plato

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import by.happygnom.plato.ui.navigation.AuthNavigation
import by.happygnom.plato.ui.navigation.addAuthGraph
import by.happygnom.plato.ui.theme.PlatoTheme
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

//        val options = FirebaseOptions.builder()
//            .setCredentials(GoogleCredentials.getApplicationDefault())
//            .build()
//
//        FirebaseApp.initializeApp(options)


        setContent {
            PlatoTheme {
                ProvideWindowInsets {
                    Scaffold { innerPadding ->
                        val navController = rememberNavController()

                        rememberSystemUiController().setStatusBarColor(
                            Color.Transparent,
                            darkIcons = MaterialTheme.colors.isLight
                        )

                        AuthNavigation(
                            navController = navController,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            graph = {
                                addAuthGraph(
                                    navController = navController
                                )
                            })
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (Firebase.auth.currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}

