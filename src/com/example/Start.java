package com.example;

public class Start {

    private static final String SALT = "SALT";

    public static int receivingExistCode(int x) {
        x += 256;
        while(!(((x <= 57) && (x >= 48)) || ((x <= 90) && (x >= 65)) || ((x <= 122) && (x >= 97)))) {
            if(x < 48) {
                x += 24;
            }
            else {
                x -= 47;
            }
        }
        return x;
    }

    public static int getControlSum(String str) {
        int sum = 0;
        for(int i = 0; i < str.length(); i++) {
            sum += (int)str.charAt(i);
        }
        return sum;
    }

    public static String compressString(String currentString) {
        String hash = "";
        while(currentString.length() != 32) {
            for(int i = 0, center = (currentString.length() / 2); i < center; i++)
                hash += (char)receivingExistCode(currentString.charAt(center - i) + currentString.charAt(center + i));
            currentString = hash;
            hash = "";
        }
        return currentString;
    }

    public static String hash(String currentString) {
        if(currentString.length() < 6) {
            new RuntimeException("Длина строки меньше 6 символов");
        }

        if (currentString.length() > 64) {
            new RuntimeException("Длина строки больше 64 символов");
        }

        if(currentString.length()%2 == 1) {
            currentString += "s";
        }

        //Добавляем символы в строку (до 64 символов)
        for(int i = 0; i < 64; i++) {
            if(currentString.length() == 64) {
                break;
            }
            currentString += (char)receivingExistCode(currentString.charAt(i) + currentString.charAt(i + 1));
        }

        //Сжимаем строку до 32 символов
        currentString = compressString(currentString);

        //Добавляем символы в строку (до 64 символов)
        currentString += SALT;
        for(int i = 0; i < 64; i++) {
            if(currentString.length() == 64) {
                break;
            }
            currentString += (char)receivingExistCode((currentString.charAt(i) * currentString.charAt(i + 1)) % 7);
        }

        //Сжимаем строку до 32 символов
        currentString = compressString(currentString);

        return currentString;
    }

    public static void main(String[] args) {
        String str = "6f=hud12h3ifdpq6";
        System.out.println("Хэш: " + hash(str));
    }
}
