package slangWord;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static Scanner scanner = new Scanner(System.in);

    public static void menu () throws IOException {
        HashMap<String, String> dictionary = Util.readerFile();
        Set<String> set = dictionary.keySet();
        int choose = 2;
        String result = "";
        String keyword;
        switch (choose){
            case 1:
                System.out.print("Nhap tu muon tim kiem: ");
                keyword = scanner.next();
                String key = set.stream().filter(m -> m.toLowerCase().equals(keyword.toLowerCase())).findFirst().get();
                if(key != null || !key.isEmpty()){
                    result = dictionary.get(key);
                }
                System.out.println(String.format("ket qua tim kiem tu khoa '%s' la: %s", keyword, result));
                break;
            case 2:
                System.out.print("Nhap tu muon tim kiem: ");
                keyword = scanner.next();
                Set<String> resultList = dictionary.entrySet().stream().parallel()
                        .filter(entry -> entry.getValue().contains(keyword))
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toSet());
                if(resultList.size() == 0){
                    System.out.println("Khong tim thay ket qua voi tu khoa "+ keyword);
                }else{
                    System.out.println("Ket qua time kiem voi tu khoa " + keyword);
                    resultList.forEach(item -> System.out.print(item));
                }
                break;
        }
    }

    public static void main(String[] args) throws IOException {
        menu();
    }
}