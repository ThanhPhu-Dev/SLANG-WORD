package slangWord;

import java.io.*;
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

    public static void writeToFile(String content, boolean isAppend){
        try{
            File file = new File(System.getProperty("user.dir") + "\\slang.txt");
            if(!file.exists()){
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file, isAppend);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.write("\n");
            bw.close();
            fw.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
