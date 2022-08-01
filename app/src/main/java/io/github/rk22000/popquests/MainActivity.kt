package io.github.rk22000.popquests

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.WindowInsets
//import androidx.compose.foundation.layout.ime
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import io.github.rk22000.data.QuestViewModel
import io.github.rk22000.design_systems.theme.PopQuestsTheme
import io.github.rk22000.design_systems.screens.QuestScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this)[QuestViewModel::class.java]
//        val viewModel = QuestViewModel(application)

        setContent {
            PopQuestsTheme {

                // A surface container using the 'background' color from the theme
//                WindowCompat.setDecorFitsSystemWindows(window, true)
                QuestScreen(
                    viewModel = viewModel,
                )


            }
        }
    }
}