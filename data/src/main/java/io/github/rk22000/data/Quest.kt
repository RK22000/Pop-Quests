package io.github.rk22000.data

import java.time.LocalDate

data class Quest(
    val description: String,
    val importance: Importance,
    val complexity: Complexity,
    val tags: List<String> = emptyList(),
    val startLine: Long = LocalDate.now().toEpochDay(),
    val deadLine: Long = LocalDate.MAX.toEpochDay()
) {
    val priority: Int
        get() = importance.value / complexity.value
}

enum class Importance(
    val value: Int,
    val label: String
) {
    DISTRACTION(-50, "Distraction"),
    INSIGNIFICANT(0, "Insignificant"),
    DESIRABLE(100, "Desirable"),
    NECESSARY(200, "Necessary"),
    SIGNIFICANT(300, "Significant"),
    SIGNIFICANTLY_DESIRABLE(500, "Significantly Desirable"),
    CRITICAL(700, "Critical"),
    CANT_MESS_UP(1300, "Can't mess up"),
    DROP_EVERYTHING(1700, "Drop Everything"),
    // -50, 0, 100, 200, 300, 500, 700, 1300
}

enum class Complexity(
    val value: Int,
    val label: String
) {
    VERY_EASY(1, "Very Easy"),
    EASY(2, "Easy"),
    SIMPLE(3, "Simple"),
    MEDIOCRE(5, "Mediocre"),
    HARD(7, "Hard"),
    DIFFICULT(13, "Difficult"),
    MISSION_IMPOSSIBLE(17, "Mission impossible"),
    RPI(23, "RIP"),
    // 1, 2, 3, 5, 7, 13
}


enum class Mood(
    val label: String,
    val check: (Quest) -> Boolean,
    val comparator: Comparator<Quest>,
) {
    LAZY(
        "Lazy",
        { it.complexity <= Complexity.SIMPLE },
        { q1, q2 ->
            (q2.priority - q1.priority)
                .takeUnless { it == 0 }
                ?: (q1.deadLine - q2.deadLine).toInt()
        }
    ),
    NORMAL(
        "Normal",
        { true },
        { q1, q2 ->
            (q1.deadLine - q2.deadLine)
                .takeUnless { it == 0L }
                ?.toInt()
                ?: (q2.priority - q1.priority)
        }
    ),
}

data class QuestDeck(
    val quests: List<Quest>
) {
    fun add(quest: Quest): QuestDeck {
        return QuestDeck((quests + quest).sortedWith(Mood.NORMAL.comparator))
    }
}