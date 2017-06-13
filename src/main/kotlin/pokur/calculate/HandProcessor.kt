package pokur.calculate

import pokur.Card

/**
 * Created by jiaweizhang on 6/12/2017.
 */

class HandProcessor() {
    fun getSevenCards(communityCards: List<Card>, holeCards: PlayerHand): List<Card> {
        return communityCards.plus(holeCards.holeCards.first).plus(holeCards.holeCards.second)
    }

    fun rank(communityCards: List<Card>, holeCards: List<PlayerHand>): List<List<RankingUnit>> {
        val handRanking = mutableListOf<List<RankingUnit>>()

        val temporaryRanking = mutableListOf<RankingUnit>()
        holeCards.forEach {
            val rank = findInt(getSevenCards(communityCards, it))
            temporaryRanking.add(RankingUnit(it, rank))
        }

        val rankingGroups = temporaryRanking.groupBy { it.rank }

        rankingGroups.keys.toSortedSet().reversed().mapTo(handRanking) { rankingGroups.getValue(it) }
        return handRanking
    }

    fun findInt(sevenCards: List<Card>): Int {
        var type = 0

        val sortedCards = sevenCards.sortedByDescending {
            it.face
        }

        val groupedCards = sevenCards.groupBy {
            it.face
        }

        val first = sortedCards.get(0).face
        val second = sortedCards.get(1).face
        val third = sortedCards.get(2).face
        val fourth = sortedCards.get(3).face
        val fifth = sortedCards.get(4).face
        val sixth = sortedCards.get(5).face
        val seventh = sortedCards.get(6).face

        var twoPairTopPair = -1
        var twoPairBottomPair = -1
        var twoPairKicker = -1

        var triple = -1
        var tripleKicker = -1
        var tripleKicker2 = -1

        var pair = -1
        var pairKicker = -1
        var pairKicker2 = -1
        var pairKicker3 = -1

        var straightHigh = 0
        var spadesCount = 0
        var heartsCount = 0
        var clubsCount = 0
        var diamondsCount = 0

        when (groupedCards.size) {
            2 -> {
                // 4 3 - Q
                // if count quad, cannot possibly be SF
                if (first == fourth) {
                    return (8 shl 20) + (first shl 16) + (fifth shl 12)
                } else {
                    return (8 shl 20) + (fourth shl 16) + (first shl 12)
                }
            }
            3 -> {
                // 4 2 1 - Q
                if (groupedCards.any { it.value.size == 4 }) {
                    if (first == second) {
                        if (second == third) {
                            // quad is top
                            return (8 shl 20) + (first shl 16) + (fifth shl 12)
                        } else {
                            // pair is top
                            if (third == fourth) {
                                // quad is after top
                                return (8 shl 20) + (third shl 16) + (first shl 12)
                            } else {
                                // quad is last
                                return (8 shl 20) + (fourth shl 16) + (first shl 12)
                            }
                        }
                    }
                }

                // 3 3 1 - FH
                else if (groupedCards.any { it.value.size == 1 }) {
                    if (first == second) {
                        // 3 3 1 or 3 1 3
                        if (fourth == fifth) {
                            // 3 3 1
                            return (7 shl 20) + (first shl 16) + (fourth shl 12)
                        } else {
                            // 3 1 3
                            return (7 shl 20) + (first shl 16) + (fifth shl 12)
                        }
                    } else {
                        // 1 3 3
                        return (7 shl 20) + (second shl 16) + (fifth shl 12)
                    }
                }

                // 3 2 2 - FH
                else {
                    if (second == third) {
                        // 3 2 2
                        return (7 shl 20) + (first shl 16) + (fourth shl 12)
                    } else if (fourth == fifth) {
                        // 2 3 2
                        return (7 shl 20) + (third shl 16) + (first shl 12)
                    } else {
                        // 2 2 3
                        return (7 shl 20) + (fifth shl 16) + (first shl 12)
                    }
                }
            }
            4 -> {
                // 4 1 1 1
                if (groupedCards.any { it.value.size == 4 }) {
                    if (first == second) {
                        // 4 1 1 1
                        return (8 shl 20) + (first shl 16) + (fifth shl 12)
                    } else if (second == third) {
                        // 1 4 1 1
                        return (8 shl 20) + (second shl 16) + (first shl 12)
                    } else if (third == fourth) {
                        // 1 1 4 1
                        return (8 shl 20) + (third shl 16) + (first shl 12)
                    } else {
                        // 1 1 1 4
                        return (8 shl 20) + (fourth shl 16) + (first shl 12)
                    }
                }

                // 3 2 1 1
                else if (groupedCards.any { it.value.size == 3 }) {
                    val advancedGroupedCards = sevenCards.groupingBy({
                        it.face
                    }).eachCount()

                    // triple and pair
                    var fhTriple = -1
                    var fhPair = -1

                    advancedGroupedCards.forEach {
                        if (it.value == 3) {
                            fhTriple = it.key
                        } else if (it.value == 2) {
                            fhPair = it.key
                        }
                    }
                    return (7 shl 20) + (fhTriple shl 16) + (fhPair shl 12)
                }

                // 2 2 2 1
                else {
                    type = 3
                    if (first != second) {
                        // 1 2 2 2
                        twoPairTopPair = second
                        twoPairBottomPair = fourth
                        twoPairKicker = first
                    } else if (sixth != seventh) {
                        // 2 2 2 1
                        twoPairTopPair = first
                        twoPairBottomPair = third
                        twoPairKicker = seventh
                    } else if (fourth != fifth) {
                        // 2 2 1 2
                        twoPairTopPair = first
                        twoPairBottomPair = third
                        twoPairKicker = fifth
                    } else {
                        // 2 1 2 2
                        twoPairTopPair = first
                        twoPairBottomPair = fourth
                        twoPairKicker = third
                    }
                }
            }
            5 -> {
                // 3 1 1 1 1
                if (groupedCards.any { it.value.size == 3 }) {
                    type = 4
                    if (first == second) {
                        // 3 1 1 1 1
                        triple = first
                        tripleKicker = fourth
                        tripleKicker2 = fifth
                    } else if (second == third) {
                        // 1 3 1 1 1
                        triple = second
                        tripleKicker = first
                        tripleKicker2 = fifth
                    } else if (third == fourth) {
                        // 1 1 3 1 1
                        triple = third
                        tripleKicker = first
                        tripleKicker2 = second
                    } else if (fourth == fifth) {
                        // 1 1 1 3 1
                        triple = fourth
                        tripleKicker = first
                        tripleKicker2 = second
                    } else {
                        // 1 1 1 1 3
                        triple = fifth
                        tripleKicker = first
                        tripleKicker2 = second
                    }
                }
                // 2 2 1 1 1

                else {
                    type = 3
                    if (first == second) {
                        twoPairTopPair = first
                        if (third == fourth) {
                            // 2 2 1 1 1
                            twoPairBottomPair = third
                            twoPairKicker = fifth
                        } else if (fourth == fifth) {
                            // 2 1 2 1 1
                            twoPairBottomPair = fourth
                            twoPairKicker = third
                        } else if (fifth == sixth) {
                            // 2 1 1 2 1
                            twoPairBottomPair = fifth
                            twoPairKicker = third
                        } else {
                            // 2 1 1 1 2
                            twoPairBottomPair = sixth
                            twoPairKicker = third
                        }
                    } else {
                        twoPairKicker = first
                        if (second == third) {
                            twoPairTopPair = second
                            if (fourth == fifth) {
                                // 1 2 2 1 1
                                twoPairBottomPair = fourth
                            } else if (fifth == sixth) {
                                // 1 2 1 2 1
                                twoPairBottomPair = fifth
                            } else {
                                // 1 2 1 1 2
                                twoPairBottomPair = sixth
                            }
                        } else if (third == fourth) {
                            twoPairTopPair = third
                            if (fifth == sixth) {
                                // 1 1 2 2 1
                                twoPairBottomPair = fifth
                            } else {
                                // 1 1 2 1 2
                                twoPairBottomPair = sixth
                            }
                        } else {
                            // 1 1 1 2 2
                            twoPairTopPair = fourth
                            twoPairBottomPair = sixth
                        }
                    }
                }
            }
            6 -> {
                type = 2
                if (first == second) {
                    // 2 1 1 1 1 1
                    pair = first
                    pairKicker = second
                    pairKicker2 = third
                    pairKicker3 = fourth
                } else if (second == third) {
                    // 1 2 1 1 1 1
                    pair = second
                    pairKicker = first
                    pairKicker2 = fourth
                    pairKicker3 = fifth
                } else if (third == fourth) {
                    // 1 1 2 1 1 1
                    pair = third
                    pairKicker = first
                    pairKicker2 = second
                    pairKicker3 = fifth
                } else if (fourth == fifth) {
                    // 1 1 1 2 1 1
                    pair = fourth
                    pairKicker = first
                    pairKicker2 = second
                    pairKicker3 = third
                } else if (fifth == sixth) {
                    // 1 1 1 1 2 1
                    pair = fifth
                    pairKicker = first
                    pairKicker2 = second
                    pairKicker3 = third
                } else {
                    // 1 1 1 1 1 2
                    pair = sixth
                    pairKicker = first
                    pairKicker2 = second
                    pairKicker3 = third
                }
            }
            else -> {
                type = 1
            }
        }

        var buckets = 0
        sevenCards.forEach {
            // fill suits - technically don't need to iterate all 7
            val suit = it.suit
            if (suit == 'S') {
                spadesCount++
            } else if (suit == 'H') {
                heartsCount++
            } else if (suit == 'C') {
                clubsCount++
            } else if (suit == 'D') {
                diamondsCount++
            }
        }
        // is flush
        val flushSuit = when {
            spadesCount >= 5 -> 'S'
            heartsCount >= 5 -> 'H'
            clubsCount >= 5 -> 'C'
            diamondsCount >= 5 -> 'D'
            else -> 'N'
        }

        // look for straight
        val distinctCards = sortedCards.distinctBy {
            it.face
        }


        if (distinctCards.size == 5) {
            if (distinctCards.get(0).face == distinctCards.get(4).face + 4) {
                // straight
                type = 5
                straightHigh = distinctCards.get(0).face
                if (findStraightFlush(flushSuit, straightHigh, groupedCards)) {
                    return (9 shl 20) + (straightHigh shl 16)
                }
            } else if ((distinctCards.get(1).face == 3)
                    and (distinctCards.get(4).face == 0)
                    and (distinctCards.get(0).face == 12)) {
                // 5-high straight
                type = 5
                straightHigh = 3
                if (findFiveHighStraightFlush(flushSuit, groupedCards)) {
                    return (9 shl 20) + (3 shl 16)
                }
            }
        } else if (distinctCards.size == 6) {
            if (distinctCards.get(0).face == distinctCards.get(4).face + 4) {
                // straight
                type = 5
                straightHigh = distinctCards.get(0).face
                if (findStraightFlush(flushSuit, straightHigh, groupedCards)) {
                    return (9 shl 20) + (straightHigh shl 16)
                }
            } else if (distinctCards.get(1).face == distinctCards.get(5).face + 4) {
                type = 5
                straightHigh = distinctCards.get(1).face
                if (findStraightFlush(flushSuit, straightHigh, groupedCards)) {
                    return (9 shl 20) + (straightHigh shl 16)
                }
            } else if ((distinctCards.get(2).face == 3)
                    and (distinctCards.get(5).face == 0)
                    and (distinctCards.get(0).face == 12)) {
                // 5-high straight
                type = 5
                straightHigh = 3
                if (findFiveHighStraightFlush(flushSuit, groupedCards)) {
                    return (9 shl 20) + (3 shl 16)
                }
            }
        } else {
            // size 7 - all different digits
            if (distinctCards.get(0).face == distinctCards.get(4).face + 4) {
                type = 5
                straightHigh = distinctCards.get(0).face
                if (findStraightFlush(flushSuit, straightHigh, groupedCards)) {
                    return (9 shl 20) + (straightHigh shl 16)
                }
            } else if (distinctCards.get(1).face == distinctCards.get(5).face + 4) {
                type = 5
                straightHigh = distinctCards.get(1).face
                if (findStraightFlush(flushSuit, straightHigh, groupedCards)) {
                    return (9 shl 20) + (straightHigh shl 16)
                }
            } else if (distinctCards.get(2).face == distinctCards.get(6).face + 4) {
                type = 5
                straightHigh = distinctCards.get(2).face
                if (findStraightFlush(flushSuit, straightHigh, groupedCards)) {
                    return (9 shl 20) + (straightHigh shl 16)
                }
            } else if ((distinctCards.get(3).face == 3)
                    and (distinctCards.get(5).face == 0)
                    and (distinctCards.get(0).face == 12)) {
                // 5-high straight
                type = 5
                straightHigh = 3
                if (findFiveHighStraightFlush(flushSuit, groupedCards)) {
                    return (9 shl 20) + (3 shl 16)
                }
            }

        }

        // SF, Q, and FH have returned
        // return flush
        if (flushSuit != 'N') {
            // get five highest of suit
            val sortedFlush = sortedCards.filter {
                it.suit == flushSuit
            }
            return (6 shl 20) + (sortedFlush.get(0).face shl 16) + (sortedFlush.get(1).face shl 12) + (sortedFlush.get(2).face shl 8) + (sortedFlush.get(3).face shl 4) + (sortedFlush.get(4).face)
        }

        // return straight
        if (type == 5) {
            return (5 shl 20) + (straightHigh shl 16)
        }

        // return triple
        if (type == 4) {
            return (4 shl 20) + (triple shl 16) + (tripleKicker shl 12) + (tripleKicker2 shl 8)
        }

        // two pair
        if (type == 3) {
            return (3 shl 20) + (twoPairTopPair shl 16) + (twoPairBottomPair shl 12) + (twoPairKicker shl 8)
        }

        // pair
        if (type == 2) {
            return (2 shl 20) + (pair shl 16) + (pairKicker shl 12) + (pairKicker2 shl 8) + (pairKicker3 shl 4)
        }

        if (type == 1) {
            return (1 shl 20) + (first shl 16) + (second shl 12) + (third shl 8) + (fourth shl 4) + fifth
        }

        throw Exception("Impossible type")
    }

