
import java.io.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
public class Main {
    public static void main (String[] args){
        final String url ="https://en.wikipedia.org/wiki/Guinea_pig";
        getData(url);
    }
    public static void getData(String url){
        try {
            final Document doc = Jsoup.connect(url).get();
            FileWriter out = new FileWriter("output.txt");
            BufferedWriter writer = new BufferedWriter(out);
            writer.write(doc.title());
            //writeToFile(doc.title());
            for (org.jsoup.nodes.Element row : doc.select("table.infobox.biota tr")){
                if(row.text() == "")
                      continue;
                writer.write("\n" + row.text());
                System.out.println(row.text());
            }
            writer.close();
        } catch (Exception e){

        }
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
