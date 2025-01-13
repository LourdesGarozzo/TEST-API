package com.example.test.controller;
import com.example.test.services.SearchNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.test.repository.ApiKeyRepository;
import com.example.test.utils.FormatConverter;
import com.example.test.model.ApiKey;
import com.example.test.services.ApiKeyServiceImpl;

@RestController
@RequestMapping("/api/search-news")

public class SearchNewsController {

    @Autowired
    private SearchNewsService searchNewsService;

    @Autowired
    private ApiKeyRepository apiKeyRepository;

    @Autowired
    private ApiKeyServiceImpl apiKeyService;

    public SearchNewsController(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    @GetMapping(path = "/search", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.TEXT_PLAIN_VALUE,
            MediaType.TEXT_HTML_VALUE})

    public ResponseEntity<?> searchNews(@RequestHeader(value = "X-API-KEY") String apiKey,
                                        @RequestHeader(value = "X-SIGNATURE") String signature,
                                        @RequestParam int webPage,
                                        @RequestParam int offset,
                                        @RequestParam String searchQuery,
                                        @RequestParam(defaultValue = "false") boolean f,
                                        @RequestHeader(value = "Accept", defaultValue = MediaType.APPLICATION_JSON_VALUE) String acceptHeader) {
        try {

            ApiKey apiKeyEntity = apiKeyRepository.findByKey(apiKey);

            if (apiKeyEntity == null) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("{\"codigo\": \"g103\", \"error\": \"No autorizado\"}");
            }

            String secretKey = apiKeyEntity.getSecretKey();
            String expectedSignature = apiKeyService.generateSignature(apiKey, secretKey);

            if (!signature.equals(expectedSignature)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("{\"codigo\": \"g104\", \"error\": \"Firma inválida\"}");
            }

            String jsonOutput = searchNewsService.extractInfomation(webPage, offset, searchQuery, f);

            if (jsonOutput.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"codigo\": \"g267\", \"error\": \"No se encuentran noticias para el texto: " + searchQuery + "\"}");
            }

            if (acceptHeader.contains(MediaType.APPLICATION_XML_VALUE)) {
                String xmlOutput = FormatConverter.convertJsonToXml(jsonOutput);
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_XML)
                        .body(xmlOutput);
            } else if (acceptHeader.contains(MediaType.TEXT_PLAIN_VALUE)) {
                String plainTextOutput = FormatConverter.convertJsonToPlainText(jsonOutput);
                return ResponseEntity.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .body(plainTextOutput);
            } else if (acceptHeader.contains(MediaType.TEXT_HTML_VALUE)) {
                String htmlOutput = FormatConverter.convertJsonToHtml(jsonOutput);
                return ResponseEntity.ok()
                        .contentType(MediaType.TEXT_HTML)
                        .body(htmlOutput);
            } else {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(jsonOutput);
            }

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"codigo\": \"g268\", \"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"codigo\": \"g100\", \"error\": \"Error interno del servidor\"}");
        }
    }

}
