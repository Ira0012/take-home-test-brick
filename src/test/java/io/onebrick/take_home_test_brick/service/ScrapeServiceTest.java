package io.onebrick.take_home_test_brick.service;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScrapeServiceTest {

    private Document document;

    private ScrapeService scrapeService;

    @BeforeEach
    public void setup() {
        scrapeService = new ScrapeServiceImpl();
        document = new Document("localhost");
    }

    @Test
    void scrapeText() {
        Element element = document.createElement("div");
        element.attr("test", "test");
        element.text("testing");
        element.appendTo(document.body());
        assertEquals("testing", scrapeService.scrapeText(document, "test", "test"));
    }

    @Test
    void scrapeAttribute() {
        Element element = document.createElement("div");
        element.attr("test", "test");
        element.attr("key", "value");
        element.appendTo(document.body());
        assertEquals("value", scrapeService.scrapeAttribute(null, document, "test", "test", "key"));
        assertEquals("value", scrapeService.scrapeAttribute(element, null, "test", "test", "key"));
    }

    @Test
    void scrapeTitle() {
        document.title("Promo OPPO A95 8/128GB Smartphone (Garansi Resmi) - Starry Black -  - OPPO OFFICIAL STORE | Tokopedia");
        assertEquals("OPPO OFFICIAL STORE", scrapeService.scrapeTitle(document));
    }
}