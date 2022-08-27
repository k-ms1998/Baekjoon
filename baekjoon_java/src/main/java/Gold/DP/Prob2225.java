package Gold.DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Gold 5
 *
 * 문제
 * 0부터 N까지의 정수 K개를 더해서 그 합이 N이 되는 경우의 수를 구하는 프로그램을 작성하시오.
 * 덧셈의 순서가 바뀐 경우는 다른 경우로 센다(1+2와 2+1은 서로 다른 경우). 또한 한 개의 수를 여러 번 쓸 수도 있다.
 *
 * 입력
 * 첫째 줄에 두 정수 N(1 ≤ N ≤ 200), K(1 ≤ K ≤ 200)가 주어진다.
 *
 * 출력
 * 첫째 줄에 답을 1,000,000,000으로 나눈 나머지를 출력한다.
 */
public class Prob2225 {

    static int n;
    static int k;
    static int[][] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        dp = new int[k + 1][n + 1];

        for (int y = 1; y < k + 1; y++) {
            for (int x = 0; x < n + 1; x++) {
                if (y == 1 || x == 0) {
                    dp[y][x] = 1;
                    continue;
                }
                /**
                 * ex:
                 * 3 = 3 + 0    => 2개의 숫자로 3을 만드는 방법 + 0을 추가
                 *   = 2 + 1    => 2개의 숫자로 2을 만드는 방법 + 1을 추가
                 *   = 1 + 2    => 2개의 숫자로 1을 만드는 방법 + 2을 추가
                 *   = 0 + 3    => 2개의 숫자로 0을 만드는 방법 + 3을 추가
                 *  ->  = dp[2][3] + dp[2][2] + dp[2][1] + dp[2][0]
                 *      -> = dp[3][2] + dp[2][3]
                 */
                dp[y][x] = (dp[y - 1][x] + dp[y][x - 1])%1000000000;
            }
        }

//        for (int y = 1; y < k + 1; y++) {
//            System.out.println(List.of(dp[y]));
//        }
        System.out.println(dp[k][n]);
    }
}
