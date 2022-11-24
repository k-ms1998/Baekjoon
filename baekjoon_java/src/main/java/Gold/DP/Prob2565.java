package Gold.DP;

import java.io.*;
import java.util.*;

/**
 * Gold 5
 *
 * https://www.acmicpc.net/problem/2565
 *
 * Solution: DP (가장 긴 증가하는 부분 수열)
 */
public class Prob2565 {

    static int n;
    static int[] lines;
    static int[] dp;

    static int cnt = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        lines = new int[501];
        dp = new int[501];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            lines[a] = b;
            dp[a] = 1;
        }

        /**
         * 가장 긴 증가하는 부분 수열과 동일한 문제
         * 시작하는 지점(a)가 작은 순서대로 탐색 시작
         * 이때 서로 겹치지 않기 위해서는 현재 b가 이전에 나왔던 b들 보다 큰 값이어야 겹치지 않는다
         * ex:  a->b가 1->2일때, 앞으로 나올 a는 모두 1보다 크다
         * 이때, 앞으로 나올 b들은 이전에 나왔던 b 들보다 커야 켭치지 않는다
         */
        for (int i = 1; i < 501; i++) {
            if (lines[i] == 0) {
                continue;
            }
            for (int j = 1; j < i; j++) {
                if (lines[i] > lines[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            cnt = Math.max(cnt, dp[i]);
        }

        System.out.println(n - cnt);

    }
}
