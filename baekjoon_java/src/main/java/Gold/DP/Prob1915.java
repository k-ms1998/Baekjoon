package Gold.DP;

import java.io.*;
import java.util.*;

/**
 * Gold 4(가장 큰 정사각형)
 *
 * https://www.acmicpc.net/problem/1915
 *
 * Solution: DP
 */
public class Prob1915 {

    static int n, m;
    static int[][] dp;

    static int ans = 0;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        dp = new int[n + 1][m + 1];
        for (int y = 1; y < n + 1; y++) {
            String curRow = br.readLine();
            for (int x = 1; x < m + 1; x++) {
                int num = Integer.parseInt(String.valueOf(curRow.charAt(x - 1)));
                /**
                 * 현재 숫자가 1이면 정사각형의 크기가 늘어날 수 있는 조건이 됨
                 * 정사각형이기 위해서는 (x-1, y), (x, y-1), (x-1, y-1) 모두 1이어야 함
                 * 이때, 1이 아닐 경우에는 dp 값이 어차피 0이므로, 따로 1인지 아닌지 추가로 확인할 필요는 X
                 * 
                 * dp[y-1][x-1], dp[y-1][x], dp[y][x-1] 모두 1이상인 경우 해당 좌표의 값은 모두 1인 상태
                 * 그러므로, 현쟈 (x,y)가 1이면 정사각형이 만들어질 수 있는 여지가 있음
                 */
                if (num == 1) {
                    dp[y][x] = Math.min(dp[y - 1][x - 1], Math.min(dp[y - 1][x], dp[y][x - 1])) + 1;
                    ans = Math.max(ans, dp[y][x]);
                }
            }
        }

        System.out.println(ans * ans);
    }
}


