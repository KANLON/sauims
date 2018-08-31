package test.controller;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.System.out;
public class HelloWord {
    public static void main(String[] args) {
        ArrayList<Integer> arr = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine());
        String string = sc.nextLine();

        int shuZu[] = new int[n];
        StringTokenizer st = new StringTokenizer(string, " ");
        for (int i = 0; i < n; i++) {
            int x = Integer.parseInt((String) st.nextElement());
            shuZu[i] = x;
        }
        for (int s : shuZu) {
            arr.add(s);
        }
        Collections.sort(arr);
        for (Integer x : arr) {
            System.out.print(x + " ");
        }
    }




    public static void hanio(int n , char a,char b,char c){

        if(n>0) {
            hanio(n - 1, a, c, b);
            out.println("盘子：" + n + "，从" + a + "———》" + b);
            hanio(n - 1, c, b, a);
        }
    }

}