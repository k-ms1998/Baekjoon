package Silver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Silver 1 Dynamic Programming
 *
 * 문제
 * Day Of Mourning의 기타리스트 강토는 다가오는 공연에서 연주할 N개의 곡을 연주하고 있다.
 * 지금까지 공연과는 다른 공연을 보여주기 위해서 이번 공연에서는 매번 곡이 시작하기 전에 볼륨을 바꾸고 연주하려고 한다.
 * 먼저, 공연이 시작하기 전에 각각의 곡이 시작하기 전에 바꿀 수 있는 볼륨의 리스트를 만들었다.
 * 이 리스트를 V라고 했을 때, V[i]는 i번째 곡을 연주하기 전에 바꿀 수 있는 볼륨을 의미한다. 항상 리스트에 적힌 차이로만 볼륨을 바꿀 수 있다.
 * 즉, 현재 볼륨이 P이고 지금 i번째 곡을 연주하기 전이라면, i번 곡은 P+V[i]나 P-V[i] 로 연주해야 한다. 하지만, 0보다 작은 값으로 볼륨을 바꾸거나, M보다 큰 값으로 볼륨을 바꿀 수 없다.
 * 곡의 개수 N과 시작 볼륨 S, 그리고 M이 주어졌을 때, 마지막 곡을 연주할 수 있는 볼륨 중 최댓값을 구하는 프로그램을 작성하시오. 모든 곡은 리스트에 적힌 순서대로 연주해야 한다.
 *
 * 입력
 * 첫째 줄에 N, S, M이 주어진다. (1 ≤ N ≤ 50, 1 ≤ M ≤ 1,000, 0 ≤ S ≤ M) 둘째 줄에는 각 곡이 시작하기 전에 줄 수 있는 볼륨의 차이가 주어진다. 이 값은 1보다 크거나 같고, M보다 작거나 같다.
 *
 * 출력
 * 첫째 줄에 가능한 마지막 곡의 볼륨 중 최댓값을 출력한다. 만약 마지막 곡을 연주할 수 없다면 (중간에 볼륨 조절을 할 수 없다면) -1을 출력한다.
 *
 * Solution:
 * Dynamic Programming
 * ex)
 * 3 5 10
 * 5 3 7
 *
 * dp:
 *   0 1 2 3 4 5 6 7 8 9 10
 * 0 F F F F F T F F F F F -> 초기화: dp[0][s] = true
 * 1 T F F F F F F F F F T -> dp[0] 중에서, 값이 true인 숫자에 v[0](== 5)의 값을 더하고 뺀 값 구하기 -> {0, 10} -> 값들이 0이상 m 이하이면 dp[1]에 해당 값 == true
 * 2 F F F T F F F T F F F -> dp[1] 중에서, 값이 true인 숫자에 v[1](== 3)의 값을 더하고 뺀 값 구하기 -> {3, 7} -> 값들이 0이상 m 이하이면 dp[2]에 해당 값 == true
 * 3 T F F F F F F F F F T -> dp[2] 중에서, 값이 true인 숫자에 v[2](== 7)의 값을 더하고 뺀 값 구하기 -> {0, 10} -> 값들이 0이상 m 이하이면 dp[3]에 해당 값 == true
 *
 */
public class Prob1495 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String conditions = br.readLine();
        String[] conditionsSplit = conditions.split(" ");
        int n = Integer.valueOf(conditionsSplit[0]);
        int s = Integer.valueOf(conditionsSplit[1]);
        int m = Integer.valueOf(conditionsSplit[2]);

        String vLine = br.readLine();
        List<Integer> v = Arrays.stream(vLine.split(" "))
                .map(e -> Integer.valueOf(e))
                .collect(Collectors.toList());

        /**
         * DP 초기화
         */
        Boolean[][] dp = new Boolean[n+1][m+1];
        for (int y = 0; y < n + 1; y++) {
            for (int x = 0; x < m + 1; x++) {
                dp[y][x] = false;
            }
        }
        dp[0][s] = true;
        for (int y = 0; y < n; y++) {
            int num = v.get(y);
            for (int x = 0; x < m + 1; x++) {
                /**
                 * dp[y+1] 업데이트
                 */
                if (dp[y][x] == true) {
                    if (x + num <= m) {
                        dp[y + 1][x + num] = true;
                    }
                    if (x - num >= 0) {
                        dp[y + 1][x - num] = true;
                    }
                }
            }
        }

        /**
         * 가장 큰 값을 찾는 것이기 때문에 뒤에서 부터 순회
         */
        for(int x = m; x >= -1; x--){
            if (x == -1) {
                /**
                 * 중간에 끊기지 않고 x == -1 까지 도달
                 * -> 0~m 사이에 true 없음
                 * -> 연주 불가능
                 * => -1 출력
                 */
                System.out.println(x);
                break;
            }
            if (dp[n][x] == true) {
                System.out.println(x);
                break;
            }
        }
    }
}
