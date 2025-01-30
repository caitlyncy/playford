package com.example.camlynplayfordproject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class RssFeedParser {
    public List<Article> fetchAndParse(String urlString) {
        List<Article> articles = new ArrayList<>();
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(inputStream, null);
            int eventType = parser.getEventType();
            Article currentArticle = null;
            String text = "";
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("item".equalsIgnoreCase(tagName)) {
                            currentArticle = new Article();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (currentArticle != null) {
                            if ("title".equalsIgnoreCase(tagName)) {
                                currentArticle.setTitle(text);
                            } else if ("link".equalsIgnoreCase(tagName)) {
                                currentArticle.setLink(text);
                            } else if ("description".equalsIgnoreCase(tagName)) {
                                currentArticle.setDescription(text);
                            } else if ("pubDate".equalsIgnoreCase(tagName)) {
                                currentArticle.setPubDate(text);
                            } else if ("item".equalsIgnoreCase(tagName)) {
                                articles.add(currentArticle);
                            }
                        }
                        break;
                }
                eventType = parser.next();
            }
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return articles;
    }
}
