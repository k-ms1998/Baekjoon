package Gold.Basic;

import java.io.*;
import java.util.*;

/**
 * Gold 1(오아시스 재결합)
 *
 * https://www.acmicpc.net/problem/3015
 *
 * Solution: Stack
 */
public class Prob3015 {

    static long[] ppl;

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        ppl = new long[n];
        for(int i = 0; i < n; i++){
            ppl[i] = sc.nextInt();
        }

        long cnt = 0L;
        Stack<Info> stack = new Stack<>();
        for(int i = 0; i < n; i++){
            int tmpCnt = 1;
            while (!stack.isEmpty()) {
                Info top = stack.peek();
                long topH = top.h;
                long topCnt = top.cnt;
                if(topH <= ppl[i]){
                    cnt += topCnt;
                    if (topH == ppl[i]) {
                        tmpCnt += topCnt;
                    }
                    stack.pop();
                }else{
                    break;
                }
            }

            if (!stack.isEmpty()) {
                cnt++;
            }
            stack.push(new Info(ppl[i], tmpCnt));
            // System.out.println("Stack = " + stack + ", cnt = " + cnt);
        }

        System.out.println(cnt);
    }

    public static class Info{
        long h;
        int cnt;

        public Info(long h, int cnt) {
            this.h = h;
            this.cnt = cnt;
        }
    }
}