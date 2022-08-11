package Silver.DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Silver 2
 *
 * 문제
 * 수열 A가 주어졌을 때, 가장 긴 감소하는 부분 수열을 구하는 프로그램을 작성하시오.
 * 예를 들어, 수열 A = {10, 30, 10, 20, 20, 10} 인 경우에 가장 긴 감소하는 부분 수열은 A = {10, !30!, 10, !20!, 20, !10!}  이고, 길이는 3이다.
 *
 * 입력
 * 첫째 줄에 수열 A의 크기 N (1 ≤ N ≤ 1,000)이 주어진다.
 * 둘째 줄에는 수열 A를 이루고 있는 A가 주어진다. (1 ≤ A ≤ 1,000)
 *
 * 출력
 * 첫째 줄에 수열 A의 가장 긴 감소하는 부분 수열의 길이를 출력한다.\
 *
 * Solution: DP
 */
public class Prob11722 {

    static int n;
    static Integer[] num;
    static Integer[] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());

        num = new Integer[n];
        dp = new Integer[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            num[i] = Integer.parseInt(st.nextToken());
            dp[i] = 1;
        }

        /**
         * 자기 자신보다 앞에 있는 숫자들을 탐색
         * -> 자기 자신 보다 크면, 해당 숫자와 현재 숫자는 감소하는 수열 O -> dp[i] = dp[j] + 1
         *  -> 이때, dp[j] 에는 이미 j 번째 숫자까지의 감소하는 수열의 길이가 들어 있음
         *      -> ex: 1 4 3 2 에서:
         *          -> i == 3, j == 1 => num[i] == 2, num[j] == 4
         *              -> 4 , 2는 감소하는 수열 O -> dp[i] = max(dp[i], dp[j]+1)
         *          -> i == 3, j == 2 => num[i] == 2, num[j] == 3
         *              -> 3, 2는 감소하는 수열 O
         *                  -> !! 이때, dp[j] 에는 이미 4, 3 의 감소하는 수열의 길이가 들어 있음 !!
         *                      -> 해당 수열에 2를 추가하는 것이기 때문에, 2를 포함한 감소하는 수열의 길이는 dp[j] + 1 이 됨
         */
        for (int i = 0; i < n; i++) {
            int curNum = num[i];
            for (int j = 0; j < i; j++) {
                if (curNum < num[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
//            System.out.println("List.of(dp) = " + List.of(dp));
        }

        int ans = 0;
        for (int i = 0; i < n; i++) {
            if (ans < dp[i]) {
                ans = dp[i];
            }
        }

        System.out.println(ans);
    }
}
//7
//1 4 3 2 6 5 1