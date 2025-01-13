package com.example.test.services;

import java.io.IOException;

public interface SearchNewsService {

    String getWebPage(int webPage, int offset, String searchQuery) throws IOException;

    String extractInfomation(int webPage, int offset, String searchQuery, boolean f) throws IOException;

}