    fun findStraightFlush(flushSuit: Char, straightHigh: Int, groupedCards: Map<Int, List<Card>>): Boolean {
        if (flushSuit != 'N') {
            // check series of 5 for flush

            var isStraightFlush = true
            for (i in straightHigh..straightHigh - 4) {
                var suitFoundInCurrent = false
                for (j in groupedCards.getValue(i)) {
                    if (j.suit == flushSuit) {
                        suitFoundInCurrent = true
                        break
                    }
                }
                if (!suitFoundInCurrent) {
                    isStraightFlush = false
                }
            }
            if (isStraightFlush) {
                return true
            }
        }
        return false
    }

    fun findFiveHighStraightFlush(flushSuit: Char, groupedCards: Map<Int, List<Card>>): Boolean {
        if (flushSuit != 'N') {
            var isStraightFlush = true
            for (i in 0..3) {
                var suitFoundInCurrent = false
                for (j in groupedCards.getValue(i)) {
                    if (j.suit == flushSuit) {
                        suitFoundInCurrent = true
                        break
                    }
                }
                if (!suitFoundInCurrent) {
                    isStraightFlush = false
                }
            }
            var aceSuitCorrect = false
            for (i in groupedCards.getValue(12)) {
                if (i.suit == flushSuit) {
                    aceSuitCorrect = true
                }
            }
            if (isStraightFlush and aceSuitCorrect) {
                return true
            }
        }
        return false
    }
}