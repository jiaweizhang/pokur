import io.kotlintest.specs.StringSpec
import pokur.generate.CardGenerator

/**
 * Created by jiaweizhang on 6/19/2017.
 */

class GenerateSpeedTests : StringSpec() {

    init {
        val cg = CardGenerator()

        "Generate 5 cards 100000 times" {
            val shuffleStartTime = System.currentTimeMillis()
            for (i in 1..100000) {
                cg.generateCardsByShuffle(5)
            }
            val shuffleEndTime = System.currentTimeMillis()

            val setStartTime = System.currentTimeMillis()
            for (i in 1..100000) {
                cg.generateCardsBySet(5)
            }
            val setEndTime = System.currentTimeMillis()

            println("Shuffle 5: ${shuffleEndTime - shuffleStartTime}ms")
            println("Set     5: ${setEndTime - setStartTime}ms")
        }

        "Generate 7 cards 100000 times" {
            val shuffleStartTime = System.currentTimeMillis()
            for (i in 1..100000) {
                cg.generateCardsByShuffle(7)
            }
            val shuffleEndTime = System.currentTimeMillis()

            val setStartTime = System.currentTimeMillis()
            for (i in 1..100000) {
                cg.generateCardsBySet(7)
            }
            val setEndTime = System.currentTimeMillis()

            println("Shuffle 7: ${shuffleEndTime - shuffleStartTime}ms")
            println("Set     7: ${setEndTime - setStartTime}ms")
        }

        "Generate 9 cards 100000 times" {
            val shuffleStartTime = System.currentTimeMillis()
            for (i in 1..100000) {
                cg.generateCardsByShuffle(9)
            }
            val shuffleEndTime = System.currentTimeMillis()

            val setStartTime = System.currentTimeMillis()
            for (i in 1..100000) {
                cg.generateCardsBySet(9)
            }
            val setEndTime = System.currentTimeMillis()

            println("Shuffle 9: ${shuffleEndTime - shuffleStartTime}ms")
            println("Set     9: ${setEndTime - setStartTime}ms")
        }

        "Generate 15 cards 100000 times" {
            val shuffleStartTime = System.currentTimeMillis()
            for (i in 1..100000) {
                cg.generateCardsByShuffle(15)
            }
            val shuffleEndTime = System.currentTimeMillis()

            val setStartTime = System.currentTimeMillis()
            for (i in 1..100000) {
                cg.generateCardsBySet(15)
            }
            val setEndTime = System.currentTimeMillis()

            println("Shuffle 15: ${shuffleEndTime - shuffleStartTime}ms")
            println("Set     15: ${setEndTime - setStartTime}ms")
        }

        "Generate 21 cards 100000 times" {
            val shuffleStartTime = System.currentTimeMillis()
            for (i in 1..100000) {
                cg.generateCardsByShuffle(21)
            }
            val shuffleEndTime = System.currentTimeMillis()

            val setStartTime = System.currentTimeMillis()
            for (i in 1..100000) {
                cg.generateCardsBySet(21)
            }
            val setEndTime = System.currentTimeMillis()

            println("Shuffle 21: ${shuffleEndTime - shuffleStartTime}ms")
            println("Set     21: ${setEndTime - setStartTime}ms")
        }

        "Generate 23 cards 100000 times" {
            val shuffleStartTime = System.currentTimeMillis()
            for (i in 1..100000) {
                cg.generateCardsByShuffle(23)
            }
            val shuffleEndTime = System.currentTimeMillis()

            val setStartTime = System.currentTimeMillis()
            for (i in 1..100000) {
                cg.generateCardsBySet(23)
            }
            val setEndTime = System.currentTimeMillis()

            println("Shuffle 23: ${shuffleEndTime - shuffleStartTime}ms")
            println("Set     23: ${setEndTime - setStartTime}ms")
        }
    }
}