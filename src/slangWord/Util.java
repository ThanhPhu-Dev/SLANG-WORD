package slangWord;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class Util {

    /**
     * @return danh sach key- value doc tu slang.txt
     * @throws IOException
     */
    public static HashMap<String, String> readerFile(String fileName) throws IOException {

        File file = new File(System.getProperty("user.dir") +"\\"+ fileName);
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

    public static void writeOneLineToFile(String content) {
        File file = null;
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            file = new File(System.getProperty("user.dir") + "\\slangCopy.txt");
            if (!file.exists()) {
                file.createNewFile();
            }

            fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);
            writeContentToFile(bw,content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getKey(Collection<String> set, String keyword){
        return set.stream().parallel().filter(m -> (m.toLowerCase()).equals(keyword.toLowerCase())).findFirst().get();
    }

    public static void writeMoreLineToFile(Map<String,String> map, String fileName){
        File file = null;
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {

            file = new File(System.getProperty("user.dir") + "\\"+ fileName);
            if (!file.exists()) {
                file.createNewFile();
            }

            fw = new FileWriter(file, false);
            bw = new BufferedWriter(fw);
            for (Map.Entry<String, String> entry : map.entrySet()) {
                writeContentToFile(bw,entry.getKey() + "`" + entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    private static void writeContentToFile(BufferedWriter bw, String content) throws IOException {
        bw.write(content);
        bw.write("\n");
    }

    public static void createFileCopy(String fileNameSource, String fileNameTarget, boolean isReplace) {
        File file = new File(System.getProperty("user.dir") + "\\" + fileNameTarget);
        try {
            if(!isReplace){
                if(!file.exists()){
                    file.createNewFile();
                    Path source = Paths.get(System.getProperty("user.dir") + "\\" + fileNameSource);
                    Path target = Paths.get(System.getProperty("user.dir") + "\\" + fileNameTarget);

                }
            }
            if (!file.exists()) {
                file.createNewFile();
                Path source = Paths.get(file.getPath());
                Path target = Paths.get(System.getProperty("user.dir") + "\\" + "slangCopy.txt");
                Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
            }else {
                if (isReplace) {
                    Path source = Paths.get(file.getPath());
                    Path target = Paths.get(System.getProperty("user.dir") + "\\" + "slangCopy.txt");
                    Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, String> init(String fileSourceName, String fileTargetName){
        File file = new File(System.getProperty("user.dir") + "\\" + fileTargetName);
        HashMap<String, String> dictionary = null;
        try{

            if(!file.exists()){
                file.createNewFile();
                dictionary = readerFile(fileSourceName);
                writeMoreLineToFile(dictionary, file.getName() + ".txt");
            }else{
                dictionary = readerFile(file.getName() + ".txt");
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return dictionary;
    }

}
