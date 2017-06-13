package pokur.calculate

import pokur.Card

/**
 * Created by jiaweizhang on 6/12/2017.
 */

class PlayerHand(val playerId: Long, val holeCards: Pair<Card, Card>, val bet: Long, var rank: Int = 0)

