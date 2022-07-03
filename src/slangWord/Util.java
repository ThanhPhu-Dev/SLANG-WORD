package slangWord;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Util {

    /**
     * @return danh sach key- value doc tu slang.txt
     * @throws IOException
     */
    public static HashMap<String, String> readerFile() throws IOException {
        File file = new File(System.getProperty("user.dir") + "\\slang.txt");
        FileReader fr = new FileReader(file);
        String line = "";
        HashMap<String, String> dictionary = new HashMap<>();
        try {
            while (true) {
                int i = fr.read();
                if (i == -1) {
                    break;
                }
                char ch = (char) i;
                line += ch;
                if (i == 10) {// "\n" = 10 bao hieu la da doc den cuoi dong
                    String[] itemLine = line.trim().split("`");
                    if(itemLine.length != 2)//dong nay bi thieu key hoac value, vi vay se bo qua dong nay.
                        continue;
                    dictionary.put(itemLine[0], itemLine[1]);
                    line = "";
                }
            }
            return dictionary;
        } catch (Exception e) {
            System.out.println(String.format("Loi khi doc file %s, dong %s", e, line));
            return null;
        } finally {
            fr.close();
        }
    }
}
