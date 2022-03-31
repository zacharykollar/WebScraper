
import java.io.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.util.HashSet;
public class Main {
    public static void main (String[] args){
        final String url ="https://en.wikipedia.org/wiki/Guinea_pig";
        final String targetUrl ="https://en.wikipedia.org/wiki/Jaw";
        
        try {
            writeToFile(String.valueOf(findDepth(Jsoup.connect(url).get(), targetUrl, url, 2)));
        } catch (IOException e) {
            writeToFile("failure");
            //e.printStackTrace();
        }
    }
    public static void getData(String url){
        try {
            final Document doc = Jsoup.connect(url).get();
            FileWriter out = new FileWriter("output.txt");
            BufferedWriter writer = new BufferedWriter(out);
            writer.write(doc.title());
            for (org.jsoup.nodes.Element row : doc.select("[href]")){
                if(row.text() == "")
                      continue;
                writer.write("\n" + row.attr("abs:href"));
                System.out.println(row.text());
            }
            writer.close();
        } catch (Exception e){

        }
    }
    public static int findDepth(Document doc, String target, String url, int maxDepth) {
        return findDepthHelper(doc, target, url, maxDepth, 0, 300);
    }
    public static int findDepthHelper(Document doc, String target, String url, int maxDepth, int deep, int smallest) {
        if (url.equals(target))
            return deep;
        if (deep > maxDepth || deep >= smallest)
            return 200;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            System.out.println("AAAHHHHHH!!!!");
            return 2000;
        } catch (Exception e) {
            System.out.println("all else failed - likely malformed");
            return 2000;
        }
        for (org.jsoup.nodes.Element link : doc.select("[href]")){
            int d = 0;
            if(link.attr("abs:href").contains("#cite_note"))
                continue;
            d = findDepthHelper(doc, target, link.attr("abs:href"), maxDepth, deep + 1, smallest);
            if (smallest > d)
                smallest = d;
        }
        return smallest;
    }

    //uses breadth first search with hashmaps
    public static int findDepthBreadth(Document doc, String target, String url, int maxDepth){
        HashSet<String> first = new HashSet<>();
        HashSet<String> second = new HashSet<>();
        System.out.println(findDepthBreadthHelper(doc, target, url));
        int deep = 0;
        if(url.equals(target))
            return deep;
        first.addAll(findDepthBreadthHelper(doc, target, url));
        deep++;
        //System.out.println("init " + first + " : " + second);
        while(deep < maxDepth){
            //System.out.println(deep + " : " + first + " : " + second);
            if(deep % 2 == 0){
                first.clear();
                for (String s : second){
                    if (s.equals(target)){
                        return deep;
                    }
                    first.addAll(findDepthBreadthHelper(doc, target, s));
                }
            } else {
                second.clear();
                for (String s : first){
                    if (s.equals(target)){
                        return deep;
                    }
                    first.addAll(findDepthBreadthHelper(doc, target, s));
                }            
            }
        }
        return -1;
    }
    private static HashSet<String> findDepthBreadthHelper(Document doc, String target, String url) {
    HashSet<String> fill = new HashSet<>();
    for (org.jsoup.nodes.Element link : doc.select("[href]")){
        fill.add(link.attr("[href]"));
    }
    return fill;
    }
    //tools
    public static void writeToFile(String data){
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
