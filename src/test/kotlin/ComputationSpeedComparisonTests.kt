import io.kotlintest.specs.StringSpec
import pokur.Card
import pokur.calculate.HandProcessor
import pokur.generate.CardGenerator

/**
 * Created by jiaweizhang on 6/19/2017.
 */
class ComputationSpeedComparisonTests : StringSpec() {

    init {
        val hp = HandProcessor()
        val cg = CardGenerator()

        "Compare 7 using different methods" {
            val sevenCardsList = mutableListOf<List<Card>>()
            for (i in 1..100000) {
                val sevenCards = cg.generateCardsByShuffle(7)
                sevenCardsList.add(sevenCards)
            }

            val complexStartTime = System.currentTimeMillis()
            for (sevenCards in sevenCardsList) {
                hp.findHandValue7Complex(sevenCards)
            }
            val complexEndTime = System.currentTimeMillis()

            val using5StartTime = System.currentTimeMillis()
            for (sevenCards in sevenCardsList) {
                hp.findHandValue7Using5(sevenCards)
            }
            val using5EndTime = System.currentTimeMillis()

            println("Complex time: ${complexEndTime - complexStartTime}")
            println("Using5  time: ${using5EndTime - using5StartTime}")
        }
    }
}