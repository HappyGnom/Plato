package by.happygnom.plato.ui.screens.user

import com.google.firebase.auth.FirebaseAuth

class TokenFilter {
    private val mUser = FirebaseAuth.getInstance().currentUser

    init {
        mUser!!.getIdToken(true)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val idToken: String? = task.result.token
                    // Send token to your backend via HTTPS
                    // ...

                } else {
                    // Handle error -> task.getException();
                }
            }

    }
}