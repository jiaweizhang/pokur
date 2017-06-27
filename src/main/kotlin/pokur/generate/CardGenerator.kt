package pokur.generate

import pokur.Card
import java.util.*
import java.util.stream.Collectors
import java.util.stream.IntStream

/**
 * Created by jiaweizhang on 6/19/2017.
 */

private val RANDOM = Random()

class CardGenerator {

    /**
     * Equal performance in all count requests
     */
    fun generateCardsByShuffle(count: Int): List<Card> {
        val allCards = IntStream.range(0, 52).boxed().collect(Collectors.toList())
        Collections.shuffle(allCards)
        return allCards.subList(0, count).map {
            val suit = when (it / 13) {
                0 -> 'S'
                1 -> 'H'
                2 -> 'C'
                else -> 'D'
            }
            Card(it % 13, suit)
        }
    }

    private fun generateCardsHelper(alreadySeen: MutableSet<Int>, count: Int): List<Card> {
        val randomCards = mutableListOf<Card>()
        while (randomCards.size < count) {
            val randomInt = RANDOM.nextInt(52)
            if (!alreadySeen.contains(randomInt)) {
                alreadySeen.add(randomInt)
                randomCards.add(Card(randomInt % 13,
                        when (randomInt / 13) {
                            0 -> 'S'
                            1 -> 'H'
                            2 -> 'C'
                            else -> 'D'
                        }))
            }
        }
        return randomCards
    }

    /**
     * Set generation is better unless a lot (more than half of deck) is generated
     */
    fun generateCardsBySet(count: Int): List<Card> {
        return generateCardsHelper(mutableSetOf<Int>(), count)
    }

    fun generateCardsBySet(exclusions: List<Card>, count: Int): List<Card> {
        return generateCardsHelper(mutableSetOf<Int>(exclusions[0].toInt(), exclusions[1].toInt()), count)
    }
}