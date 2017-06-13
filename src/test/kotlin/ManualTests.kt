import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec
import pokur.Card
import pokur.calculate.HandProcessor
import pokur.calculate.PlayerHand

/**
 * Created by jiaweizhang on 6/12/2017.
 */

class ManualTests : StringSpec() {
    init {

        "Q 4 3" {
            val communityCards = listOf(Card(12, 'S'), Card(12, 'D'), Card(12, 'H'), Card(12, 'C'), Card(8, 'S'))
            val playerHands = listOf(PlayerHand(1, Pair(Card(8, 'H'), Card(8, 'D')), 1000))
            val handProcessor = HandProcessor()
            handProcessor.rank(communityCards, playerHands).get(0).get(0).rank shouldBe ((8 shl 20) + (12 shl 16) + (8 shl 12))
        }

        "SF 12" {
            val communityCards = listOf(Card(12, 'S'), Card(11, 'S'), Card(10, 'S'), Card(9, 'S'), Card(8, 'S'))
            val playerHands = listOf(PlayerHand(1, Pair(Card(2, 'S'), Card(5, 'H')), 1000))
            val handProcessor = HandProcessor()
            handProcessor.rank(communityCards, playerHands).get(0).get(0).rank shouldBe ((9 shl 20) + (12 shl 16))
        }

    }
}