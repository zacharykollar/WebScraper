
import java.io.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
public class Main {
    public static void main (String[] args){
        final String url ="https://en.wikipedia.org/wiki/Guinea_pig";
        final String targetUrl ="https://en.wikipedia.org/wiki/Guinea_hog";
        try {
            writeToFile(String.valueOf(findDepth(Jsoup.connect(url).get(), targetUrl, url, 0)));
        } catch (IOException e) {
            e.printStackTrace();
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
    public static int findDepth(Document doc, String target, String url, int deep){
        if (deep > 5)
            return 200;
        if (url == target)
            return deep;
        int smallest = 300;
        for (org.jsoup.nodes.Element link : doc.select("href")){
            System.out.println(link.attr("abs:href"));
            int d = findDepth(doc, target, link.attr("abs:href"), deep + 1);
            if (smallest > d)
                smallest = d;
        }
        return smallest;
    }
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
