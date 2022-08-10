package Gold.DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Gold 5
 *
 * 문제
 * n가지 종류의 동전이 있다. 이 동전들을 적당히 사용해서, 그 가치의 합이 k원이 되도록 하고 싶다.
 * 그러면서 동전의 개수가 최소가 되도록 하려고 한다. 각각의 동전은 몇 개라도 사용할 수 있다.
 * 사용한 동전의 구성이 같은데, 순서만 다른 것은 같은 경우이다.
 *
 * 입력
 * 첫째 줄에 n, k가 주어진다. (1 ≤ n ≤ 100, 1 ≤ k ≤ 10,000) 다음 n개의 줄에는 각각의 동전의 가치가 주어진다.
 * 동전의 가치는 100,000보다 작거나 같은 자연수이다. 가치가 같은 동전이 여러 번 주어질 수도 있다.
 *
 * 출력
 * 첫째 줄에 사용한 동전의 최소 개수를 출력한다. 불가능한 경우에는 -1을 출력한다.
 *
 * Solution : DP
 */
public class Prob2294 {

    static int n;
    static int k;
    static Integer[] coins;
    static Integer[] dp;
    static int maxK = 10001;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        coins = new Integer[n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            coins[i] = Integer.parseInt(st.nextToken());
        }

        dp = new Integer[k + 1];
        for (int i = 1; i < k + 1; i++) {
            if (i % coins[0] == 0) {
                dp[i] = i / coins[0];
            } else {
                dp[i] = maxK;
            }
        }
        dp[0] = 1;

        /**
         * 백준 2293: 동전 1과 비슷한 문제
         * dp[j] 에는 j 원을 만드는데 필요한 최소한의 동전의 갯수 등장
         */
        for (int i = 1; i < n; i++) {
            int coin = coins[i];
            for (int j = 1; j < k + 1; j++) {
                /**
                 * coin 이 j 보다 크면 coin 으로 j 원을 만들 수 있는 방법은 없기 때문에 coin 이 j 보다 작거나 같을때만 검사
                 */
                if (coin <= j) {
                    /**
                     * 현재 j의 값이 coin으로 나누어 떨어질 경우, dp[j] 의 값이 줄어 들 수 있기 때문에 일단 먼저 확인
                     * 이때, 동전이 주어진 순서에 따라서 dp[j] 의 값이 이미 더 작을 수 있음
                     * ex: j 의 값이 8 이고, 동전이 4원, 2원 순으로 주어 졌으면, 4원일때 더 적은 수의 동전 필요
                     */
                    if (j % coin == 0) {
                        dp[j] = Math.min(dp[j], j / coin);
                    }

                    /**
                     * j 원을 만드는 방법:
                     * 현재 coin 한개 + (j - coin)을 만드는데 필요한 동전의 갯수의 최솟값
                     * ->1 + dp[j-coin]
                     *
                     * 이때, 만약에 j 가 10이고 coin 이 5 이면:
                     * 5 짜리 하나 + 5를 만다는데 필요한 동전의 갯수의 최솟값 == 1 + 1 = 2
                     *  => 이미 5를 만다는데 5원 짜리 동전을 썼기 때문에 추가로 고려 X
                     *
                     * 이때, 만약에 j 가 11이고 coin 이 5 이면:
                     * 5 짜리 하나 + 6를 만다는데 필요한 동전의 갯수의 최솟값 == 1 + 2 == 3
                     *  => 이미 6를 만다는데 5원 짜리 동전을 썼기 때문에 추가로 고려 X
                     *
                     *  이때, 경우에 따라서 이미 dp[j]의 값이 더 작을 수도 있음:
                     *  ex: j == 15, coin == 12이고, 이미 coin == 5 인 경우 확인
                     *      => dp[j] = 3 (5원 짜리 3 개) vs. 1(12원 짜리 하나) + dp[15-12](3원을 만들때 필요한 최소의 동전 갯수)
                     *          -> dp[15-12] == dp[3] => 3원을 만들 수 있는 방법이 1원짜리 3개인 경우 밖에 없으면 1 + dp[3] == 4
                     *             -> 그러므로, dp[j] < 1 + dp[3]
                     */
                    dp[j] = Math.min(dp[j], 1 + dp[j - coin]);
                }
            }
//            System.out.println("List.of(dp) = " + List.of(dp));
        }

        int ans = dp[k];
        if (ans == maxK) {
            System.out.println(-1);
        } else {
            System.out.println(ans);
        }
    }
}
