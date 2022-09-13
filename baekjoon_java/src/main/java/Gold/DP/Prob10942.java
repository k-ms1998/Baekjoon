package Gold.DP;

import java.io.*;
import java.util.*;

/**
 * Gold 4 (팰린드롬?)
 *
 * https://www.acmicpc.net/problem/10942
 *
 * Solution: DP
 */
public class Prob10942 {

    static int n;
    static int[] num;
    static int m;

    static boolean[][] dp;
    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        num = new int[n + 1];
        dp = new boolean[n + 1][n + 1];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i < n + 1; i++) {
            num[i] = Integer.parseInt(st.nextToken());
        }

        initDp();

        m = Integer.parseInt(br.readLine());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());

            if (dp[s][e]) {
                ans.append("1").append("\n");
            } else {
                ans.append("0").append("\n");
            }
        }

        System.out.println(ans);
    }

    /**
     * DP:
     * dp[y][x] -> num[x]에서, x~y 구간이 팰린드롬인지 아닌지 확인
     */
    private static void initDp() {
        /**
         * 문자열의 길이가 1이면 무조건 팰린드롬
         * -> dp[1][1] => num[1]~ num[1] 구간이 팰린드롬인지 확인
         */
        for (int x = 1; x < n + 1; x++) {
            dp[x][x] = true;
        }

        /**
         * 문자열의 길이가 2일때, 두개의 문자가 같으면 팰린드롬
         * -> dp[1][2] => num[1]~num[2] 구간이 팰린드롬인지 확인
         * -> dp[2][3] => num[2]~num[3] 구간이 팰린드롬인지 확인
         */
        for (int x = 1; x < n; x++) {
            if (num[x] == num[x + 1]) {
                dp[x][x + 1] = true;
            }
        }

        /**
         * dp[1][5] => num[2]~num[5] 구간이 팰린드롬인지 확인
         *  => num[1] == num[5] 이고, num[2]~num[4] 구간이 팰린드롬이면, dp[1][5]는 true
         *      -> num[2]~num[4]이 팰린드롬이면 dp[2][4] == true
         */
        for (int i = 2; i < n + 1; i++) {
            for (int x = 1; x < n; x++) {
                int sx = x;
                int dx = x + i;
                
                if (dx < n + 1) {
                    if (num[sx] == num[dx]) {
                        if (dp[sx + 1][dx - 1]) {
                            /**
                             * dp[sx][dx] => num[sx]~num[dx] 구간이 팰린드롬인지 확인
                             *  => num[sx] == num[dx] 이고, dp[sx+1][dx-1] 이 true 이면 dp[sx][dx] 도 팰린드롬
                             */
                            dp[sx][dx] = true;
                        }
                    }
                }
            }
        }
    }
}