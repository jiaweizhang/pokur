package pokur.calculate

import pokur.Card
import pokur.generate.CardGenerator

/**
 * Created by jiaweizhang on 6/26/2017.
 */
class GeneralProcessor {

    private val iterations = 100000
    private val cg = CardGenerator()
    private val hp = HandProcessor()

    fun preFlopOdds(holeCards: List<Card>): Pair<Double, Double> {

        var winCount = 0
        var tieCount = 0

        for (i in 0..iterations) {
            val generatedCards = cg.generateCardsBySet(holeCards, 7)

            val yourHandValue = hp.findHandValue7Complex(listOf(holeCards[0], holeCards[1],
                    generatedCards[0], generatedCards[1], generatedCards[2], generatedCards[3], generatedCards[4]))
            val opponentHandValue = hp.findHandValue7Complex(generatedCards)

            if (yourHandValue > opponentHandValue) {
                winCount++
            } else if (yourHandValue == opponentHandValue) {
                tieCount++
            }
        }

        return Pair(winCount * 100.0 / iterations, tieCount * 100.0 / iterations)
    }
}