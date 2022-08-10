package Gold.DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Gold 5
 *
 * 문제
 * n가지 종류의 동전이 있다. 각각의 동전이 나타내는 가치는 다르다. 이 동전을 적당히 사용해서, 그 가치의 합이 k원이 되도록 하고 싶다. 그 경우의 수를 구하시오. 각각의 동전은 몇 개라도 사용할 수 있다.
 * 사용한 동전의 구성이 같은데, 순서만 다른 것은 같은 경우이다.
 *
 * 입력
 * 첫째 줄에 n, k가 주어진다. (1 ≤ n ≤ 100, 1 ≤ k ≤ 10,000) 다음 n개의 줄에는 각각의 동전의 가치가 주어진다. 동전의 가치는 100,000보다 작거나 같은 자연수이다.
 *
 * 출력
 * 첫째 줄에 경우의 수를 출력한다. 경우의 수는 231보다 작다.
 *
 * Solution: DP
 */
public class Prob2293 {

    static int n;
    static int k;
    static Integer[] coins;
    static Integer[] dp;

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
        for (int x = 0; x < k + 1; x++) {
            dp[x] = 0;
        }
        dp[0] = 1;

        for (int i = 0; i < n; i++) {
            int coin = coins[i];
            for (int j = 0; j < k + 1; j++) {
                if (coin <= j) {
                    /**
                     * i = 0 -> coin = 1 일때:
                     * dp[j] 는 j 원을 1원짜리 동전으로 만들 수 있는 방법의 수
                     *  -> dp[j] += dp[j-coin] => 이전에 나온 동전들로 j원을 만들 수 있는 방법 + 현재 i 번째 동전을 사용해서 만들 수 있는 방법
                     *      -> Why dp[j - coin] ? => 예를 들어, 현재 동전이 1원 짜리이고, j 가 3이면, 1원짜리로 동전을 만다는 방법은 2(3 - 1 == j - coin)원에서 1원을 더하기
                     *          -> 그러므로, 2원을 만들 수 있는 방법의 수를 추가해줌
                     * i = 1 -> coin = 2 일때:
                     * dp[j] 는 j 원을 1원짜리랑 2원 짜리 동전으로 만들 수 있는 방법의 수
                     *  -> dp[j] += dp[j-coin] => 이전에 나온 동전들로(1원 짜리 동전으로) j원을 만들 수 있는 방법 + 현재 i 번째 동전(2원 짜리 동전)을 사용해서 만들 수 있는 방법
                     *      -> Why dp[j - coin] ? => 예를 들어, 현재 동전이 2원 짜리이고, j 가 3이면, 2원짜리로 동전을 만다는 방법은 1(3 - 2 == j - coin)원에서 2원을 더하기
                     *      -> Why dp[j - coin] ? => 예를 들어, 현재 동전이 2원 짜리이고, j 가 4이면, 2원짜리로 동전을 만다는 방법은 2(3 - 2 == j - coin)원에서 2원을 더하기
                     *          -> 이때, dp[2]에는 이미 1원 짜리 && 2원짜리 동전들을 이용해서 2원을 만들 수 있는 경우의 수 모두 포함
                     *          -> 그러므로, 2원을 만들 수 있는 방법의 수를 추가해줌
                     */
                    dp[j] += dp[j - coin];
                }
            }
        }
//        System.out.println("List.of(dp) = " + List.of(dp));
        System.out.println(dp[k]);
    }
}
