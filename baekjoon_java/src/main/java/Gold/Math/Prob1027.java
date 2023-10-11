package Gold.Math;

import java.io.*;
import java.util.*;

/**
 * Gold 4(고층 건물)
 *
 * https://www.acmicpc.net/problem/1027
 *
 * Solution: CCW
 */
public class Prob1027 {

    static int n;
    static long[] input;
    static int answer = 0;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        input = new long[n];

        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < n; i++){
            input[i] = Long.parseLong(st.nextToken());
        }


        for(int x1 = 0; x1 < n; x1++){
            long y1 = input[x1];

            int leftCnt = 0;
            int rightCnt = 0;

            for(int x2 = 0; x2 < x1; x2++){
                long y2 = input[x2];

                boolean flag = true;
                for(int x3 = x2 + 1; x3 < x1; x3++){
                    long y3 = input[x3];
                    long res = ccw(x1, y1, x2, y2, x3, y3);

                    if(res <= 0){
                        flag = false;
                        break;
                    }
                }

                if(flag){
                    leftCnt++;
                }
            }

            for(int x2 = n - 1; x2 > x1; x2--){
                long y2 = input[x2];

                boolean flag = true;
                for(int x3 = x2 - 1; x3 > x1; x3--){
                    long y3 = input[x3];
                    long res = ccw(x1, y1, x2, y2, x3, y3);

                    if(res >= 0){
                        flag = false;
                        break;
                    }
                }

                if(flag){
                    rightCnt++;
                }
            }

            answer = Math.max(answer, leftCnt + rightCnt);
        }

        System.out.println(answer);
    }

    public static long ccw(int x1, long y1, int x2, long y2, int x3, long y3){
        long a = x1*y2 + x2*y3 + x3*y1;
        long b = x2*y1 + x3*y2 + x1*y3;

        return a - b;
    }
}