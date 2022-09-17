package Gold.DP;

import java.io.*;
import java.util.*;

/**
 * Gold 4 (가장 긴 증가하는 부분 수열 4)
 *
 * https://www.acmicpc.net/problem/14002
 *
 * Solution: DP
 */
public class Prob14002 {

    static int n;
    static int[] num;

    static int[] dp;
    static boolean[] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        num = new int[n + 1];
        dp = new int[n + 1];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i < n + 1; i++) {
            num[i] = Integer.parseInt(st.nextToken());
        }

        /**
         * DP 로 가장 긴 증가하는 수열 찾기
         */
        int maxV = 0;
        for (int i = 1; i < n + 1; i++) {
            for (int j = 0; j < i; j++) {
                /**
                 * num[i] 가 num[j] 보다 크면:
                 * 0~j 까지의 num 값에 num[i] 까지해도 증가하는 수열임
                 * 그러므로, dp[i] 의 값은 dp[j] + 1 값
                 *  -> dp[j]는 num[j] 까지의 증가하는 수열의 길이. 해당 수열에 num[i] 값을 추가해서 증가하는 수열을 만드는 것이기 때문에 dp[j] + 1 이 됨
                 */
                if (num[i] > num[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                    maxV = Math.max(maxV, dp[i]);
                }
            }
        }

        StringBuilder ans = new StringBuilder();

        ans.append(maxV).append("\n");

        /**
         * 역추적하기:
         * dp 값이 변하는 시점 == 수열이 증가하는 부분
         * 이때, dp 값이 가장 큰 값을 찾고, 해당 값부터 역추적 시작
         */
        int tmp = maxV;
        Stack<Integer> stack = new Stack<>();
        for (int i = n; i > 0; i--) {
            if (dp[i] == tmp) {
                stack.push(num[i]);
                tmp--;
            }
        }

        while (!stack.isEmpty()) {
            ans.append(stack.pop()).append(" ");
        }

        System.out.println(ans);
    }
}
