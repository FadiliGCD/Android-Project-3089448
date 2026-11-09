package com.fadili.autocare

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.fadili.autocare.ui.navigation.AutoCareApp
import com.fadili.autocare.ui.onboarding.OnboardingActivity
import com.fadili.autocare.ui.theme.AutoCareTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val preferences = getSharedPreferences(
            "autocare_setup",
            MODE_PRIVATE
        )

        val onboardingComplete = preferences.getBoolean(
            "onboarding_complete",
            false
        )

        if (!onboardingComplete) {
            startActivity(
                Intent(this, OnboardingActivity::class.java)
            )
            finish()
            return
        }

        enableEdgeToEdge()

        val userName = intent.getStringExtra("USER_NAME")
            ?: preferences.getString("user_name", "")
            ?: ""

        setContent {
            AutoCareTheme {
                AutoCareApp()
            }
        }
    }
}
