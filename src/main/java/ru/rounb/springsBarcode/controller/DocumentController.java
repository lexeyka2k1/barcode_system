package ru.rounb.springsBarcode.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import ru.rounb.springsBarcode.dto.DocumentInfoResponse;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.net.URI;
import java.net.URL;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    @GetMapping("/info")
    public ResponseEntity<DocumentInfoResponse> getDocumentInfo(@RequestParam String inventoryNumber,
                                                                @RequestParam(required = false, defaultValue = "1") int database) {
        try {
            // Вставляем inventoryNumber в запрос корректно (URL-кодировать стоит дополнительно)
         /*   String url = String.format(
                    "https://opac.rounb.ru/cgiopac/opacg/direct.exe?_version=2.7.0&_service=opacfindd.FindView&outformList%5B0%%5D/outform=SHOTFORM&length=1&query/body=(IN+%s%27%27)&userId=GUEST&session=1&iddb=1",
                    inventoryNumber
            ); */


            String url = "https://opac.rounb.ru/cgiopac/opacg/direct.exe?_version=2.7.0&_service=opacfindd.FindView&outformList%5B0%5D/outform=SHOTFORM&length=1&query/body=(IN+" + inventoryNumber + "%27%27)&userId=GUEST&session=1&iddb=" + database;

            URI uri = new URI(url);

            RestTemplate restTemplate = new RestTemplate();
            String xmlResponse = restTemplate.getForObject(uri, String.class);

            if (xmlResponse == null || xmlResponse.isEmpty()) {
                return ResponseEntity.status(502)
                        .body(new DocumentInfoResponse(inventoryNumber, "Пустой ответ от сервиса"));
            }

            String title = parseXmlForTitle(xmlResponse);

            return ResponseEntity.ok(
                    new DocumentInfoResponse(inventoryNumber, title != null ? title : "Название не найдено (проверьте выбранную базу данных)"));
        } catch (Exception e) {
            // Можно логировать: logger.error("Ошибка при получении данных", e);
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body(new DocumentInfoResponse(inventoryNumber, "Ошибка при получении данных"));
        }
    }

    private String parseXmlForTitle(String xml) throws Exception {
        if (xml == null || xml.isEmpty()) return null;

        System.out.println("XML RESPONSE:\n" + xml); // <-- временно для отладки

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(xml)));

        NodeList contentList = document.getElementsByTagName("content");
        if (contentList.getLength() == 0) {
            System.out.println("Нет тегов <content>");
            return null;
        }

        Element contentElement = (Element) contentList.item(0);
        NodeList entryList = contentElement.getElementsByTagName("entry");

        if (entryList.getLength() == 0) {
            System.out.println("Нет тегов <entry> внутри <content>");
            return null;
        }

        String text = entryList.item(0).getTextContent().trim();
        System.out.println("Получено название: " + text);
        return text;
    }

}
