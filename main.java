package shomin.algoritm.practice.p10;

import java.util.InputMismatchException;
import java.util.Scanner;

public class main {

    static Scanner sc = new Scanner(System.in);
    static String[][] database = new String[15][2];
    static int userCount = 0;

    static void main(String[] args) {
        while (true) {
            System.out.println("===Головне меню===");
            System.out.println("1. Додати користувача.");
            System.out.println("2. Видалити користувача.");
            System.out.println("3. Переглянути існуючих користувачів.");
            System.out.println("4. Вихід");
            int choice;

            try {
                choice = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e){
                System.out.println("Введіть число");
                sc.nextLine();
                continue;
            }


            if (choice == 1) {
                try {
                    database[userCount] = inputUserData();
                    userCount++;
                    System.out.println("Користувача було аутентифіковано");
                    System.out.println();
                }catch(ArrayIndexOutOfBoundsException e) {
                    System.out.println("Помилка: Недостатньо місць у масиві! Максимум 15 користувачів.");
                }
            } else if (choice == 2) {
                deleteUserData();
            } else if (choice == 3) {
                printUsers();
            } else if (choice == 4) {
                break;
            }
        }
    }

    public static String[] inputUserData() {
        String user_name;
        String password;

        while (true) {
            try {
                System.out.println("Введите имя пользователя: ");
                user_name = sc.nextLine();
                System.out.println("Введите пароль: ");
                password = sc.nextLine();

                isUserDataValid(user_name, password);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка валидации: " + e.getMessage());
                System.out.println("Введите корректные данные!!!");
            }
        }

        return new String[] {user_name, password};
    }

    public static void isUserDataValid(String user_name, String password) {
        if (user_name.length() < 5) {
            throw new IllegalArgumentException("Iм'я має складатись не менше ніж з 5 символів!");
        } else if (user_name.contains(" ")) {
            throw new IllegalArgumentException("В імені не має містити пробілів!");
        }

        String[] badPassword = {"admin", "pass", "password", "qwerty", "ytrewq"};
        for (String forbiden : badPassword) {
            if (password.toLowerCase().contains(forbiden.toLowerCase())) {
                throw new IllegalArgumentException("Пароль не може містити такі слова, як - admin, pass, password, qwerty, ytrewq!");
            }
        }

        String allowedChars = "^[a-zA-Z0-9\\p{Punct}]+$";

        if (password.length() < 10) {
            throw new IllegalArgumentException("Довжина паролю має бути не менше 10 символів!");
        } else if (password.contains(" ")) {
            throw new IllegalArgumentException("Пароль не має містити пробілів!");
        } else if (!password.matches(allowedChars)) {
            throw new IllegalArgumentException("Пароль має складатись виключно з латинських символів, спеціальних символів, символів чисел!");
        }

        int digitCount = 0;
        int specialCharCount = 0;
        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);

            if (Character.isDigit(ch)) {
                digitCount++;
            } else if (allowedChars.indexOf(ch) != -1) {
                specialCharCount++;
            }
        }

        if (digitCount < 3) {
            throw new IllegalArgumentException("Пароль має мати хоча б 3 цифри!");
        } else if (specialCharCount < 1) {
            throw new IllegalArgumentException("Пароль має містити хоча б 1 спеціальний символ!");
        }
    }

    public static void deleteUserData() {
        if (userCount == 0) {
            System.out.println("Помилка: Немає кого видаляти!");
            return;
        }

        printUsers();

        int index;
        while (true) {
            try {
                System.out.println("Введіть номер користувача для видалення");
                index = sc.nextInt();
                sc.nextLine();
                break;
            }catch (InputMismatchException e) {
                System.out.println("Введіть число");
                sc.nextLine();
            }

        }

        int realIndex = index - 1;

        try {
            if (realIndex < 0 || realIndex >= userCount) {
                throw new ArrayIndexOutOfBoundsException();
            }

            for (int i = realIndex; i < userCount - 1; i++) {
                database[i] = database[i + 1];
            }

            database[userCount - 1] = null;
            userCount--;
            System.out.println("Користувача успішно видалено");
        }catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("Помилка: Користувача з номером не існує!");
        }
    }

    public static void printUsers() {
        System.out.println("---Сущесвтующие пользователи---");
        if (userCount > 0) {
            for (int i = 0; i < userCount; i++) {
                System.out.println((i + 1) + ". " + database[i][0]);
            }
        } else {
            System.out.println("Пользователей нет!");
        }
        System.out.println();
    }
}
