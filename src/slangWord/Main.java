package slangWord;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static Scanner scanner = new Scanner(System.in);

    public static List<String> history = new ArrayList<>();

    public static void menu () throws IOException {
        HashMap<String, String> dictionary = Util.readerFile();
        Set<String> set = dictionary.keySet();
        int choose = 1;
        String result = "";
        String keyword;
        switch (choose){
            case 1:
                System.out.print("Nhap tu muon tim kiem: ");
                keyword = scanner.next();
                String key = set.stream().parallel().filter(m -> (m.toLowerCase()).equals(keyword.toLowerCase())).findFirst().get();
                if(key != null || !key.isEmpty()){
                    result = dictionary.get(key);
                }
                history.add(keyword);
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
            case 3:
                System.out.println("Danh sach cac slang word da tim kiem.");
                history.forEach(item -> System.out.println(item));
                break;
            case 4:
                System.out.println("Chuc nang add 1 slang words moi.");
                System.out.print("Nhap slang word: ");
                String slangWord = scanner.next();
                System.out.format("Nhap dinh nghia cho %s: ", slangWord);
                String definition = scanner.next();
                dictionary.put(slangWord, definition);
                Util.writeToFile(slangWord+"`"+definition, true);
                System.out.println("Luu Thanh Cong");
                break;
            case 8:
                Random generator = new Random();
                Object[] keyArray = history.toArray();
                Object randomKey = keyArray[generator.nextInt(keyArray.length)];
                System.out.println(String.format("Random slang word trong ngay hom nay %s : $s", randomKey, dictionary.get(randomKey)));
                break;
        }
    }

    public static void main(String[] args) throws IOException {
        menu();
    }
}
