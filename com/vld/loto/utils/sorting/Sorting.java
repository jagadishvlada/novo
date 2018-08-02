package com.vld.loto.utils.sorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by 803064 on 12/07/2018.
 */
public class Sorting {

    //    public List<Integer> getSortedCombo (List<Integer> rawList, int size){
    public Integer[] getSortedCombo(Integer[] rawArray) {
        List<Integer> sortedCombo = new ArrayList<>();

        int tmp = 0;
        int size = rawArray.length;
        for (int i = 0; i < size; i++) {
            for (int j = 1; j < (size - i); j++) {
                if (rawArray[j - 1] > rawArray[j]) {
                    tmp = rawArray[j - 1];
                    rawArray[j - 1] = rawArray[j];
                    rawArray[j] = tmp;
                }
            }
        }
        return rawArray;
    }

    public List batchSortMainBalls(List<String> rawMainBalls){
        List<String> sortedMainBalls = new ArrayList<>();

        Collections.sort(rawMainBalls);
        System.out.println(rawMainBalls);
        return sortedMainBalls;
    }
}
