package com.example.greetandeat2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginBtn: Button
    private lateinit var googleBtn: Button
    private lateinit var registerBtn: Button
    private lateinit var biometricBtn: Button

    private val RC_SIGN_IN = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        // Views
        emailInput = findViewById(R.id.emailInput)
        passwordInput = findViewById(R.id.passwordInput)
        loginBtn = findViewById(R.id.loginBtn)
        googleBtn = findViewById(R.id.googleBtn)
        registerBtn = findViewById(R.id.registerBtn)
        biometricBtn = findViewById(R.id.biometricBtn)

        // Google Sign-In setup
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("187103160430-oc5i3k2gdeqej5fodpne796b6ulee401.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Biometrics setup
        val biometricManager = BiometricManager.from(this)
        val canAuthenticate = biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)
        val prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val savedEmail = prefs.getString("lastUserEmail", null)
        biometricBtn.isEnabled = (canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS && savedEmail != null)

        loginBtn.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email and password required", Toast.LENGTH_SHORT).show()
            } else {
                loginUser(email, password)
            }
        }

        googleBtn.setOnClickListener {
            googleSignInClient.signOut().addOnCompleteListener {
                val signInIntent = googleSignInClient.signInIntent
                startActivityForResult(signInIntent, RC_SIGN_IN)
            }
        }

        registerBtn.setOnClickListener {
            startActivity(Intent(this, Register::class.java))
        }

        biometricBtn.setOnClickListener {
            showBiometricPrompt()
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Toast.makeText(this, "Welcome back ${user?.email}", Toast.LENGTH_SHORT).show()

                    val prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)
                    prefs.edit().putString("lastUserEmail", email).apply()

                    // ✅ Unlock First Login if not yet unlocked
                    if (!RewardsManager.isRewardUnlocked(this, "first_login")) {
                        RewardsManager.unlockReward(this, "first_login")
                        Toast.makeText(this, "🎉 You unlocked the 'First Login' reward!", Toast.LENGTH_LONG).show()
                    }

                    // ✅ Track daily streak
                    RewardsManager.handleDailyCheckIn(this)

                    startActivity(Intent(this, Home::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Login failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }

    // Google Sign-In result
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Toast.makeText(this, "Google Sign-In failed: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Toast.makeText(this, "Google Login success: ${user?.displayName}", Toast.LENGTH_SHORT).show()

                    val prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)
                    prefs.edit().putString("lastUserEmail", user?.email).apply()


                    if (!RewardsManager.isRewardUnlocked(this, "first_login")) {
                        RewardsManager.unlockReward(this, "first_login")
                        Toast.makeText(this, "🎉 You unlocked the 'First Login' reward!", Toast.LENGTH_LONG).show()
                    }


                    RewardsManager.handleDailyCheckIn(this)

                    startActivity(Intent(this, Home::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Google Auth failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }

    // Biometric Login
    private fun showBiometricPrompt() {
        val executor = ContextCompat.getMainExecutor(this)
        val biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(applicationContext, "Biometric Auth succeeded!", Toast.LENGTH_SHORT).show()

                    // Unlock First Login if not yet unlocked
                    if (!RewardsManager.isRewardUnlocked(applicationContext, "first_login")) {
                        RewardsManager.unlockReward(applicationContext, "first_login")
                        Toast.makeText(applicationContext, "🎉 You unlocked the 'First Login' reward!", Toast.LENGTH_LONG).show()
                    }

                    RewardsManager.handleDailyCheckIn(applicationContext)
                    startActivity(Intent(applicationContext, Home::class.java))
                    finish()
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    Toast.makeText(applicationContext, "Auth error: $errString", Toast.LENGTH_SHORT).show()
                }

                override fun onAuthenticationFailed() {
                    Toast.makeText(applicationContext, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Login")
            .setSubtitle("Use your fingerprint or face to log in")
            .setNegativeButtonText("Cancel")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}


// References:
// Firebase Authentication guide: https://firebase.google.com/docs/auth/android/start
// Google Sign-In integration: https://developers.google.com/identity/sign-in/android/start-integrating
// Android Biometric authentication: https://developer.android.com/training/sign-in/biometric-auth
// SharedPreferences for storing last used user: https://developer.android.com/training/data-storage/shared-preferences
