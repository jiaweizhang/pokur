package pokur.calculate

/**
 * Created by jiaweizhang on 6/12/2017.
 */

class RankingUnit(var playerHand: PlayerHand, var rank: Int) {
    override fun toString(): String {
        return """PlayerId: ${playerHand.playerId}
        |Card1: ${"" + playerHand.holeCards.first.face + playerHand.holeCards.first.suit}
        |Card2: ${"" + playerHand.holeCards.second.face + playerHand.holeCards.second.suit}
        |Bet: ${playerHand.bet}
        |1: ${rank shr 20}
        |2: ${rank shr 16 and 0b1111}
        |3: ${rank shr 12 and 0b1111}
        |4: ${rank shr 8 and 0b1111}
        |5: ${rank shr 4 and 0b1111}
        |6: ${rank and 0b1111}""".trimMargin()
    }
}