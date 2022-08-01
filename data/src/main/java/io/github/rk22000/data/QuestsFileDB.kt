package io.github.rk22000.data

import android.content.Context
import android.util.Log
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import java.io.IOException

class QuestsFileDB(val context: Context): BasicQuestDB {
    init {
        Log.v(javaClass.simpleName,"DataBase Created on ${Thread.currentThread().name}")
    }
    private val FILE_NAME = "Quests.txt"
    private val moshi: Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()
    private val questDeckAdapter: JsonAdapter<QuestDeck> = moshi.adapter(QuestDeck::class.java)

    private val LOG_TAG = javaClass.simpleName
    private fun log(msg: String) { Log.v(LOG_TAG, msg) }

    private fun _readFile(): QuestDeck {
        log("Attempting to read questDeck from $FILE_NAME on ${Thread.currentThread().name}")
        context.filesDir
        val file = File(context.filesDir, FILE_NAME)
        if (!file.exists()) { file.createNewFile() }
        val json = file.readText()
        val questDeck: QuestDeck =
            try {
                val qd = questDeckAdapter.fromJson(json)!!
                log("Successfully read questDeck from $FILE_NAME ")
                qd
            } catch (ioe: IOException) {
                Log.e(LOG_TAG,"Failed to translate json from $FILE_NAME into a QuestDeck", ioe)
                QuestDeck(emptyList())
            } catch (npe: NullPointerException) {
                Log.e(LOG_TAG, "Json adapter returned null", npe)
                QuestDeck(emptyList())
            }
        log("Returning QuestDeck with ${questDeck.quests.size} quests")
        return questDeck
    }
    private fun _overwriteFile(questDeck: QuestDeck) {
        log("Attempting to overwrite $FILE_NAME with a ${questDeck.quests.size} quests long QuestDeck on ${Thread.currentThread().name}")
        val file = File(context.filesDir, FILE_NAME)
        val json = questDeckAdapter.toJson(questDeck)
        file.writeText(json)
        log("Wrote quests to $FILE_NAME")
    }

    private val channel = Channel<QuestDeck>()

    override fun getQuestsAsFlow(): Flow<QuestDeck> = flow {
        emit(_readFile())
        log("Emitted returned Quests")
        for (deck in channel) {
            log("questDeck with ${deck.quests.size} quests received from channel")
            emit(deck)
        }
    }
    override suspend fun saveQuestDeck(questDeck: QuestDeck) {
        _overwriteFile(questDeck)
        channel.send(questDeck)
    }

}