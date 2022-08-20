package io.onebrick.take_home_test_brick.service;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public interface ScrapeService {

    String scrapeText(Document page, String attributeName, String attributeValue);
    String scrapeAttribute(Element elPage, Document dPage, String attributeName, String attributeValue, String attributeKey);
    String scrapeTitle(Document page);
}
