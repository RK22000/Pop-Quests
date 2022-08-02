package io.github.rk22000.popquests

//import androidx.compose.foundation.layout.WindowInsets
//import androidx.compose.foundation.layout.ime
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import io.github.rk22000.basic_quests.BASIC_QUEST_SCREEN_DESTINATION
import io.github.rk22000.basic_quests.BasicQuestScreen
import io.github.rk22000.basic_quests.basicQuestGraph
import io.github.rk22000.data.QuestViewModel
import io.github.rk22000.design_systems.theme.PopQuestsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this)[QuestViewModel::class.java]
//        val viewModel = QuestViewModel(application)

        setContent {
            PopQuestsTheme {

                // A surface container using the 'background' color from the theme
//                WindowCompat.setDecorFitsSystemWindows(window, true)
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = BASIC_QUEST_SCREEN_DESTINATION) {
                    basicQuestGraph(navController, viewModel)
                }
            }
        }
    }
}