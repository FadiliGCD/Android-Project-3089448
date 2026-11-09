package com.fadili.autocare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.fadili.autocare.ui.navigation.AutoCareApp
import com.fadili.autocare.ui.theme.AutoCareTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AutoCareTheme {
                AutoCareApp()
            }
        }
    }
}
