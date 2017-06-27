import io.kotlintest.specs.StringSpec
import pokur.Card
import pokur.calculate.GeneralProcessor

/**
 * Created by jiaweizhang on 6/26/2017.
 */

class GeneralProcessorTest : StringSpec() {

    init {
        val gp = GeneralProcessor()

        "AA" {
            val holeCards = listOf(Card(12, 'S'), Card(12, 'H'))
            val results = gp.preFlopOdds(holeCards)
            println("AA")
            println("Win  prob: ${results.first}")
            println("Tie  prob: ${results.second}")
            println("Lose prob: ${100 - results.first - results.second}")
        }

        "AKs" {
            val holeCards = listOf(Card(12, 'S'), Card(11, 'S'))
            val results = gp.preFlopOdds(holeCards)
            println("AKs")
            println("Win  prob: ${results.first}")
            println("Tie  prob: ${results.second}")
            println("Lose prob: ${100 - results.first - results.second}")
        }

        "72o" {
            val holeCards = listOf(Card(0, 'S'), Card(5, 'H'))
            val results = gp.preFlopOdds(holeCards)
            println("72o")
            println("Win  prob: ${results.first}")
            println("Tie  prob: ${results.second}")
            println("Lose prob: ${100 - results.first - results.second}")
        }
    }
}