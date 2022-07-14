package Silver;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Silver 2: Dynamic Programming
 *
 * 문제
 * 수열 A가 주어졌을 때, 가장 긴 증가하는 부분 수열을 구하는 프로그램을 작성하시오.
 * 예를 들어, 수열 A = {10, 20, 10, 30, 20, 50} 인 경우에 가장 긴 증가하는 부분 수열은 A = {10, 20, 10, 30, 20, 50} 이고, 길이는 4이다.
 *
 * 입력
 * 첫째 줄에 수열 A의 크기 N (1 ≤ N ≤ 1,000)이 주어진다.
 *
 * 둘째 줄에는 수열 A를 이루고 있는 Ai가 주어진다. (1 ≤ Ai ≤ 1,000)
 *
 * 출력
 * 첫째 줄에 수열 A의 가장 긴 증가하는 부분 수열의 길이를 출력한다
 *
 * Solution: DP
 * A = {10, 20, 10, 30, 20, 50}
 *
 * 1 2 1 3 2 4
 *
 */
public class Prob11053 {

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.valueOf(br.readLine());
        String[] arrSplit = br.readLine().split(" ");
        Integer[] num = new Integer[n];
        for (int i = 0; i < n; i++) {
            num[i] = Integer.valueOf(arrSplit[i]);
        }

        Integer[] dp = new Integer[n];
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
        }

        int maxDp = 1;
        for(int y = 1; y < n; y++){
            int curDp = 1;
            int curNum = num[y];
            /**
             * 1. 기준점(y)를 두고, num[0]~num[y-1]의 값들과 num[y]의 값을 비교
             * 2. 기준점보다 값이 작으면 증가하는 수열 O
             * 3. 그러므로, dp[x] + 1의 값을 임시로 저장
             *      ->dp[x]에 저장된 값은 x 까지의 증가하는 수열의 최댓값을 저장하고 있음 -> 그러므로, 해당 값 + 1의 값이 x로부터 기준점까지의 증가하는 수열의 최댓값이 됨
             * 4. 증가하는 수열의 값들 중 최댓값을 dp[y]의 값으로 업데이트
             */
            for (int x = 0; x < y; x++) {
                if (curNum > num[x]) {
                    curDp = curDp < dp[x] + 1 ? dp[x] + 1 : curDp;
                }
            }
            dp[y] = curDp;
            if (curDp > maxDp) {
                maxDp = curDp;
            }
        }

        System.out.println(maxDp);
    }
}
