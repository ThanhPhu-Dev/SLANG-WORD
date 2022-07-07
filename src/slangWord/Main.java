package slangWord;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static Scanner scanner = new Scanner(System.in);
    public static List<String> history = new ArrayList<>();

    public static void menu (HashMap<String, String> dictionary) throws IOException {

        int choose = 9;
        Random generator = new Random();
        String keyword;
        switch (choose){
            case 1:
                System.out.print("Nhap tu muon tim kiem: ");
                keyword = scanner.next();
                String key = Util.getKey(dictionary.keySet(), keyword);
                if(key != null || !key.isEmpty()){
                    history.add(keyword);
                    System.out.println(String.format("ket qua tim kiem tu khoa '%s' la: %s", keyword, dictionary.get(key)));
                }else{
                    System.out.println(String.format("Khong tim thay slang word trong he thong", keyword));
                }

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
                Util.writeOneLineToFile(slangWord+"`"+definition);
                System.out.println("Luu Thanh Cong");
                break;
            case 5:
                System.out.print("Nhap slang word muốn chỉnh sua: ");
                keyword = Util.getKey(dictionary.keySet(),scanner.next());

                if(keyword == null){
                    System.out.println("slang word khong tim thay.");
                    break;
                }
                System.out.format("Nhap noi dung chinh sua dinh nghia cho %s: ", keyword);
                String value = scanner.next();
                dictionary.put(keyword, value);
                Util.writeMoreLineToFile(dictionary, "slangCopy.txt");
                System.out.println("Chinh sua thanh cong");
                break;
            case 6:
                System.out.print("Nhap slang word muon xoa: ");
                keyword = Util.getKey(dictionary.keySet(),scanner.next());

                if(keyword == null){
                    System.out.println("slang word khong tim thay.");
                    break;
                }
                System.out.print("Nhap Y de xac nhan xoa. Luu y thao tac nay khong hoan tac: ");
                if(!scanner.next().toLowerCase().equals("y")){
                    break;
                }
                dictionary.remove(keyword);
                Util.writeMoreLineToFile(dictionary, "slangCopy.txt");
                System.out.println("Xoa thanh cong.");
                break;
            case 7:
                dictionary = Util.readerFile("slang.txt");
                Util.writeMoreLineToFile(dictionary, "slangCopy.txt");
                System.out.println("Reset file thanh cong.");
                break;
            case 8:
                Object[] keyArray = history.toArray();
                Object randomKey = keyArray[generator.nextInt(keyArray.length)];
                System.out.println(String.format("Random slang word trong ngay hom nay %s : $s", randomKey, dictionary.get(randomKey)));
                break;
            case 9:
                Object[] questions = dictionary.keySet().toArray();
                Object[] answers = dictionary.values().toArray();
                String question = (String) questions[generator.nextInt(questions.length)];
                String answer = dictionary.get(question);
                int index = generator.nextInt(4);

                System.out.format("Chon denfinition cua slang word: %s.\n", question);
                for (int i=0;i<4;i++){
                    if(i == index) {
                        System.out.print(index +". "+ answer +"\t");
                        continue;
                    }
                    String answerWrong = (String) answers[generator.nextInt(answers.length)];
                    if(answerWrong.equals(answer)){
                        answerWrong = (String) answers[generator.nextInt(answers.length)];
                        i-=1;
                        continue;
                    }else{
                        System.out.print(i +". "+ answerWrong +"\t");
                    }
                }
                System.out.print("\nDap an ban chon la: ");
                String yourAnswer = scanner.next();
                if(yourAnswer.equals(String.valueOf(index))){
                    System.out.println("Chuc mung, ban da tra loi chinh xac.");
                }else{
                    System.out.println("ban da tra loi sai, dap an la " + answer);
                }
                break;
        }
    }

    public static void main(String[] args) throws IOException {
        HashMap<String, String> dictionary = Util.init("slang.txt", "slangCopy.txt");
        menu(dictionary);

    }
}
