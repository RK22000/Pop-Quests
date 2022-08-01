package io.github.rk22000.data

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consume
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.withContext

class QuestViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var repo: BasicRepository
    private val channel = Channel<Unit>()

    init {
        Log.v(javaClass.simpleName, "Creating ViewModel on ${Thread.currentThread().name}")
        viewModelScope.launch(Dispatchers.IO) {
            repo = BasicRepository.Companion.Builder()
                .setDB(QuestsFileDB(application))
                .build()
            channel.send(Unit)
        }
    }

    fun getSampleQuests(): QuestDeck = SampleData.getSampleQuests()
    lateinit var questDeck: QuestDeck
    val questFlow = flow {
        if (!this@QuestViewModel::repo.isInitialized) {
            channel.receive()
        }
        repo.getQuestsAsFlow().collect {
            questDeck = it
            emit(it)
        }
    }.flowOn(Dispatchers.IO)

    fun saveQuests(questDeck: QuestDeck) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.saveQuests(questDeck)
        }
    }

    var draftQuest: Quest = Quest("", Importance.DESIRABLE, Complexity.SIMPLE)

    fun saveNewQuest(quest: Quest) {
        saveQuests(questDeck.add(quest))
    }
}