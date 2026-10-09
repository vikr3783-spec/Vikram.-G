package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.data.local.LovePrimeDatabase
import com.example.data.repository.LovePrimeRepository
import com.example.ui.screens.MainScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.LovePrimeViewModel
import com.example.ui.viewmodel.LovePrimeViewModelFactory

class MainActivity : ComponentActivity() {

    private val viewModel: LovePrimeViewModel by viewModels {
        val database = LovePrimeDatabase.getDatabase(applicationContext)
        val repository = LovePrimeRepository(database.lovePrimeDao(), applicationContext)
        LovePrimeViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainScreen(viewModel = viewModel)
            }
        }
    }
}
