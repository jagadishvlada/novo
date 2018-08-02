package com.vld.loto;

import com.vld.loto.processor.Combine;
import com.vld.loto.processor.RandomNumbersGenerator;
import com.vld.loto.utils.sorting.ReadAndSort;
import com.vld.loto.utils.write.WriteFile;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by 803064 on 12/07/2018.
 */
public class MainApplication {
    private static final int NUMBER_OF_COMBINATIONS_ATTEMPTED = 13;
    // the number of most frequently appearing balls, to be used as the size when constructing the wheeling/shorthand system
    // possibly needs to be left a bit longer than the bare minimum intended to be played
    /**
     * pool size, the size of shorthand system to be played
     */
    private static final int POOL = 7;
    /**
     * Total number of combinations derived out of the pool of numbers
     */
    private static final int KOMB = 5;
    /**
     * Randomly selected smaller number of combinations derived out of the pool of numbers
     */
    private static final int SHORTHAND_SYSTEM_SIZE = 3;
    private static final String NEW_LINE = "\n";
    private static final String TRIPLE_SPACE = "   ";

    public static void main(String[] args) throws IOException {

        RandomNumbersGenerator randomNumbersGenerator = new RandomNumbersGenerator();

        BufferedWriter writer = null;

        // generate combinations and write them to a <fileName>.csv file
        for (int i = 0; i < NUMBER_OF_COMBINATIONS_ATTEMPTED; i++) {
            mainWorx(randomNumbersGenerator);
        }

        ReadAndSort readAndSort = new ReadAndSort();
        Map<String, Integer> mainBallNumbers = readAndSort.readMainNumbers();

        WriteFile writeFile = new WriteFile();
        writeFile.writeSortedMainBalls(mainBallNumbers, POOL);

        Map<String, Integer> luckyStarNumbers = readAndSort.readLuckyStars();

        if (POOL % 2 == 0) {
            writeFile.writeSortedLuckyStars(luckyStarNumbers, POOL / 2);
        } else {
            writeFile.writeSortedLuckyStars(luckyStarNumbers, POOL / 2 + 1);
        }
        readAndSort.getBallsCombinationsForTicket();
//        new MainApplication().displayCombinations();
        new MainApplication().writeCombinationsToFile();
    }


    //    VLD TODO look into changing the method signature, i.e. NOT passing any arguments to it
    private static void mainWorx(RandomNumbersGenerator randomNumbersGenerator) {

        String toBeWritten = randomNumbersGenerator.getRandomMainBalls();
        if (toBeWritten.length() > 12) {
            String lStarz = randomNumbersGenerator.getRandomLuckyStars();
            // If lucky stars failed validation, not even the main string is written to the file
            // lStars has to be at least 4 chars long, i.e. "x, y" // VLD  TODO sort out the Lucky stars
            if (lStarz.length() >= 4) {
                toBeWritten = toBeWritten + lStarz + "\n";
                new WriteFile().writeToMainCsvFile(toBeWritten);
            }
        }
    }

    /**
     * so now we have produced the top 6 list, need to distribute them into playable combinations
     */

    /**
     * debug/testing method
     */
    private void displayCombinations() {
        System.out.println("MainClass.displayCombinations");
        List<String> displayedCombination = new ReadAndSort().getBallsCombinationsForTicket();

        ArrayList<ArrayList<Integer>> shorthandIndicesSubset = new Combine().getShorthandSubsetPub(POOL, KOMB, SHORTHAND_SYSTEM_SIZE);

        // assign indices to aNumber
        for (ArrayList<Integer> aCombination : shorthandIndicesSubset) {
            int counter = 0;
            for (Integer aBall : aCombination) {
                int index = aBall - 1;
                if (counter < (aCombination.size() - 1)) {
                    System.out.print(displayedCombination.get(index) + ", ");
                } else {
                    System.out.print(displayedCombination.get(index));
                }
                counter++;
            }
            System.out.println();
        }
    }


    private void writeCombinationsToFile() {
        Map<String, Integer> shorthandSubsetParams = new HashMap<>();
        shorthandSubsetParams.put("POOL", POOL);
        shorthandSubsetParams.put("KOMB", KOMB);
        shorthandSubsetParams.put("SHORTHAND_SYSTEM_SIZE", SHORTHAND_SYSTEM_SIZE);

        Map<String, String> stringParams = new HashMap<>();
        stringParams.put("TRIPLE_SPACE", TRIPLE_SPACE);
        stringParams.put("NEW_LINE", NEW_LINE);

//        new WriteFile().writeCombinationsToFile(shorthandSubsetParams, stringParams);
        new WriteFile().writeCombinationsToFile(shorthandSubsetParams);
    }
}
