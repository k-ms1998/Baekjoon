package Gold.DP;

import java.io.*;
import java.util.*;

/**
 * Gold 2(기업 투자)
 *
 * https://www.acmicpc.net/problem/2662
 *
 * Solution: DP (배낭 문제)
 * 1. 각 투자 금액에 대해서, 기업들을 처음부터 순차적으로 투자한다고 했을때 얻는 총 이익 계산
 * DP:
 * 기업\무게: 1 2 3 4 5 ... n
 *        1
 *        2
 *  dp[2][3] = Math.max(dp[2][3], dp[1][2] + profit[1][2], dp[1][1] + profit[2][2], dp[1][0] + profit[3][2])
 *  (dp[2][3] = 현재 투자할 수 있는 금액이 3이고 기업 2에 투자를 하고 있을때 얻을 수 있는 최대 이익)
 *  (profit[1][2] = 기업 2에 총 1의 금액을 투자 했을때 얻는 이익)
 *
 * 2. 기업 m개에 총 n의 금액을 투자할때 얻을 수 있는 최대 이익 = dp[m][n]
 * 3. 각 기업별로 투자한 금액 추적하기 -> invest[m][n] 부터 시작해서 역추적
 */
public class Prob2622 {

    static int n, m;
    static int[][] profit;
    static int[][] dp;
    static int[] path;
    static int[][] invest;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        profit = new int[n + 1][m + 1];
        dp = new int[m + 1][n + 1];
        path = new int[n + 1];
        invest = new int[m + 1][n + 1];
        for (int i = 1; i < n + 1; i++) {
            st = new StringTokenizer(br.readLine());
            int cur = Integer.parseInt(st.nextToken());
            for (int j = 1; j < m + 1; j++) {
                profit[cur][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 1; i < m + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                for (int r = 0; r <= j; r++) {
                    int tmp = profit[j - r][i] + dp[i - 1][r];
                    /**
                     * dp[i][j] 값을 업데이트 할때 이번 기업에 투자한 금액을 invest[i][j]에 저장
                     */
                    if(tmp > dp[i][j]){
                        dp[i][j] = profit[j - r][i] + dp[i - 1][r];
                        invest[i][j] = j - r;
                    }
                }
            }
        }
        findPath(n, m);

        StringBuilder ans = new StringBuilder();
        ans.append(dp[m][n]).append("\n");
        for (int i = 1; i < m + 1; i++) {
            ans.append(path[i]).append(" ");
        }
        System.out.println(ans);
    }

    public static void findPath(int n, int m) {
        if (m == 0) {
            return;
        }
        path[m] = invest[m][n];
        findPath(n - path[m], m - 1);
    }
}
/*
4 3
1 5 1 1
2 6 5 2
3 7 9 3
4 10 15 4
 */
