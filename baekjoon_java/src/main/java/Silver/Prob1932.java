package Silver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Silver 1
 * 
 * 문제
 *         7
 *       3   8
 *     8   1   0
 *   2   7   4   4
 * 4   5   2   6   5
 * 위 그림은 크기가 5인 정수 삼각형의 한 모습이다.
 * 맨 위층 7부터 시작해서 아래에 있는 수 중 하나를 선택하여 아래층으로 내려올 때, 이제까지 선택된 수의 합이 최대가 되는 경로를 구하는 프로그램을 작성하라.
 * 아래층에 있는 수는 현재 층에서 선택된 수의 대각선 왼쪽 또는 대각선 오른쪽에 있는 것 중에서만 선택할 수 있다.
 *
 * 삼각형의 크기는 1 이상 500 이하이다. 삼각형을 이루고 있는 각 수는 모두 정수이며, 범위는 0 이상 9999 이하이다.
 *
 * 입력
 * 첫째 줄에 삼각형의 크기 n(1 ≤ n ≤ 500)이 주어지고, 둘째 줄부터 n+1번째 줄까지 정수 삼각형이 주어진다.
 *
 * 출력
 * 첫째 줄에 합이 최대가 되는 경로에 있는 수의 합을 출력한다.
 * 
 * Solution: DP + Bottom Up
 * 1. 1~n 번째 줄이 있을때, n - 1 번째 줄부터 시작
 * 2. n - 1 번째 줄부터, 해당 줄에 있는 모든 정수들 탐색
 * 3. n - 1 번째 줄의 x 번째 원소에서, n 번째 줄에 있는 숫자들 중에서 더할수 있는 숫자들 중에서 가장 큰 숫자들 더해서 업데이트
 *  -> 이때, x 번째 원소는 n 번째 줄의 x 번째 또는 x + 1 번째 숫자들을 더할수 있음
 * 4. 1번째 줄까지 2~3번 반복
 *  -> 결국 (0,0)에 가장 큰 숫자가 저장됨 
 */
public class Prob1932 {

    static Integer[] tx = {0, 1};
    static Integer[][] triangle;
    static int n;
    static int ans;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.valueOf(br.readLine());
        triangle = new Integer[n][n];
        for (int y = 0; y < n; y++) {
            st = new StringTokenizer(br.readLine());
            for (int x = 0; x < y + 1; x++) {
                triangle[y][x] = Integer.parseInt(st.nextToken());
            }
        }

//        dfs(0, 0, 0);
        /**
         * DP
         */
        for (int y = n-2; y >= 0; y--) {
            for (int x = 0; x < y + 1; x++) {
                int nx1 = x + 1;
                int nx2 = x;

                int cnt1 = triangle[y][x];
                cnt1 += triangle[y + 1][nx1];

                int cnt2 = triangle[y][x];
                cnt2 += triangle[y + 1][nx2];

                triangle[y][x] = cnt1 > cnt2 ? cnt1 : cnt2;
            }
        }

//        for (int y = 0; y < n; y++) {
//            for (int x = 0; x < y + 1; x++) {
//                System.out.print(triangle[y][x] + " ");
//            }
//            System.out.println();
//        }
        ans = triangle[0][0];
        System.out.println(ans);
    }

    /**
     * DFS 로 풀이시 시간초과
     */
    public static void dfs(int cnt, int y, int x) {
        if (y == n) {
            if (cnt > ans) {
                ans = cnt;
            }
            return;
        }

        for (int i = 0; i < 2; i++) {
            int nx = x + tx[i];
            dfs(cnt + triangle[y][x], y + 1, nx);
        }
    }
}
