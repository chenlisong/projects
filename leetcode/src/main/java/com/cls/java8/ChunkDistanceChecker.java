package com.cls.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChunkDistanceChecker {
    private final String[] knownWords;

    public ChunkDistanceChecker(String[] knowns) {
        knownWords = knowns;
    }

    /**
     * Build list of checkers spanning word list.
     *
     * @param words
     * @param block
     * @return checkers
     */
    public static List<ChunkDistanceChecker> buildCheckers(String[] words, int block) {
        List<ChunkDistanceChecker> checkers = new ArrayList<>();
        for (int base = 0; base < words.length; base += block) {
            int length = Math.min(block, words.length - base);
            checkers.add(new ChunkDistanceChecker(Arrays.copyOfRange(words, base, base + length)));
        }
        return checkers;
    }
    /**
     * Find best distance from target to any known word.
     *
     * @param target
     * @return best
     */
//    public DistancePair bestDistance(String target) {
//        int[] v0 = new int[target.length() + 1];
//        int[] v1 = new int[target.length() + 1];
//        int bestIndex = -1;
//        int bestDistance = Integer.MAX_VALUE;
//        boolean single = false;
//        for (int i = 0; i < knownWords.length; i++) {
//            int distance = editDistance(target, knownWords[i], v0, v1);
//            if (bestDistance > distance) {
//                bestDistance = distance;
//                bestIndex = i;
//                single = true;
//            } else if (bestDistance == distance) {
//                single = false;
//            }
//        }
//        return single ? new DistancePair(bestDistance, knownWords[bestIndex]) :
//                new DistancePair(bestDistance);
//    }
}