package slangWord;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static Scanner scanner = new Scanner(System.in);
    public static List<String> history = new ArrayList<>();

    public static int showMenu(){
        System.out.println("-----------------------------------");
        System.out.println("1. Chuc nang tim kiem theo slang word.");
        System.out.println("2. Chuc nang tim kiem theo definition");
        System.out.println("3. Chuc nang hien thi history,");
        System.out.println("4. Chuc nang add 1 slang words moi.");
        System.out.println("5. Chuc nang edit 1 slang word..");
        System.out.println("6. Chuc nang delete 1 slang word.");
        System.out.println("7. Chuc nang reset danh sach slang words.");
        System.out.println("8. Chuc nang ramdom 1 slang word trong ngay.");
        System.out.println("9. Chuc nang do vui, chon dap an dung voi slang word.");
        System.out.println("10. Chuc nang do vui, chon dap an dung voi definition.");
        System.out.println("0. Thoat.");
        System.out.print("chon chuc nang: ");
        return Integer.valueOf(scanner.next()).intValue();
    }

    public static void menu (HashMap<String, String> dictionary) throws IOException {
        Random generator = new Random();
        String keyword = null;
        Object[] questions;
        Object[] answers;
        String question;
        String answer;
        String yourAnswer;
        String answerWrong;
        int index;
        int choose = 0;
        do {
            choose = showMenu();
            switch (choose) {
                case 0:
                    break;
                case 1:
                    System.out.print("Nhap tu muon tim kiem: ");
                    keyword = scanner.next();
                    String key = Util.getKey(dictionary.keySet(), keyword);
                    if (key != null) {
                        history.add(keyword);
                        System.out.println(String.format("ket qua tim kiem tu khoa '%s' la: %s", keyword, dictionary.get(key)));
                    } else {
                        System.out.println(String.format("Khong tim thay slang word trong he thong", keyword));
                    }

                    break;
                case 2:
                    System.out.print("Nhap tu muon tim kiem: ");
                    String k = scanner.next();
                    Set<String> resultList = dictionary.entrySet().stream().parallel()
                            .filter(entry -> entry.getValue().contains(k))
                            .map(Map.Entry::getKey)
                            .collect(Collectors.toSet());
                    if (resultList.size() == 0) {
                        System.out.println("Khong tim thay ket qua voi tu khoa " + keyword);
                    } else {
                        System.out.println("Ket qua time kiem voi tu khoa " + keyword);
                        resultList.forEach(item -> System.out.println(item));
                    }
                    break;
                case 3:
                    System.out.println("Danh sach cac slang word da tim kiem.");
                    if(history.isEmpty()){
                        System.out.println("hom nay ban chua tim kiem slang word nao");
                    }else{
                        history.forEach(item -> System.out.println(item));
                    }
                    break;
                case 4:
                    System.out.println("Chuc nang add 1 slang words moi.");
                    System.out.print("Nhap slang word: ");
                    String slangWord = scanner.next();
                    System.out.format("Nhap dinh nghia cho %s: ", slangWord);
                    String definition = scanner.next();
                    dictionary.put(slangWord, definition);
                    Util.writeOneLineToFile(slangWord + "`" + definition);
                    System.out.println("Luu Thanh Cong");
                    break;
                case 5:
                    System.out.print("Nhap slang word muốn chỉnh sua: ");
                    keyword = Util.getKey(dictionary.keySet(), scanner.next());

                    if (keyword == null) {
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
                    keyword = Util.getKey(dictionary.keySet(), scanner.next());

                    if (keyword == null) {
                        System.out.println("slang word khong tim thay.");
                        break;
                    }
                    System.out.print("Nhap Y de xac nhan xoa. Luu y thao tac nay khong hoan tac: ");
                    if (!scanner.next().toLowerCase().equals("y")) {
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
                    Object[] keyArray = dictionary.keySet().toArray();
                    Object randomKey = keyArray[generator.nextInt(keyArray.length)];
                    System.out.println(String.format("Random slang word trong ngay hom nay %s : %s", randomKey, dictionary.get(randomKey)));
                    break;
                case 9:
                    questions = dictionary.keySet().toArray();
                    answers = dictionary.values().toArray();
                    question = (String) questions[generator.nextInt(questions.length)];
                    answer = dictionary.get(question);
                    index = generator.nextInt(4);

                    System.out.format("Chon denfinition cua slang word: %s.\n", question);
                    for (int i = 0; i < 4; i++) {
                        if (i == index) {
                            System.out.print(index + ". " + answer + "\t");
                            continue;
                        }
                        do {
                            answerWrong = (String) answers[generator.nextInt(answers.length)];
                        } while (answerWrong.equals(answer));
                        System.out.print(i + ". " + answerWrong + "\t");
                    }
                    System.out.print("\nDap an ban chon la: ");
                    yourAnswer = scanner.next();
                    if (yourAnswer.equals(String.valueOf(index))) {
                        System.out.println("Chuc mung, ban da tra loi chinh xac.");
                    } else {
                        System.out.println("ban da tra loi sai, dap an la " + answer);
                    }
                    break;
                case 10:
                    answers = dictionary.keySet().toArray();
                    questions = dictionary.values().toArray();
                    answer = (String) answers[generator.nextInt(answers.length)];
                    question = dictionary.get(answer);
                    index = generator.nextInt(4);

                    System.out.format("Chon slang word cua definition: %s.\n", question);
                    for (int i = 0; i < 4; i++) {
                        if (i == index) {
                            System.out.print(index + ". " + answer + "\t");
                            continue;
                        }
                        do {
                            answerWrong = (String) questions[generator.nextInt(questions.length)];
                        } while (answerWrong.equals(answer));
                        System.out.print(i + ". " + answerWrong + "\t");
                    }
                    System.out.print("\nDap an ban chon la: ");
                    yourAnswer = scanner.next();
                    if (yourAnswer.equals(String.valueOf(index))) {
                        System.out.println("Chuc mung, ban da tra loi chinh xac.");
                    } else {
                        System.out.println("ban da tra loi sai, dap an la " + answer);
                    }
                    break;
                default:
                    System.out.println("Chuc nang khong ton tai.");
                    break;
            }
        }while (choose != 0);
    }

    public static void main(String[] args) throws IOException {
        HashMap<String, String> dictionary = Util.init("slang.txt", "slangCopy.txt");
        menu(dictionary);

    }
}
