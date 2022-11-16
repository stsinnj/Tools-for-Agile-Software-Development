package Controller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {


    //read  a file
    public static List<String> read(String fileName) {
        isExist(fileName);
        List<String> list = new ArrayList<String>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            String line = in.readLine();
            while (line != null) {
                list.add(line);
                line = in.readLine();
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return list;
    }


    /**
     * write to file
     *
     * @param fileName
     * @param content
     */
    public static void write(String fileName, String content) {
        isExist(fileName);
        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write(content);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void isExist(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                //create file
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
