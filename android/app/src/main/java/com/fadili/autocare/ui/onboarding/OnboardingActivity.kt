package com.fadili.autocare.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.fadili.autocare.MainActivity
import com.fadili.autocare.ui.theme.AutoCareTheme
import java.util.Calendar

class OnboardingActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AutoCareTheme {
                OnboardingScreen { name, make, model,
                                   year, registration, mileage ->

                    saveProfile(
                        name, make, model,
                        year, registration, mileage
                    )

                    val intent = Intent(
                        this,
                        MainActivity::class.java
                    ).apply {
                        putExtra("USER_NAME", name)
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                                Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }

                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    private fun saveProfile(
        name: String,
        make: String,
        model: String,
        year: Int,
        registration: String,
        mileage: Int
    ) {
        getSharedPreferences(
            "autocare_setup",
            MODE_PRIVATE
        ).edit().apply {
            putString("user_name", name)
            putString("vehicle_make", make)
            putString("vehicle_model", model)
            putInt("vehicle_year", year)
            putString("vehicle_registration", registration)
            putInt("vehicle_mileage", mileage)
            putBoolean("onboarding_complete", true)
            apply()
        }
    }
}

@Composable
private fun OnboardingScreen(
    onComplete: (
        String, String, String,
        Int, String, Int
    ) -> Unit
) {
    var step by rememberSaveable { mutableIntStateOf(0) }

    var name by rememberSaveable { mutableStateOf("") }
    var make by rememberSaveable { mutableStateOf("") }
    var model by rememberSaveable { mutableStateOf("") }
    var year by rememberSaveable { mutableStateOf("") }
    var registration by rememberSaveable {
        mutableStateOf("")
    }
    var mileage by rememberSaveable { mutableStateOf("") }

    val currentYear = Calendar.getInstance()
        .get(Calendar.YEAR)

    val validYear = year.toIntOrNull()
        ?.let { it in 1886..(currentYear + 1) } == true

    val validMileage = mileage.toIntOrNull()
        ?.let { it >= 0 } == true

    val vehicleValid =
        make.isNotBlank() &&
                model.isNotBlank() &&
                validYear &&
                registration.isNotBlank() &&
                validMileage

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .imePadding()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(Modifier.height(20.dp))

            Text(
                "AutoCare",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                if (step == 0) "Create your profile"
                else "Register your vehicle",
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                if (step == 0)
                    "Let's get started with your personal vehicle companion."
                else
                    "Enter the details of your first vehicle."
            )

            LinearProgressIndicator(
                progress = { if (step == 0) 0.5f else 1f },
                modifier = Modifier.fillMaxWidth()
            )

            if (step == 0) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Full name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = { step = 1 },
                    enabled = name.isNotBlank(),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Continue")
                }
            } else {
                OutlinedTextField(
                    value = make,
                    onValueChange = { make = it },
                    label = { Text("Vehicle make") },
                    placeholder = { Text("e.g. Volkswagen") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = model,
                    onValueChange = { model = it },
                    label = { Text("Vehicle model") },
                    placeholder = { Text("e.g. Golf") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = year,
                    onValueChange = {
                        year = it.filter(Char::isDigit).take(4)
                    },
                    label = { Text("Year") },
                    isError = year.isNotEmpty() && !validYear,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = registration,
                    onValueChange = { registration = it },
                    label = { Text("Registration number") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = mileage,
                    onValueChange = {
                        mileage = it.filter(Char::isDigit).take(9)
                    },
                    label = { Text("Current mileage (km)") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        if (vehicleValid) {
                            onComplete(
                                name.trim(),
                                make.trim(),
                                model.trim(),
                                year.toInt(),
                                registration.trim(),
                                mileage.toInt()
                            )
                        }
                    },
                    enabled = vehicleValid,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Finish Setup")
                }

                TextButton(onClick = { step = 0 }) {
                    Text("Back")
                }
            }
        }
    }
}
