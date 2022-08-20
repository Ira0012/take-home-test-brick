package io.onebrick.take_home_test_brick;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        int count = 1;
        int pgCnt = 1;
        List<String[]> csvData = new ArrayList<>();
        String[] header = new String[7];
        header[0] = "No.";
        header[1] = "Name";
        header[2] = "Description";
        header[3] = "Image Link";
        header[4] = "Price";
        header[5] = "Rating";
        header[6] = "Shop Name";
        csvData.add(header);
        try {
            while (count <= 100) {
                Document page = Jsoup.connect("https://www.tokopedia.com/p/handphone-tablet/handphone?page=" + pgCnt).get();
                Elements elements = page.getElementsByAttributeValue("data-testid", "lstCL2ProductList");
                Element element = elements.get(0);
                Elements aElements = element.getElementsByTag("a");
                for (Element el : aElements) {
                    String href = el.attr("href");
                    if (href.startsWith("https://ta.tokopedia.com")) {
                        continue;
                    }

                    Document productPage = Jsoup.connect(href).get();
                    String[] toBePrintedToCsv = new String[7];
                    toBePrintedToCsv[0] = count + ".";

                    Element productName = productPage.getElementsByAttributeValue("data-testid", "lblPDPDetailProductName").get(0);
                    String productNameText = productName.text();
                    toBePrintedToCsv[1] = productNameText;

                    Element productDescription = productPage.getElementsByAttributeValue("data-testid", "lblPDPDescriptionProduk").get(0);
                    String productDescriptionText = productDescription.text();
                    toBePrintedToCsv[2] = productDescriptionText;

                    Elements images = productPage.getElementsByAttributeValue("data-testid", "PDPMainImage");
                    Element image = images.get(0);
                    String imageHref = image.attr("src");
                    toBePrintedToCsv[3] = imageHref;

                    Element productPrice = productPage.getElementsByAttributeValue("data-testid", "lblPDPDetailProductPrice").get(0);
                    String productPriceText = productPrice.text();
                    toBePrintedToCsv[4] = productPriceText;

                    Element productRating = productPage.head().getElementsByAttributeValue("itemprop", "ratingValue").get(0);
                    toBePrintedToCsv[5] = productRating.attr("content");

                    String[] TokopediaTitle = productPage.title().split(" -");
                    String title = TokopediaTitle[TokopediaTitle.length - 1];
                    int lastIndexOfSeparator = title.indexOf(" | ");
                    toBePrintedToCsv[6] = title.substring(0, lastIndexOfSeparator).trim();

                    csvData.add(toBePrintedToCsv);

                    count++;
                    if (count == 101) {
                        break;
                    }
                }
                pgCnt++;
            }

            new Main().printCSV("test.csv", csvData);
        } catch (IOException e) {
            System.out.println("An error occurred: " + e);
        }
    }

    public String convertToCSV(String[] data) {
        return Stream.of(data)
                .map(this::escapeSpecialCharacters)
                .collect(Collectors.joining(","));
    }

    public String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }

    public void printCSV(String CSV_FILE_NAME, List<String[]> dataLines) throws IOException {
        File csvOutputFile = new File(CSV_FILE_NAME);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            dataLines.stream()
                    .map(this::convertToCSV)
                    .forEach(pw::println);
        }
    }
}
