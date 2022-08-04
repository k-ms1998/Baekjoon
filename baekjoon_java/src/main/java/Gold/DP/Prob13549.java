package Gold.DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * Gold 5
 *
 * 문제
 * 수빈이는 동생과 숨바꼭질을 하고 있다. 수빈이는 현재 점 N(0 ≤ N ≤ 100,000)에 있고, 동생은 점 K(0 ≤ K ≤ 100,000)에 있다. 수빈이는 걷거나 순간이동을 할 수 있다.
 * 만약, 수빈이의 위치가 X일 때 걷는다면 1초 후에 X-1 또는 X+1로 이동하게 된다. 순간이동을 하는 경우에는 0초 후에 2*X의 위치로 이동하게 된다.
 * 수빈이와 동생의 위치가 주어졌을 때, 수빈이가 동생을 찾을 수 있는 가장 빠른 시간이 몇 초 후인지 구하는 프로그램을 작성하시오.
 *
 * 입력
 * 첫 번째 줄에 수빈이가 있는 위치 N과 동생이 있는 위치 K가 주어진다. N과 K는 정수이다.
 *
 * 출력
 * 수빈이가 동생을 찾는 가장 빠른 시간을 출력한다.
 *
 * Solution: BFS + DP
 */
public class Prob13549 {

    static int n;
    static int k;
    static Integer[] dp;
    static int maxX;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        /**
         * n(수빈이) < k(동생) 일 경우  => 동생이 수빈이 보다 뒤에 있는 경우
         * n(수빈이) > k(동생) 일 경우  => 수빈이가 동생 보다 뒤에 있는 경우
         * 
         * maxX == 최대 움직일 수 있는 X 좌표
         * -> 최대 X의 범위를 정해주지 않으면 무한 루프 발생
         *  -> 최대 범위가 없을 경우, 제한 없이 x+1 또는 2*x 가 가능해짐
         */
        maxX = n > k ? 2 * n : 2 * k;
        dp = new Integer[maxX + 1];
        for (int i = 0; i < maxX + 1; i++) {
            dp[i] = Integer.MAX_VALUE;
        }

        bfs();
        System.out.println(dp[k]);
    }

    public static void bfs() {
        Deque<Integer> queue = new ArrayDeque<>();
        dp[n] = 0;
        queue.offer(n);

        /**
         * BFS 로 모든 경우의 수 확인
         */
        while (!queue.isEmpty()) {
            int curX = queue.poll();
            int cnt = dp[curX];

            for (int i = 0; i < 3; i++) {
                int nx = curX;
                int nCnt = cnt;
                if (i == 0) {
                    nx += 1;
                    nCnt++;
                } else if (i == 1) {
                    nx -= 1;
                    nCnt++;
                } else {
                    /**
                     * 조건에 의해 순간 이동시 시간 증가 X
                     */
                    nx *= 2;
                }

                /**
                 * nx의 좌표가 허용된 범위 안에 있는지 확인
                 */
                if (nx >= 0 && nx <= maxX) {
                    /**
                     * nx 까지 걸리는 시간이 현재 시간보다 큰 경우 == nx 까지 걸리는 시간이 단축 가능
                     *  -> nx 까지 걸리는 시간(dp[nx]) 업데이트
                     *  -> nx 까지 걸리는 시간이 단축됐기 때문에 nx 는 다시 탐색해야 되는 대상이 됨 -> queue 에 추가
                     */
                    if (dp[nx] > nCnt) {
                        dp[nx] = nCnt;
                        queue.offer(nx);
                    }
                }

            }
        }

    }
}
