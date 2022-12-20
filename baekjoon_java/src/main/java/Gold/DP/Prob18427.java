package Gold.DP;

import java.io.*;
import java.util.*;

/**
 * Gold 4(함께 블록 쌓기)
 *
 * https://www.acmicpc.net/problem/18427
 *
 * Solution: DP
 * 1. 평범한 배낭 문제의 변형 문제
 * 2. 0 ~ h에서 각 높이마다 블록들로 만들 수 있는 경우의 수 구하기
 * 3. 각 h를, 각 학생들마다 각각 블록을 써서 만들수 잇는 경우의 수 구하면 됨
 */
public class Prob18427 {

    static int n, m, h;
    static int[][] nums;
    static int[] sizes;
    static int[][] dp;

    static final int MOD = 10007;
    static int ans = 0;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        h = Integer.parseInt(st.nextToken());
        nums = new int[n][m + 1];
        sizes = new int[n];
        dp = new int[n][h + 1];

        for (int i = 0; i < n; i++) {
            String[] curRow = br.readLine().split(" ");
            sizes[i] = curRow.length;
            for (int j = 0; j < curRow.length; j++) {
                nums[i][j] = Integer.parseInt(curRow[j]);
                dp[i][nums[i][j]] = 1;
            }
        }

        findAnswer();
//        for (int i = 0; i < n; i++) {
//            for(int j = 0; j < h + 1; j++){
//                System.out.print(dp[i][j] + " ");
//            }
//            System.out.println();
//        }
        System.out.println(dp[n-1][h] % MOD);
    }

    public static void findAnswer(){
        for (int i = 0; i < h + 1; i++) {
            for (int j = 1; j < n; j++) {
                for (int r = 0; r < sizes[j]; r++) {
                    if (nums[j][r] <= i) {
                        /**
                         * j 번째 학생의 r번째 블록으로 높이 i를 만들수 있는 방법:
                         * j - 1번째 학생의 높이 (i - (j번째 학생의 r번째 블록의 높이))를 만들수 있는 경우의 수 더하기
                         */
                        dp[j][i] += (dp[j - 1][i - nums[j][r]] % MOD);
                    }
                }
                dp[j][i] += (dp[j - 1][i] % MOD);
            }
        }
    }
}
