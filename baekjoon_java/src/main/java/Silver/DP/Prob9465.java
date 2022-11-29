package Silver.DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Silver 1(스티커)
 *
 * https://www.acmicpc.net/problem/9465
 *
 * Solution: DP
 */
public class Prob9465 {

    static int n;
    static int[][] stickers;
    static int[][] dp;

    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int t = Integer.parseInt(br.readLine());
        while(t-- > 0) {
            n = Integer.parseInt(br.readLine());
            stickers = new int[2][n];
            dp = new int[2][n];
            for (int y = 0; y < 2; y++) {
                st = new StringTokenizer(br.readLine());
                for (int x = 0; x < n; x++) {
                    stickers[y][x] = Integer.parseInt(st.nextToken());
                }
            }
            ans.append(calculateAnswer()).append("\n");
//            printDp();
        }

        System.out.println(ans);
    }

    public static int calculateAnswer() {
        dp[0][0] = stickers[0][0];
        dp[1][0] = stickers[1][0];

        /**
         * dp[0][x] -> 첫번째 줄의 x번째 스티커까지의 최댓값
         * dp[1][x] -> 두번째 줄의 x번째 스티커까지의 최댓값
         *
         * dp[0][x]의 최댓값은 dp[0][x-1]이랑 dp[1][x-1]+sticker[0][x] 중 더 큰 값
         * -> 만약, sticker[0][x]를 사용하면, sticker[0][x-1]은 사용못하기 때문에 dp[1][x-1]의 값에 sticker[0][x]를 더해야됨
         */
        for (int x = 1; x < n; x++) {
            dp[0][x] = Math.max(dp[0][x - 1], dp[1][x - 1] + stickers[0][x]);
            dp[1][x] = Math.max(dp[1][x - 1], dp[0][x - 1] + stickers[1][x]);
        }

        return Math.max(dp[0][n - 1], dp[1][n - 1]);
    }

    private static void printDp() {
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < n; x++) {
                System.out.print(dp[y][x] + " ");
            }
            System.out.println();
        }
    }
}
