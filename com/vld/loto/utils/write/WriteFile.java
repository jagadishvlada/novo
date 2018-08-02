package com.vld.loto.utils.write;

import com.vld.loto.processor.Combine;
import com.vld.loto.utils.sorting.ReadAndSort;
import com.vld.loto.utils.validation.FileHolder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by 803064 on 17/07/2018.
 */
public class WriteFile {
    private FileHolder FILE_HOLDER = new FileHolder();
     private static  final String COMMA = ", ";

    private static final String NEW_LINE = "\n";
    private static final String TRIPLE_SPACE = "   ";

    private BufferedWriter writer = null;

    public void writeToMainCsvFile(String toBeWritten) {
        try {
            File csvFile = new File(FILE_HOLDER.getCsvFile());
            writer = new BufferedWriter(new FileWriter(csvFile, true)); // append rows to the text file
            if (toBeWritten.length() > 12) {
                writer.write(toBeWritten);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


    /**
     * @param countedAndSortedBalls
     * @param mostFrequentBalls
     */

    public void writeSortedMainBalls(Map<String, Integer> countedAndSortedBalls, int mostFrequentBalls) {

        try {
            File sortedFile = new File(FILE_HOLDER.getSortedMainBalls());
            writer = new BufferedWriter(new FileWriter(sortedFile)); // do not append rows to the text file

            for (Map.Entry<String, Integer> mapEntry : countedAndSortedBalls.entrySet()) {
                if (mostFrequentBalls > 0) {
                    writer.write(mapEntry.toString() + "\n");
                } else {
                    return;
                }
                mostFrequentBalls--;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void writeSortedLuckyStars(Map<String, Integer> countedAndSortedBalls, int mostFrequentBalls) {
        try {
            File sortedLuckyStarsFile = new File(FILE_HOLDER.getSortLuckyStarz());
            // do not append rows to the text file, always overwrite it
            writer = new BufferedWriter(new FileWriter(sortedLuckyStarsFile));

            for (Map.Entry<String, Integer> mapEntry : countedAndSortedBalls.entrySet()) {
                if (mostFrequentBalls > 0) {
                    writer.write(mapEntry.toString() + "\n");
                }
                mostFrequentBalls--;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


    /**
     * create smaller subset of combinations
     *
     * @param shorthandSubset
     */
    public void writeMainBallsCombinationsPrintout(ArrayList<String> shorthandSubset) {
        try {
            File printoutFile = new File(FILE_HOLDER.getMainBallsCombinationsPrintout());
            writer = new BufferedWriter(new FileWriter(printoutFile)); // do not append rows to the text file

            // assign indices to aNumber
            for (String line : shorthandSubset) {
                writer.write(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


    public void writeCombinationsToFile(Map<String, Integer> intParams) {
        ReadAndSort readAndSort = new ReadAndSort();
        List<String> poolOfMainBallsNumbers = readAndSort.getBallsCombinationsForTicket();
        Collections.sort(poolOfMainBallsNumbers);

        int poolSize = intParams.get("POOL");
        // Obtaining set of indices, so that they can later be assigned proper ball numbers
        // Also, n always has to be greater than k
        ArrayList<ArrayList<Integer>> shorthandIndicesSubset =
                new Combine().getShorthandSubsetPub(poolSize, intParams.get("KOMB"),
                        intParams.get("SHORTHAND_SYSTEM_SIZE"));

        ArrayList<String> shorthandBallsSubset = new ArrayList<>();
        List<String> poolOfLuckyStarNumbers = readAndSort.getLuckyStarsCombinationsForTicket();
        Collections.sort(poolOfLuckyStarNumbers);

        ArrayList<ArrayList<Integer>> shorthandLuckyStarIndicesSubset =
//                new Combine().getShorthandSubsetPub(poolOfLuckyStarNumbers.size(), intParams.get("KOMB"),// VLD TODO canot be more than 2
                new Combine().getShorthandSubsetPub(poolOfLuckyStarNumbers.size(), 2,
                        intParams.get("SHORTHAND_SYSTEM_SIZE"));

        // assign indices to aNumber
        for (ArrayList<Integer> aCombination : shorthandIndicesSubset) {
            StringBuffer aBallsCombination = new StringBuffer();

            int counter = 0;
            String alreadyUsedLuckyStarsPair = "";
            for (Integer aBall : aCombination) {
                int index = aBall - 1;
                aBallsCombination.append(poolOfMainBallsNumbers.get(index))
                        .append(counter < 4 ? COMMA :   TRIPLE_SPACE  // strParams.get("TRIPLE_SPACE")
                                + getLuckyStarsCombination(shorthandLuckyStarIndicesSubset, poolOfLuckyStarNumbers, alreadyUsedLuckyStarsPair)
//                                + strParams.get("NEW_LINE")); // vld fixme see if strBuff apend can replace +
                                + NEW_LINE); // vld fixme see if strBuff apend can replace +
                // VLD TODO slap lucky stars
                counter++;
            }     /// TRIPLE_SPACE

            shorthandBallsSubset.add(aBallsCombination.toString());
        }
        new WriteFile().writeMainBallsCombinationsPrintout(shorthandBallsSubset);
    }

    private String getLuckyStarsCombination(ArrayList<ArrayList<Integer>> shorthandLuckyStarIndicesSubset,
                                            List<String> poolOfLuckyStarNumbers, String alreadyUsedLuckyStarsPair) {
        StringBuffer luckyStarsPair = new StringBuffer();

        for (ArrayList<Integer> luckyStarsIndicesPair : shorthandLuckyStarIndicesSubset) {
            int counter = 0;

            for (Integer luckyStarIndex : luckyStarsIndicesPair){
                luckyStarsPair.append(poolOfLuckyStarNumbers.get(luckyStarIndex - 1))
                        .append(counter < 1 ? COMMA : "  ");
                counter++;
            }
        }

        String luckyStarsPairString = luckyStarsPair.toString();

        return luckyStarsPairString;
    }
}
