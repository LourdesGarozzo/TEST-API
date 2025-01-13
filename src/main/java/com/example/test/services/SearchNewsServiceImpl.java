package com.example.test.services;

import com.example.test.model.News;
import com.example.test.utils.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;

@Service
public class SearchNewsServiceImpl implements SearchNewsService {

    WebClient webClient = new WebClient(BrowserVersion.CHROME);

    public String getWebPage(int webPage, int offset, String searchQuery) throws IOException {

        String url = "https://www.hoy.com.py/ajax.php?action=paginate&paged=" + webPage + "&offset=" + offset + "&s=" + searchQuery;

        webClient.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
        webClient.getOptions().setCssEnabled(false);
        webClient.getCookieManager().setCookiesEnabled(true);
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setAppletEnabled(false);
        webClient.getOptions().setPrintContentOnFailingStatusCode(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);

        return webClient.getPage(url).getWebResponse().getContentAsString();
    }


    public String extractInfomation(int webPage, int offset, String searchQuery, boolean f) throws IOException {

        List<News> news_array = new ArrayList<>();
        String base64Image = null;
        String contentTypeFoto = null;

        String responseContent = getWebPage(webPage,offset,searchQuery);

        if (responseContent.startsWith("{") || responseContent.startsWith("[")) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonResponse = objectMapper.readTree(responseContent);

            String outputHtml = jsonResponse.get("output").asText();

            HtmlPage page = webClient.getPage("data:text/html;charset=utf-8," + outputHtml);
            List<HtmlAnchor> noticias = page.getByXPath("//h3[@class='h-sb--lg list-entry-title']/a");

            for (HtmlAnchor noticia : noticias) {
                String articleLink = noticia.getHrefAttribute();
                HtmlPage articlePage = webClient.getPage(articleLink);

                String date = FormatConverter.convertToISO8601(articlePage.querySelector(".byline-content p.byline").getTextContent().trim());
                String title = articlePage.querySelector("h1.h-b--lg").getTextContent().trim();
                String resume = articlePage.querySelector(".lead--lg.hse-5").getTextContent().trim();

                HtmlElement imageElement = articlePage.querySelector(".gallery-item img");
                String imageLink = imageElement != null ? imageElement.getAttribute("src").trim() : "";

                if (imageLink != null && !imageLink.isEmpty() && f) {

                    InputStream imageStream = new URL(imageLink).openStream();

                    Tika tika = new Tika();
                    contentTypeFoto = tika.detect(imageStream);

                    base64Image = EncodeToBase64.encodeToBase64(imageLink);

                }

                News news = new News(date, articleLink, imageLink, title, resume, base64Image, contentTypeFoto);
                news_array.add(news);
            }

        } else {

            System.out.println("Error o fin de contenido: " + responseContent);
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

        return gson.toJson(news_array);
    }

}
