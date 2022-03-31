
import java.io.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        final String url = "https://jsoup.org/cookbook/introduction/";
        final String target = "https://jsoup.org/apidocs/?org/jsoup/nodes/package-tree.html";
        writeToFile(String.valueOf(findDepthBreadth(target, url, 2)));
    }

    public static void getData(String url) {
        try {
            final Document doc = Jsoup.connect(url).get();
            FileWriter out = new FileWriter("output.txt");
            BufferedWriter writer = new BufferedWriter(out);
            writer.write(doc.title());
            for (org.jsoup.nodes.Element row : doc.select("[href]")) {
                if (row.text() == "")
                    continue;
                writer.write("\n" + row.attr("abs:href"));
                System.out.println(row.text());
            }
            writer.close();
        } catch (Exception e) {

        }
    }

    public static int findDepth(String target, String url, int maxDepth) {
        return findDepthHelper(target, url, maxDepth, 0, 300);
    }

    public static int findDepthHelper(String target, String url, int maxDepth, int deep, int smallest) {
        System.out.println(deep + " : " + url);
        if (url.equals(target))
            return deep;
        if (deep >= smallest)
            return 200;
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            System.out.println("AAAHHHHHH!!!!");
            return 2000;
        } catch (Exception e) {
            System.out.println("all else failed - likely malformed");
            return 2000;
        }
        if (deep != maxDepth && deep < smallest) {
            for (org.jsoup.nodes.Element link : doc.select("[href]")) {
                int d = 0;
                String ref = link.attr("abs:href");
                if (ref.contains("#cite_note") || ref.equals(url) || link.text().equals(""))
                    continue;
                d = findDepthHelper(target, link.attr("abs:href"), maxDepth, deep + 1, smallest);
                if (smallest > d)
                    smallest = d;
            }
        } else {
            return 200;
        }
        return smallest;
    }

    // uses breadth first search with hashmaps
    public static int findDepthBreadth(String target, String url, int maxDepth) {
        HashSet<String> first = new HashSet<>();
        HashSet<String> second = new HashSet<>();
        System.out.println(getLinksHash(url));
        int deep = 0;
        if (url.equals(target))
            return deep;
        second.addAll(getLinksHash(url));
        System.out.println("init " + first + " : " + second);
        while (deep < maxDepth) {
            System.out.println(deep + " : " + first + " : " + second);
            if (deep % 2 == 0) {
                first.clear();
                for (String s : second) {
                    if (s.equals(target)) {
                        return deep;
                    }
                    first.addAll(getLinksHash(s));
                }
            } else {
                second.clear();
                for (String s : first) {
                    if (s.equals(target)) {
                        return deep;
                    }
                    second.addAll(getLinksHash(s));
                }
            }
            deep++;
        }
        return -1;
    }

    private static HashSet<String> getLinksHash(String url) {
        HashSet<String> fill = new HashSet<>();
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
        } catch (Exception e) {
            return fill;
        }
        for (org.jsoup.nodes.Element link : doc.select("[href]")) {
            if (link.text().equals(""))
                continue;
            fill.add(link.attr("abs:href"));
        }
        return fill;
    }

    // tools
    public static void writeToFile(String data) {
        try {
            FileWriter out = new FileWriter("output.txt");
            BufferedWriter writer = new BufferedWriter(out);
            writer.write(data);
            writer.newLine();
            writer.close();
        } catch (Exception e) {
            System.out.print("write to file failed");
        }
    }
}
