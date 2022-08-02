package io.github.rk22000.data

import io.github.rk22000.data.QuestDeck
import kotlinx.coroutines.flow.Flow

interface BasicQuestDB {
    fun getQuestsAsFlow(): Flow<QuestDeck>
    suspend fun saveQuestDeck(questDeck: QuestDeck)
}