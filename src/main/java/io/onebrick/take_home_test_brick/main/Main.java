package io.onebrick.take_home_test_brick.main;

import io.onebrick.take_home_test_brick.service.ScrapeService;
import io.onebrick.take_home_test_brick.service.ScrapeServiceImpl;
import io.onebrick.take_home_test_brick.service.TokopediaPageService;
import io.onebrick.take_home_test_brick.service.TokopediaPageServiceImpl;
import io.onebrick.take_home_test_brick.util.CSVUtil;

import java.io.IOException;
import java.util.List;

public class Main {

    private static final String TOKOPEDIA_URL = "https://www.tokopedia.com/p/handphone-tablet/handphone?page=";
    private static final String CSV_FILE_NAME = "test.csv";

    public static void main(String[] args) {
        try {
            ScrapeService scrapeService = new ScrapeServiceImpl();
            TokopediaPageService tokopediaPageService = new TokopediaPageServiceImpl(scrapeService);
            List<String[]> csvData = tokopediaPageService.scrapeTokopediaPage(TOKOPEDIA_URL);
            new CSVUtil().printCSV(CSV_FILE_NAME, csvData);
        } catch (IOException e) {
            System.out.println("An error occurred: " + e);
        }
    }

}
