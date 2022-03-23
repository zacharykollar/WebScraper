
import java.io.*;
public class Main {
    public static void main (String[] args){
        writeToFile("never");
    }
    public static void getData(String url){

    }
    public static void writeToFile(String data){
        try {
            FileWriter out = new FileWriter("output.txt");
            BufferedWriter writer = new BufferedWriter(out);
            writer.write(data);
            writer.close();
        } catch (Exception e) {
            System.out.print("file failed");
        }
    }
}
