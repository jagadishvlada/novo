package com.vld.loto.processor;

import com.vld.loto.utils.sorting.Sorting;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by 803064 on 12/07/2018.
 */
public class RandomNumbersGenerator {

    /**
     * @return String containing main combination numbers
     */
    public String getRandomMainBalls() {
        Integer min = 1;
        Integer max = 50;
        Integer mainSize = 5;


        Set<Integer> nasumzGl = new HashSet<>();

        int setSize = 5;
        while (setSize > 0) {
            nasumzGl.add(new Random().nextInt(max - min + 1) + min);
            setSize--;
        }

        Integer[] mainArray = nasumzGl.toArray(new Integer[0]);

        // sort them ascending
        Sorting sort = new Sorting();
        mainArray = sort.getSortedCombo(mainArray);

        StringBuilder mainBallsStringBuilder = new StringBuilder();
        if (mainArray.length == mainSize) {
            for (int i = 0; i < mainSize; i++) {
                mainBallsStringBuilder.append(mainArray[i]).append(", ");
            }
        }

        return mainBallsStringBuilder.toString();
    }

    /**
     * @return String containing lucky star numbers
     */
    public String getRandomLuckyStars() {
        Integer min = 1;
        Integer max = 12;

        Set<Integer> randomLuckyStars = new HashSet<>();

        int setSize = 2;
        int numberOfStars = 2;
        while (setSize > 0) {
            randomLuckyStars.add(new Random().nextInt(max - min + 1) + min);
            setSize--;
        }

        Integer[] mainArray = randomLuckyStars.toArray(new Integer[0]);
        // sort them in ascending order
        Sorting sort = new Sorting();
        mainArray = sort.getSortedCombo(mainArray);

        StringBuilder sb = new StringBuilder("  ");
        if (mainArray.length == numberOfStars) {
            sb.append(mainArray[0]).append(", ").append(mainArray[1]);
        }
        return sb.toString();
    }

    /**
     *
     * @param max
     * @param shorthandSystemSize
     * @return Set of indices, as uniqueness needed
     */
    public Set getRandomIndices(int max, int shorthandSystemSize) {
        int min = 1;
        Set<Integer> randomIndices = new HashSet<>();
        while (shorthandSystemSize >= 0) {
            randomIndices.add(new Random().nextInt(max - min + 1) + min);
            shorthandSystemSize--;
        }
        return randomIndices;
    }
}
