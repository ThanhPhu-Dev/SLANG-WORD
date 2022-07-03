package slangWord;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static Scanner scanner = new Scanner(System.in);

    public static void menu () throws IOException {
        HashMap<String, String> dictionary = Util.readerFile();
        Set<String> set = dictionary.keySet();
        int choose = 1;
        switch (choose){
            case 1:
                String result = "";
                System.out.println("Nhap tu muon tim kiem: ");
                String keyword = scanner.next();
                String key = set.stream().filter(m -> m.equals(keyword)).findFirst().get();
                if(key != null || !key.isEmpty()){
                    result = dictionary.get(key);
                }
                System.out.println(String.format("ket qua tim kiem tu khoa %s la: %s", keyword, result));

        }
    }

    public static void main(String[] args) throws IOException {
        menu();
    }
}
