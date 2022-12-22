package Gold.DP;

import java.io.*;
import java.util.*;

/**
 * Gold 3(카드 게임)
 *
 * https://www.acmicpc.net/problem/11062
 *
 * Solution: DFS + DP
 * (https://loosie.tistory.com/212)
 * 1. DFS 로 모든 경우의 수 탐색
 * 2. 시간 초과 발생하는 것을 예방하기 위해서 메모이제이션(DP)을 통해 풀이
 * 3. 근우가 얻게되는 최고의 점수를 구하는 문제. 그러므로, 근우는 최고의 점수를 구하고, 명우는 최소의 점수를 얻어야 근우가 최고의 점수를 얻을 수 있음
 * 4. dp[근우/명우][Left Idx][Right Idx]
 */
public class Prob11062 {

    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int t = Integer.parseInt(br.readLine());
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine());
            int[] nums = new int[n + 1];
            int[][][] dp = new int[2][n + 1][n + 1];

            st = new StringTokenizer(br.readLine());
            for (int i = 1; i < n + 1; i++) {
                nums[i] = Integer.parseInt(st.nextToken());
                dp[0][i][i] = nums[i];
            }

            ans.append(findAnswer(dp, nums, 1, n, 0)).append("\n");
        }

        System.out.println(ans);
    }

    public static int findAnswer(int[][][] dp, int[]nums, int left, int right, int player) {
        if (left >= right || dp[player][left][right] != 0) {
            return dp[player][left][right];
        }
        if (player == 0) {
            return dp[player][left][right] = Math.max(findAnswer(dp, nums, left + 1, right, 1) + nums[left],
                    findAnswer(dp, nums, left, right - 1, 1) + nums[right]);
        } else {
            return dp[player][left][right] = Math.min(findAnswer(dp, nums, left + 1, right, 0),
                    findAnswer(dp, nums, left, right - 1, 0));
        }
    }
}