package Gold.DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * Gold 4(LCS 2)
 *
 * https://www.acmicpc.net/problem/9252
 *
 * Solution: DP
 */
public class Prob9252 {

    static int sizeA;
    static int sizeB;

    static char[] arrA;
    static char[] arrB;

    static int[][] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String strA = br.readLine();
        String strB = br.readLine();

        sizeA = strA.length();
        sizeB = strB.length();

        arrA = new char[sizeA + 1];
        arrB = new char[sizeB + 1];
        arrA[0] = '0';
        arrB[0] = '0';
        for (int i = 0; i < sizeA; i++) {
            arrA[i + 1] = strA.charAt(i);
        }
        for (int i = 0; i < sizeB; i++) {
            arrB[i + 1] = strB.charAt(i);
        }

        dp = new int[sizeB + 1][sizeA + 1];

        /**
         * Problem 9251 이랑 동일한 로직
         */
        for (int y = 1; y < sizeB + 1; y++) {
            for (int x = 1; x < sizeA + 1; x++) {
                if (arrA[x] == arrB[y]) {
                    dp[y][x] = dp[y-1][x - 1] + 1;
                } else {
                    dp[y][x] = Math.max(dp[y][x - 1], dp[y - 1][x]);
                }
            }
        }

//        for (int y = 0; y < sizeB + 1; y++) {
//            for (int x = 0; x < sizeA + 1; x++) {
//                System.out.print(dp[y][x] + " ");
//            }
//            System.out.println();
//        }

        StringBuffer ans = new StringBuffer();
        int ansLength = dp[sizeB][sizeA];
        System.out.println(ansLength);

        /**
         * LSC 수열을 역추적으로 찾기
         */
        if(ansLength > 0){
            int curX = sizeA;
            int curY = sizeB;
            while (curY >= 1 && curX >= 1) {
                /**
                 * 왼쪽으로 dp 값이 변하지 않는 시점 찾기(대각선으로 dp 값이 변경하는 시점을 찾기 위한 과정)
                 */
                if (dp[curY][curX - 1] == dp[curY][curX]) {
                    curX--;
                    continue;
                }
                /**
                 * 위로 dp 값이 변하지 않는 시점 찾기(대각선으로 dp 값이 변경하는 시점을 찾기 위한 과정)
                 */
                if (dp[curY- 1][curX] == dp[curY][curX]) {
                    curY--;
                    continue;
                }

                /**
                 * 대각선으로 값이 바뀔때 수열에 새로운 문자열에 추가되는 것
                 * -> 해당 문자를 ans 에 추가
                 */
                if (dp[curY - 1][curX - 1] != dp[curY][curX]) {
                    ans.append(arrA[curX]);
                    curX--;
                    curY--;
                    continue;
                } else {
                    break;
                }
            }
        }

        System.out.print(ans.reverse());
    }
}
/*
KKKBCBCAAA
KCKBCBCAKK
*/
