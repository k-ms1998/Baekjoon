package Gold.DP;

import java.io.*;
import java.util.*;

/**
 * Gold 2(사전)
 *
 * https://www.acmicpc.net/problem/1256
 *
 * Solution: 트리 + DP
 * 1. 트리를 이용해서, 분기점에서 조건에 따라서 'a' 또는 'z' 추가
 * 2. 첫번째 위치부터 현재 위칭에 'a' 또는 'z'가 올때, 앞으로 만들 수 있는 문자열의 갯수 확인
 * 3. 앞으로 만들 수 있는 문자열의 갯수와 k의 값을 비교해서, 'a'를 추가해서 왼쪽으로 내려갈지, 'z'를 추가해서 오른쪽으로 내려갈지 판별
 *              0
 *         a         z
 *     aa     az  za   zz
 *      aaz
 *       aazz
 */
public class Prob1256 {

    static int n, m;
    static long k;
    static long[][] dp;

    static final int INF = 1000000001;
    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Long.parseLong(st.nextToken());

        dp = new long[n + 1][m + 1];
        for (int i = 1; i < n + 1; i++) {
            dp[i][0] = 1;
        }
        for (int i = 1; i < m + 1; i++) {
            dp[0][i] = 1;
        }
        if (getMaxComb(n, m) < k) {
            System.out.println(-1);
        } else {
            findAnswer(1, 1L, 1, 0);
            System.out.println(ans);
        }

    }

    public static void findAnswer(long left, long depth, int cntA, int cntZ) {
        if (depth > n + m) {
            return;
        }

        if (cntA > n) {
            ans.append("z");
            findAnswer(left, depth + 1, cntA, cntZ + 1);
            return;
        }
        if(cntZ >= m){
            ans.append("a");
            findAnswer(left, depth + 1, cntA + 1, cntZ);
            return;
        }

        /**
         * 현재까지 cntA랑 cntZ 만큼 각각 a랑 z를 사용함
         * 그러므로, 앞으로 사용할 수 있는 a = n - cntA, 앞으로 사용할 수 있는 z = m - cntZ
         */
        long mid = getMaxComb(n - cntA, m - cntZ);
        if (k < left + mid) {
            ans.append("a");
            findAnswer(left,  depth + 1, cntA + 1, cntZ);
        } else {
            ans.append("z");
            findAnswer(left + mid, depth + 1, cntA, cntZ + 1);
        }
    }

    /**
     * dp[cntA][cntZ] = dp[cntA-1][cntZ] + dp[cntA][cntZ-1]
     */
    public static long getMaxComb(int cntA, int cntZ) {
        if(cntA <= 0 || cntZ <= 0){
            return dp[cntA][cntZ];
        }
        if (dp[cntA][cntZ] > 0) {
            return dp[cntA][cntZ];
        }

        return dp[cntA][cntZ] = Math.min(getMaxComb(cntA - 1, cntZ) + getMaxComb(cntA, cntZ - 1), INF);
    }
}