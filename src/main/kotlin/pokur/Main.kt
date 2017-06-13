package pokur

import pokur.calculate.HandProcessor
import pokur.calculate.PlayerHand

fun main(args: Array<String>) {
    println("Poker is really fun.")
    val communityCards = listOf(Card(12, 'S'), Card(12, 'H'), Card(12, 'D'), Card(12, 'C'), Card(8, 'S'))
    val playerHands = listOf(PlayerHand(1, Pair(Card(8, 'H'), Card(8, 'D')), 1000))
    val handProcessor = HandProcessor()
    for (i in handProcessor.rank(communityCards, playerHands)) {
        println("Group:")
        for (j in i) {
            println(j.toString())
        }
    }
}
