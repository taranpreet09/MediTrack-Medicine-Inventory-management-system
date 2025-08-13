package util;

import java.util.Scanner;

public class InputUtil {
    public static Scanner sc = new Scanner(System.in);

    public static int readInt(String msg) {
        System.out.print(msg);
        int val = sc.nextInt();
        sc.nextLine();
        return val;
    }

    public static String readLine(String msg) {
        System.out.print(msg);
        return sc.nextLine();
    }
}
