package io.onebrick.take_home_test_brick.service;

import java.io.IOException;
import java.util.List;

public interface TokopediaPageService {

    List<String[]> scrapeTokopediaPage(String url) throws IOException;
}
