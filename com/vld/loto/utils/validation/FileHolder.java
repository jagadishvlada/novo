package com.vld.loto.utils.validation;

import java.io.File;

/**
 * Created by 803064 on 17/07/2018.
 */
public class FileHolder {
    private File csvFile;
    private String path = "C:\\Users\\803064\\workspace\\vld_workspaces\\";
    private String monthPath = "out\\july\\";
    @SuppressWarnings("FieldCanBeLocal")
    private String binPath = "bin\\";
    @SuppressWarnings("FieldCanBeLocal")
    private String fileName = "July3";
    @SuppressWarnings("FieldCanBeLocal")
    private String fileExtensionCsv = ".csv";
    private String fileExtensionSorted = ".sorted";
    private String fileExtensionOut = ".out";
    private String sortedMainBalls = fileName + fileExtensionSorted;
    @SuppressWarnings("FieldCanBeLocal")
    private String sortMainBalls = "sortMainBalls.sh";
    private String fileExtensionLuckyStars = "LuckyStars";
    private String sortedLuckyStarz = fileName + fileExtensionLuckyStars + fileExtensionSorted;
    private String mainBallsCombinationsPrintout = fileName + fileExtensionOut;
    private String luckyStarsCombinationsPrintout = fileName + fileExtensionLuckyStars + fileExtensionOut;
    @SuppressWarnings("FieldCanBeLocal")
    private String outputTxt = "output.txt";

    public String getSortMainBalls() {
        return sortMainBalls;
    }

    public String getOutputTxt() {
        return outputTxt;
    }


    public FileHolder() {
        this.csvFile = new File(path + monthPath + fileName + fileExtensionCsv);
    }

    public File getCsvFile1() {
        return this.csvFile;
    }

    public String getCsvFile() {
        return path + monthPath + fileName + fileExtensionCsv;
    }

    public String getPath() {
        return path;
    }

    public String getBinPath() {
        return binPath;
    }

    public String getSortedMainBalls() {
        return path + monthPath + sortedMainBalls;
    }

    public String getSortLuckyStarz() {
        return path + monthPath + sortedLuckyStarz;
    }

    public String getMainBallsCombinationsPrintout() {
        return path + monthPath + mainBallsCombinationsPrintout;
    }


    public String getLuckyStarsCombinationsPrintout() {
        return luckyStarsCombinationsPrintout;
    }
}
