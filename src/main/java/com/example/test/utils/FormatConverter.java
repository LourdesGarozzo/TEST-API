package com.example.test.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class FormatConverter {

    public static String convertJsonToXml(String json) {
        // Convierte JSON a XML usando una biblioteca como Jackson o Gson
        // Ejemplo básico:
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><data>" + json + "</data>";
    }

    public static String convertJsonToPlainText(String json) {
        // Convierte JSON a texto plano (puedes adaptar el formato si lo necesitas)
        return json.replace("{", "").replace("}", "").replace("\"", "");
    }

    public static String convertJsonToHtml(String json) {
        // Convierte JSON a HTML simple
        return "<html><body><pre>" + json + "</pre></body></html>";
    }

    public static String convertToISO8601(String date) {
        try {

            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy HH:mm", Locale.forLanguageTag("es"));
            DateTimeFormatter outputFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            LocalDateTime dateTime = LocalDateTime.parse(date, inputFormatter);

            return dateTime.format(outputFormatter);

        } catch (Exception e) {

            System.err.println("Error al convertir la fecha: " + date);
            return date;
        }
    }
}
