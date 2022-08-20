package io.onebrick.take_home_test_brick.service;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ScrapeServiceImpl implements ScrapeService {

    @Override
    public String scrapeText(Document page, String attributeName, String attributeValue) {
        Element element = getElementByAttributeValue(page, null, attributeName, attributeValue);
        return element.text();
    }

    @Override
    public String scrapeAttribute(Element elPage, Document dPage, String attributeName, String attributeValue, String attributeKey) {
        Element element = getElementByAttributeValue(dPage, elPage, attributeName, attributeValue);
        return element.attr(attributeKey);
    }

    private Element getElementByAttributeValue(Document dPage, Element elPage, String attributeName, String attributeValue) {
        Elements element;
        if (elPage == null) {
            element = dPage.getElementsByAttributeValue(attributeName, attributeValue);
        } else {
            element = elPage.getElementsByAttributeValue(attributeName, attributeValue);
        }
        if (element.size() > 0) {
            return element.get(0);
        } else {
            return new Element("div");
        }
    }

    @Override
    public String scrapeTitle(Document page) {
        String[] titles = page.title().split(" -");
        String title = titles[titles.length - 1];
        int lastIndexOfSeparator = title.indexOf(" | ");
        return title.substring(0, lastIndexOfSeparator).trim();
    }
}
