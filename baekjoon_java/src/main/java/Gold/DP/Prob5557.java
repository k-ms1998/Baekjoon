package Gold.DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Gold 5
 *
 * Solution: DP(Memoization)
 */
public class Prob5557 {

    static int n;
    static int[] num;
    static long[][] dp;
    static int target;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());

        num = new int[n - 1];
        /**
         * 1. 계산하는 값이 항상 0 이상 20 이하 임으로 , dp의 x도 0~20 까지만 있으면 됨(size == 21)
         * 2. 2번 입력 예시 보면, 결과 값이 7069052760 => 그러므로, dp는 int 가 아닌 long
         * 3. dp[y][x] 에는, 현재 y 번째 숫자까지 왔을 때, 값 x를 만들 수 있는 방법의 수 저장
         *      -> if num[] = {8,3,2,4,8,7,2,4,0,8,8},  y == 1, x == 5
         *          => y == 0 일때 만들 수 있는 값들 중에서, num[y] == num[1] == 3 을 덧셈 또는 뺄셈 해서 dp 업데이트
         *              => 5를 만들 수 있는 방법은, 8 에서 3을 뺴는 것이므로 dp[1][5] = 1
         */
        dp = new long[n-1][21];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n - 1; i++) {
            num[i] = Integer.parseInt(st.nextToken());
        }
        target = Integer.parseInt(st.nextToken()); // 입력 값 중에서 마지막 값은 만들고자 하는 값

        dp[0][num[0]] = 1;
        for (int y = 1; y < n - 1; y++) {
            int curNum = num[y];
            for (int x = 0; x <= 20; x++) {
                if (dp[y - 1][x] > 0) {
                    /**
                     * ex) dp[6][0] == 1, dp[6][8] == 3, y == 7
                     *  => num[7] == 4
                     *  => dp[7][4] 를 구하고 싶으면, 0 에서 4를 더하던가 8 에서 4를 뺴는 방법 두 가지가 있으면
                     *      => 그러므로, dp[7][4] = dp[6][0] + dp[6][8] => dp[7][4] == 1 + 3 == 4
                     */
                    if (x + curNum <= 20) {
                        dp[y][x + curNum] += dp[y - 1][x];
                    }
                    if (x - curNum >= 0) {
                        dp[y][x - curNum] += dp[y - 1][x];
                    }
                }
            }
        }

//        for (int y = 0; y < n - 1; y++) {
//            System.out.print(num[y] + ": ");
//            for (int x = 0; x <= 20; x++) {
//                System.out.print(dp[y][x] + " ");
//            }
//            System.out.println();
//        }
        
        System.out.println(dp[n-2][target]);

    }

}
