package Gold.DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Gold 5
 *
 * https://www.acmicpc.net/problem/5582
 *
 * Solution: DP
 * 1. 2차원 배열의 DP 생성
 * 2. 2중 for 문으로 문자열 1과 문자열 2의 각 문자를 비교
 * 3. 같은 문자가 나왔을때, dp 에서 왼쪽 위 대각선 값 + 1로 현재 dp 값을 업데이트
 */
public class Prob5582 {
    static String[] strA;
    static String[] strB;
    static int ans = 0;
    static int[][] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        strA = br.readLine().split("");
        strB = br.readLine().split("");

        int strASize = strA.length;
        int strBSize = strB.length;

        dp = new int[strASize + 1][strBSize + 1];
        for (int x = 1; x < strASize + 1; x++) {
            String curXChar = strA[x - 1];
            for (int y = 1; y < strBSize + 1; y++) {
                if (curXChar.equals(strB[y-1])) {
                    /**
                     *
                     * 한칸 왼쪽 위의 대각선을 확인하는 이유:
                     * if, 현재 문자열 == ECA && ABRACA, 왼쪽 위 대각선 문자열 == EC && ABRAC
                     *  => 그러므로, 각각 문자열에 똑같은 문자 A 가 추가 됐으므로, 왼쪽 위 대각선에서 공통 되는 길이에 1을 추가해줌
                     */
                    dp[x][y] = dp[x - 1][y - 1] + 1;
                    ans = Math.max(ans, dp[x][y]);
                }else{
                    dp[x][y] = 0;
                }
            }
        }

        /**
         *   A B R A C A D A B R A
         * E 0 0 0 0 0 0 0 0 0 0 0  -> E 랑  A ~ A B R A C A D A B R A 까지의 문자열(+ subString) 중에서 공통되는 문자열의 길이
         * C 0 0 0 0 1 0 0 0 0 0 0  -> E~EC 랑  A ~ A B R A C A D A B R A 까지의 문자열(+ subString) 중에서 공통되는 문자열의 길이
         * A 1 0 0 1 0 2 0 1 0 0 1  -> E~ECA 랑  A ~ A B R A C A D A B R A 까지의 문자열(+ subString) 중에서 공통되는 문자열의 길이
         * D 0 0 0 0 0 0 3 0 0 0 0      => 한칸 왼쪽 위의 대각선을 확인하는 이유:
         * A 1 0 0 1 0 1 0 4 0 0 1          -> 현재 문자열 == ECA && ABRACA, 왼쪽 위 대각선 문자열 == EC && ABRAC
         * D 0 0 0 0 0 0 2 0 0 0 0              => 그러므로, 각각 문자열에 똑같은 문자 A 가 추가 됐으므로, 왼쪽 위 대각선에서 공통 되는 길이에 1을 추가해줌
         * A 1 0 0 1 0 1 0 3 0 0 1
         * B 0 2 0 0 0 0 0 0 4 0 0
         * R 0 0 3 0 0 0 0 0 0 5 0
         * B 0 1 0 0 0 0 0 0 1 0 0
         * C 0 0 0 0 1 0 0 0 0 0 0
         * R 0 0 1 0 0 0 0 0 0 1 0
         * D 0 0 0 0 0 0 1 0 0 0 0
         * A 1 0 0 1 0 1 0 2 0 0 1
         * R 0 0 1 0 0 0 0 0 0 1 0
         * A 1 0 0 2 0 1 0 1 0 0 2
         */
//        for (int y = 1; y < strBSize + 1; y++) {
//            for (int x = 1; x < strASize + 1; x++) {
//                System.out.print(dp[x][y] + " ");
//            }
//            System.out.println();
//        }

        System.out.println(ans);
    }
}
