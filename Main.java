import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static AtomicInteger count3 = new AtomicInteger();
    public static AtomicInteger count4 = new AtomicInteger();
    public static AtomicInteger count5 = new AtomicInteger();

    public static void main(String[] args) {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        new Thread(() -> {
            for (String text : texts) {
                onThread(isPalindrome(text), text);
            }
        }).start();

        new Thread(() -> {
            for (String text : texts) {
                onThread(isSame(text), text);
            }
        }).start();

        new Thread(() -> {
            for (String text : texts) {
                onThread(isIncreasing(text), text);
            }
        }).start();

        printResult(3, count3);
        printResult(4, count4);
        printResult(5, count5);
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isPalindrome(String text) {
        return new StringBuilder(text).reverse().toString().equals(text);
    }

    public static boolean isSame(String text) {
        for (int i = 1; i < text.length() - 1; i++) {
            if (text.charAt(0) != text.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isIncreasing(String text) {
        for (int i = 1; i < text.length() - 1; i++) {
            if (text.charAt(i) < text.charAt(i - 1)) {
                return false;
            }
        }
        return true;
    }

    public static void printResult(int textLength, AtomicInteger count) {
        System.out.println("Красивых слов с длиной " + textLength + ": " + count + " шт");
    }

    public static void onThread(boolean criterion, String text) {
        if (criterion) {
            switch (text.length()) {
                case 3:
                    count3.incrementAndGet();
                    break;
                case 4:
                    count4.incrementAndGet();
                    break;
                case 5:
                    count5.incrementAndGet();
                    break;
            }
        }
    }
}