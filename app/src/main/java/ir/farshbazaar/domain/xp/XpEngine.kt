package ir.farshbazaar.domain.xp

import ir.farshbazaar.domain.model.XpEventType

object XpEngine {
    const val POST_CREATED_POINTS = 10
    const val LIKE_RECEIVED_POINTS = 2
    const val COMMENT_RECEIVED_POINTS = 0
    const val FOLLOW_RECEIVED_POINTS = 0

    fun collaborationMultiplier(): Int = 1

    fun calculatePoints(type: XpEventType): Int {
        val base = when (type) {
            XpEventType.POST_CREATED -> POST_CREATED_POINTS
            XpEventType.LIKE_RECEIVED -> LIKE_RECEIVED_POINTS
            XpEventType.COMMENT_RECEIVED -> COMMENT_RECEIVED_POINTS
            XpEventType.FOLLOW_RECEIVED -> FOLLOW_RECEIVED_POINTS
        }
        return base * collaborationMultiplier()
    }
}
