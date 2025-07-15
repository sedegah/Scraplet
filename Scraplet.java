import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.*;

public class Scraplet {

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: java Scraplet <url> <output.json> <output.html>");
            return;
        }

        String url = args[0];
        String jsonOutputFile = args[1];
        String htmlOutputFile = args[2];

        try {
            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0").get();
            saveRawHtml(doc, htmlOutputFile);

            Element root = doc.child(0);
            DomNode rootNode = convertToDomNode(root);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(rootNode);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(jsonOutputFile))) {
                writer.write(json);
            }

            System.out.println("HTML saved to: " + htmlOutputFile);
            System.out.println("JSON saved to: " + jsonOutputFile);

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void saveRawHtml(Document doc, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(doc.outerHtml());
        }
    }

    private static DomNode convertToDomNode(Element element) {
        DomNode node = new DomNode();
        node.tag = element.tagName();

        if (!element.attributes().isEmpty()) {
            Map<String, String> attrs = new HashMap<>();
            element.attributes().forEach(attr -> attrs.put(attr.getKey(), attr.getValue()));
            node.attributes = attrs;
        }

        List<DomNode> children = new ArrayList<>();

        for (Node child : element.childNodes()) {
            if (child instanceof TextNode) {
                String text = ((TextNode) child).text().trim();
                if (!text.isEmpty()) {
                    DomNode textNode = new DomNode();
                    textNode.tag = "#text";
                    textNode.text = text;
                    children.add(textNode);
                }
            } else if (child instanceof Element) {
                children.add(convertToDomNode((Element) child));
            }
        }

        node.children = children;
        return node;
    }

    static class DomNode {
        String tag;
        Map<String, String> attributes;
        String text;
        List<DomNode> children;
    }
}
