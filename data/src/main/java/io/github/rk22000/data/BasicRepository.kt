package io.github.rk22000.data

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BasicRepository private constructor(val questDB: BasicQuestDB) {
    companion object {
        class Builder{
            private lateinit var db: BasicQuestDB
            fun setDB(questDB: BasicQuestDB): Builder {
                db = questDB
                return this
            }
            fun build(): BasicRepository {
                Log.v(javaClass.simpleName, "Building BasicRepository on ${Thread.currentThread().name}")
                return BasicRepository(db)
            }
        }
    }
    init {
        Log.v(javaClass.simpleName, "Repository Created on ${Thread.currentThread().name}")
    }
    fun getQuestsAsFlow(): Flow<QuestDeck> = flow {
        questDB.getQuestsAsFlow().collect {
            Log.v("QuestRepository", "QuestDeck with ${it.quests.size} quests received")
            emit(it)
        }
    }

    suspend fun saveQuests(questDeck: QuestDeck) =
        questDB.saveQuestDeck(questDeck)

}