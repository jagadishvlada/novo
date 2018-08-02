package com.vld.loto.utils.sorting;

import com.vld.loto.utils.validation.FileHolder;

import java.io.*;
import java.util.*;

/**
 * Created by 803064 on 17/07/2018.
 */
public class ReadAndSort {
    FileHolder FILE_HOLDER = new FileHolder();

    public static void main(String[] args) {

        ReadAndSort thisClass = new ReadAndSort();

        try {
            thisClass.readMainNumbers();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final String EQUAL_SIGN_TOKEN_DELIMITER = "=";

    public Map<String, Integer> readMainNumbers() throws IOException {
        File csvFile = new File(FILE_HOLDER.getCsvFile());
        List<String> aBallList = new ArrayList<>(); // fixme - check and remove
        Map<String, Integer> groupedBalls = new HashMap<>();
        BufferedReader csvFileBufferedReader = new BufferedReader(new FileReader(csvFile));

        ArrayList<String> mainBalls = new ArrayList();

        try {

            String line = csvFileBufferedReader.readLine();

            while (line != null) {
                mainBalls.addAll(getMainBalls(line));
                // by this stage we have main balls sorted and formatted with leading "0"
                // need to output them to a file
//                rawList.add(line);
                line = csvFileBufferedReader.readLine();
            }
            Collections.sort(mainBalls);
        } finally {
            csvFileBufferedReader.close();
        }
        groupedBalls = getNumberFrequency(mainBalls);
        // Compare and sort the balls based on frequeancy of their appearance,
        // i.e. based on values, rather than on the keys
        ValueComparator valueComparator = new ValueComparator(groupedBalls);
        TreeMap<String, Integer> countedAndSorted = new TreeMap<String, Integer>(valueComparator);
        countedAndSorted.putAll(groupedBalls);

        return countedAndSorted;
    }

    public Map<String, Integer> readLuckyStars() throws IOException {
        File csvFile = new File(FILE_HOLDER.getCsvFile());
//        List<String> luckyStarList = new ArrayList<>();
        Map<String, Integer> groupedBalls = new HashMap<>();
        BufferedReader csvFileBufferedReader = new BufferedReader(new FileReader(csvFile));

        ArrayList<String> luckyStarList = new ArrayList();

        try {

            String line = csvFileBufferedReader.readLine();

            while (line != null) {
                luckyStarList.addAll(getLuckyStars(line));
                // by this stage we have main balls sorted and formatted with leading "0"
                // need to output them to a file
//                rawList.add(line);
                line = csvFileBufferedReader.readLine();
            }
            Collections.sort(luckyStarList);
        } finally {
            csvFileBufferedReader.close();
        }
        groupedBalls = getNumberFrequency(luckyStarList);
        // Compare and sort the balls based on frequeancy of their appearance,
        // i.e. based on values, rather than on the keys
        ValueComparator valueComparator = new ValueComparator(groupedBalls);
        TreeMap<String, Integer> countedAndSorted = new TreeMap<String, Integer>(valueComparator);
        countedAndSorted.putAll(groupedBalls);

        return countedAndSorted;
    }


    /**
     * @param line
     * @return list containing only main balls of the draw
     */
    private ArrayList<String> getMainBalls(String line) {
        // the nums come here already sorted
        ArrayList<String> mainBalls = new ArrayList<>();
        StringTokenizer str = new StringTokenizer(line);

        while (str.hasMoreElements()) {
            String aBall = str.nextToken();
            aBall = aBall.replace(",", "");
            if (aBall.length() < 2) {
                aBall = "0" + aBall;
            }
            if (mainBalls.size() < 5)
                mainBalls.add(aBall);
        }

        return mainBalls;
    }

    /**
     * @param line, i.e. combination
     * @return list containing only lucky stars of the draw
     */
    private ArrayList<String> getLuckyStars(String line) {
        // the nums come here already sorted
        ArrayList<String> allNumbersInLine = new ArrayList<>();
        ArrayList<String> luckyStars = new ArrayList<>();
        StringTokenizer str = new StringTokenizer(line);


        while (str.hasMoreElements()) {
            allNumbersInLine.add(str.nextToken());
        }

        for (int i = 5; i <= 6; i++) {
            String currentLuckyStar = allNumbersInLine.get(i);
            currentLuckyStar = currentLuckyStar.replace(",", "");

            if (currentLuckyStar.length() < 2) {
                luckyStars.add("0" + currentLuckyStar);
            } else {
                luckyStars.add(currentLuckyStar);
            }
        }
        return luckyStars;
    }


    private Set<String> getUniques(List<String> rawList) {
        Set<String> uniques = new HashSet<>();
        for (String uniq : rawList) {
            uniques.add(uniq);
        }
        return uniques;
    }


    private Map<String, Integer> getNumberFrequency(List<String> rawList) {
        Map<String, Integer> counted = new HashMap<String, Integer>();

        for (String s : rawList) {
            if (!counted.containsKey(s)) {
                counted.put(s, 1);
            } else {
                counted.put(s, counted.get(s) + 1);
            }
        }
        return counted;
    }


    public List getBallsCombinationsForTicket() {
        File sortedFile = new File(FILE_HOLDER.getSortedMainBalls());
        // get the main combination numbers
        List<String> ballNumbers = new ArrayList<>();

        BufferedReader mainBallsSortedBufferedReader = null;
        try {
            mainBallsSortedBufferedReader = new BufferedReader(new FileReader(sortedFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            String readLineContainingTopBall = mainBallsSortedBufferedReader.readLine();

            while (readLineContainingTopBall != null) {
                StringTokenizer str = new StringTokenizer(readLineContainingTopBall, EQUAL_SIGN_TOKEN_DELIMITER);
                ballNumbers.addAll(getMainBalls(str.nextToken()));
                readLineContainingTopBall = mainBallsSortedBufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace(); // VLD TODO improve error/exception logging
        } catch (IOException ie) {

        } finally {
            try {
                mainBallsSortedBufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ballNumbers;
    }

    /**
     * @return list of actual lucky star numbers, sorted in order of number of appearances
     */
    public List getLuckyStarsCombinationsForTicket() {
        List<String> luckyStars = new ArrayList<>();
        File sortedFile = new File(FILE_HOLDER.getSortLuckyStarz());

        BufferedReader luckyStarsSortedBufferedReader = null;
        try {
            luckyStarsSortedBufferedReader = new BufferedReader(new FileReader(sortedFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            String readLineContainingTopBall = luckyStarsSortedBufferedReader.readLine();

            while (readLineContainingTopBall != null) {
                StringTokenizer str = new StringTokenizer(readLineContainingTopBall, EQUAL_SIGN_TOKEN_DELIMITER);
                luckyStars.addAll(getMainBalls(str.nextToken()));
                readLineContainingTopBall = luckyStarsSortedBufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ie) {

        } finally {
            try {
                luckyStarsSortedBufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return luckyStars;
    }

}


/**
 * Auxiliary class for sorting maps based on the value, rather than on the key
 */
class ValueComparator implements Comparator<String> {
    Map<String, Integer> base;

    public ValueComparator(Map<String, Integer> base) {
        this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with equals.
    public int compare(String a, String b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys
    }

    public List<?> sortNumbers(List<?> rawList) {
        return new ArrayList<>();
    }


    public void quickSort(int[] inputArr) {

        int size = inputArr.length;
        quickSort(inputArr, 0, size - 1);
    }

    // unlike hackerRank solution, this method's signature
    // does not take int[] array as the first argument
    private void quickSort(int[] array, int lowerIndex, int higherIndex) {

        int left = lowerIndex;
        int right = higherIndex;
        // find out the  pivot number. It should be the value of approximately middle index number
        int pivot = array[lowerIndex + (higherIndex - lowerIndex) / 2];
        // Divide into two arrays
        while (left <= right) {
            /**
             * In each iteration, we will identify a number from left side which
             * is greater then the pivot value, and also we will identify a number
             * from right side which is less then the pivot value. Once the search
             * is done, then we exchange both numbers.
             */
            // if the current number on the left from the pivot is smaller then
            // the pivot, we just move the pointer towards the pivot...
            while (array[left] < pivot) {
                left++;
            }

            // if the current number on the right from the pivot is bigger then
            // the pivot, we just move the pointer closer towards the pivot...
            while (array[right] > pivot) {
                right--;
            }

            // every time we exited either of the while loops above, we have
            // the situation where current number is greater or smaller than
            // the pivot so we need to swap their places in the big array
            if (left <= right) {
                swap(array, left, right);
                //move index to next position on both sides
                left++;
                right--;
            }
        }
        // call quickSort() method recursively
        if (lowerIndex < right)
            quickSort(array, lowerIndex, right);
        if (left < higherIndex)
            quickSort(array, left, higherIndex);
    }

    private void swap(int[] array, int left, int right) {
        int temp = array[left];
        array[left] = array[right];
        array[right] = temp;
    }


}
