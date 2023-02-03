package Review.Gold.DP;

import java.io.*;
import java.util.*;

/**
 * Gold 5(동전 2)
 *
 * https://www.acmicpc.net/problem/2294
 *
 * Solution: DP(배낭 문제)
 * 1. 각 동전에 대해서, 1~k를 만드는데 필요한 최소한의 동전의 갯수 구하기
 *
 * ex:
 * 3 15
 * 1
 * 5
 * 2
 * =>
 *      0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15
 * 0  | I I I I I I I I I I I  I  I  I  I  I   (I = INF)
 * 1  | 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15
 * 5  | 0 1 2 3 4 1 2 3 4 5 2  3  4  5  6  3
 * 12 | 0 1 2 3 4 1 2 3 4 5 2  3  1  2  3  4
 * dp[i][j] = Math.min(dp[i-1][j], 1 + dp[i][j-coins[i]])
 *  => dp[i-1][j] == 직전의 동전까지 사용했을때 j를 만드는데 필요한 최소한의 동전 갯수,
 *     1 + dp[i][j-coins[i]] == 현재 i번째 동전을 하나 사용하고, 하나 사용했을때 나머지 값을 만들기 위해 필요한 동전의 최소 개수
 *     (ex: j = 7이고, coins[i]가 5이면, 5를 하나 사용하고 나머지 2를 만들기 위해 필요한 동전의 최소 갯수)
 * 그러므로, ans = 3 (== Math.min(dp[1][k] ~ dp[3][k]))
 */
public class Prob2294 {

    static int n, k;
    static int[] coins;
    static int[][] dp;
    static final int INF = 1000000000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        coins = new int[n + 1];
        dp = new int[n + 1][k + 1];
        for (int i = 0; i < n + 1; i++) {
            for (int j = 1; j < k + 1; j++) {
                dp[i][j] = INF;
            }
        }
        for (int i = 1; i < n + 1; i++) {
            coins[i] = Integer.parseInt(br.readLine());
        }

        for (int i = 1; i < n + 1; i++) {
            int coin = coins[i];
            for (int j = 0; j < k + 1; j++) {
                if (j < coin) {
                    /**
                     * 현재 동전 값이 만들려는 값(j) 보다 크면 어차피 j 값을 만들지 못하기 때문에, dp[i][j] = dp[i - 1][j]
                     */
                    dp[i][j] = dp[i - 1][j];
                }else{
                    int dif = j - coin;
                    dp[i][j] = Math.min(dp[i - 1][j], 1 + dp[i][dif]);
                }
            }
        }

        int ans = INF;
        for (int i = 1; i < n + 1; i++) {
            ans = Math.min(ans, dp[i][k]);
        }
        System.out.println(ans == INF ? -1 : ans);
    }
}
/*
3 14
2
5
11

3 13
1
5
12

3 13
10
12
14
 */
