package io.onebrick.take_home_test_brick.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TokopediaPageServiceImpl implements TokopediaPageService {

    private static final String DATA_TEST_ID = "data-testid";

    private final ScrapeService scrapeService;

    public TokopediaPageServiceImpl(ScrapeService scrapeService) {
        this.scrapeService = scrapeService;
    }

    @Override
    public List<String[]> scrapeTokopediaPage(String url) throws IOException {
        int count = 1;
        int pgCnt = 1;
        List<String[]> csvData = new ArrayList<>();
        csvData.add(generateHeader());
        while (count <= 100) {
            Document page = Jsoup.connect(url + pgCnt).get();
            Elements aElements = page.getElementsByAttributeValue(DATA_TEST_ID, "lstCL2ProductList").get(0).getElementsByTag("a");
            for (Element el : aElements) {
                String href = el.attr("href");
                if (href.startsWith("https://ta.tokopedia.com")) {
                    continue;
                }

                Document productPage = Jsoup.connect(href).get();

                String[] toBePrintedToCsv = new String[7];
                toBePrintedToCsv[0] = count + ".";
                toBePrintedToCsv[1] = scrapeService.scrapeText(productPage, DATA_TEST_ID, "lblPDPDetailProductName");
                toBePrintedToCsv[2] = scrapeService.scrapeText(productPage, DATA_TEST_ID, "lblPDPDescriptionProduk");
                toBePrintedToCsv[3] = scrapeService.scrapeAttribute(null, productPage, DATA_TEST_ID, "PDPMainImage", "src");
                toBePrintedToCsv[4] = scrapeService.scrapeText(productPage, DATA_TEST_ID, "lblPDPDetailProductPrice");
                toBePrintedToCsv[5] = scrapeService.scrapeAttribute(productPage.head(), null, "itemprop", "ratingValue", "content");
                toBePrintedToCsv[6] = scrapeService.scrapeTitle(productPage);

                csvData.add(toBePrintedToCsv);

                count++;

                if (count == 101) {
                    break;
                }
            }
            pgCnt++;
        }

        return csvData;
    }

    private String[] generateHeader() {
        String[] header = new String[7];
        header[0] = "No.";
        header[1] = "Name";
        header[2] = "Description";
        header[3] = "Image Link";
        header[4] = "Price";
        header[5] = "Rating";
        header[6] = "Shop Name";
        return header;
    }
}
