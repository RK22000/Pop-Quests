package io.github.rk22000.data

data class Quest(val description: String, val importance: Importance, val complexity: Complexity) {
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
    HARD(5, "Hard"),
    DIFFICULT(7, "Difficult"),
    MISSION_IMPOSSIBLE(8, "Mission impossible"),
    RPI(13, "RIP"),
    // 1, 2, 3, 5, 7, 13
}

data class QuestDeck(
    val quests: List<Quest>
) {
    fun add(quest: Quest): QuestDeck{
        return QuestDeck((quests + quest).sortedByDescending { it.priority })
    }
}