package ru.mtuci;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    private static int depth;

    private static Queue<URLDepthPair> urls = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String siteUrl = reader.readLine();
        depth = Integer.parseInt(reader.readLine());

        Queue<URLDepthPair> links = new LinkedList<>();
        links.add(new URLDepthPair(siteUrl, 0));

        while (!links.isEmpty()) {
            URLDepthPair currentUrl = links.remove();
            urls.add(currentUrl);
            if (currentUrl.getDepth() < depth) {
                Set<String> links1 = getLinks(currentUrl.getUrl());
                for (String url : links1) {
                    URLDepthPair e = new URLDepthPair(url, currentUrl.getDepth() + 1);
                    links.add(e);
                    System.out.println(e.getUrl() + ": " + e.getDepth());
                }
            }
        }
    }

    public static Set<String> getLinks(String url) {
        Set<String> urls = new HashSet<>();
        try {
            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.select("a");
            for (Element element : elements) {
                urls.add(element.absUrl("href"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return urls;
    }
}
