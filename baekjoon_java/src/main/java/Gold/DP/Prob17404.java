package Gold.DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Gold 4 (RGB 거리 2)
 *
 * https://www.acmicpc.net/problem/17404
 *  규칙:
 * 1. 1번 집의 색은 2번, N번 집의 색과 같지 않아야 한다.
 * 2. N번 집의 색은 N-1번, 1번 집의 색과 같지 않아야 한다.
 * 3. i(2 ≤ i ≤ N-1)번 집의 색은 i-1, i+1번 집의 색과 같지 않아야 한다.
 *
 * Solution: DP
 */
public class Prob17404 {

    static int n;
    static int[][] houses;
    
    static int[][] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        houses = new int[n + 1][3];
        dp = new int[n + 1][3];
        for (int i = 1; i < n + 1; i++) {
            /**
             * 0 : R
             * 1 : G
             * 2 : B
             */
            st = new StringTokenizer(br.readLine());
            houses[i][0] = Integer.parseInt(st.nextToken());
            houses[i][1] = Integer.parseInt(st.nextToken());
            houses[i][2] = Integer.parseInt(st.nextToken());
        }

        int ans = Integer.MAX_VALUE;
        /**
         * 첫번째 집의 색깔에 따라서 마지막 집의 색깔이 영향을 받기 때문에, 첫번째 색깔이 R, G, B인 경우 따로 생각
         */
        for (int i = 0; i < 3; i++) {
            initDp();
            dp[1][i] = houses[1][i];

            for (int j = 2; j < n + 1; j++) {
                for (int r = 0; r < 3; r++) {
                    /**
                     * r == 0 이면: j번째 집는 Red 로 색칠 -> 이전에 있는 집에서 1(Green) 또는 2(Blue)인 상태일때를 확인
                     * r == 1 이면: j번째 집는 Green 로 색칠 -> 이전에 있는 집에서 0(Red) 또는 2(Blue)인 상태일때를 확인
                     * r == 2 이면: j번째 집는 Blue 로 색칠 -> 이전에 있는 집에서 0(Red) 또는 1(Green)인 상태일때를 확인
                     */
                    dp[j][r] = Math.min(dp[j-1][(r-1+3)%3], dp[j-1][(r+1+3)%3]) + houses[j][r];
                }
            }

            /**
             * i == 0 이면: 첫번째 집의 색깔 = Red, 마지막 집의 색깔 = Green || Blue
             * i == 1 이면: 첫번째 집의 색깔 = Green, 마지막 집의 색깔 = Red || Blue
             * i == 2 이면: 첫번째 집의 색깔 = Blue, 마지막 집의 색깔 = Red || Green
             */
            ans = Math.min(ans, Math.min(dp[n][(i-1+3)%3], dp[n][(i+1+3)%3]));
        }

        System.out.println(ans);

    }

    public static void initDp() {
        for (int j = 0; j < 3; j++) {
            dp[1][j] = 1001;
        }
    }

}
