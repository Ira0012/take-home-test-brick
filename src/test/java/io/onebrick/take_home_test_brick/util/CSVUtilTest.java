package io.onebrick.take_home_test_brick.util;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CSVUtilTest {

    private final CSVUtil csvUtil = new CSVUtil();

    @Test
    void printCSV() throws IOException {
        String[] data = new String[7];
        data[0] = "No.";
        data[1] = "Name";
        data[2] = "Description";
        data[3] = "Image Link";
        data[4] = "Price";
        data[5] = "Rating";
        data[6] = "Shop Name";
        List<String[]> dataLines = new ArrayList<>();
        dataLines.add(data);
        csvUtil.printCSV("testData.csv", dataLines);
        File csvOutputFile = new File("testData.csv");
        assertTrue(csvOutputFile.exists());
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(csvOutputFile))) {
            String line = bufferedReader.readLine();
            assertEquals("No.,Name,Description,Image Link,Price,Rating,Shop Name", line);
        }
    }
}