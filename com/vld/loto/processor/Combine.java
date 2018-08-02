package com.vld.loto.processor;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by 803064 on 17/07/2018.
 */
public class Combine {
    /**
     * @param n - size of pool from which the combinations are created
     * @param k - size of combination to be created out of n sized pool of numbers
     * @return ArrayList<ArrayList<Integer>> - list of lists, where each one dimension
     * list is a combination
     */
    protected ArrayList<ArrayList<Integer>> combine(int n, int k) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();

        if (n <= 0 || n < k)
            return result;

        ArrayList<Integer> item = new ArrayList<Integer>();
        doCombine(n, k, 1, item, result); // because it need to begin from 1

        return result;
    }

    private void doCombine(int n, int k, int start, ArrayList<Integer> item,
                           ArrayList<ArrayList<Integer>> res) {
        if (item.size() == k) {
            res.add(new ArrayList<Integer>(item));
            return;
        }

        for (int i = start; i <= n; i++) {
            item.add(i);
            doCombine(n, k, i + 1, item, res);
            item.remove(item.size() - 1);
        }
    }

    /*
     *  obtain a  smaller, random subset of array lists, so called "shorthand" system or "wheeling" system
     *  this method produces list of lists of keys for ball numbers. maybe fro lucky stars, too
     */
    protected ArrayList<ArrayList<Integer>> getShorthandSubset(int n, int k, int shorthandSystemSize) {
        ArrayList<ArrayList<Integer>> shorthandSubset = new ArrayList<ArrayList<Integer>>();
        //create full set of possible combinations
        ArrayList<ArrayList<Integer>> rawCombinatoins = combine(n, k);

        // invoke a random integer producing method
        //send full, return "system" list of combinations
        RandomNumbersGenerator randomNumbersGenerator = new RandomNumbersGenerator(); // VLD TODO - spring here! Inject the dependency, rather than explicit declaration!
//        int shorthandSystemSize = SHORTHAND_SYSTEM_SIZE;
        // passing parameter rawCombinatoins.size()-1 as the indices start from 0 and go up to maximum -1.
        // So if the maximum number comes as the result of randomization, it could cause IndexOutOfBoundsException
        HashSet<Integer> randomIndices = (HashSet) randomNumbersGenerator.getRandomIndices(rawCombinatoins.size() - 1, shorthandSystemSize);

        for (Integer i : randomIndices) {
            shorthandSubset.add(rawCombinatoins.get(i));
        }
        return shorthandSubset;
    }

    /**
     *
     * @param n
     * @param k
     * @return wheeling system of index combinations
     */
    public ArrayList<ArrayList<Integer>> getShorthandSubsetPub(int n, int k, int shorthandSystemSize ) {
        return getShorthandSubset(n, k, shorthandSystemSize);
    }

}
