package Silver.DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * Silver 1
 *
 * 문제
 * N×N 게임판에 수가 적혀져 있다. 이 게임의 목표는 가장 왼쪽 위 칸에서 가장 오른쪽 아래 칸으로 규칙에 맞게 점프를 해서 가는 것이다.
 * 각 칸에 적혀있는 수는 현재 칸에서 갈 수 있는 거리를 의미한다.
 * 반드시 오른쪽이나 아래쪽으로만 이동해야 한다. 0은 더 이상 진행을 막는 종착점이며, 항상 현재 칸에 적혀있는 수만큼 오른쪽이나 아래로 가야 한다.
 * 한 번 점프를 할 때, 방향을 바꾸면 안 된다. 즉, 한 칸에서 오른쪽으로 점프를 하거나, 아래로 점프를 하는 두 경우만 존재한다.
 * 가장 왼쪽 위 칸에서 가장 오른쪽 아래 칸으로 규칙에 맞게 이동할 수 있는 경로의 개수를 구하는 프로그램을 작성하시오.
 *
 * 입력
 * 첫째 줄에 게임 판의 크기 N (4 ≤ N ≤ 100)이 주어진다. 그 다음 N개 줄에는 각 칸에 적혀져 있는 수가 N개씩 주어진다.
 * 칸에 적혀있는 수는 0보다 크거나 같고, 9보다 작거나 같은 정수이며, 가장 오른쪽 아래 칸에는 항상 0이 주어진다.
 *
 * 출력
 * 가장 왼쪽 위 칸에서 가장 오른쪽 아래 칸으로 문제의 규칙에 맞게 갈 수 있는 경로의 개수를 출력한다. 경로의 개수는 263-1보다 작거나 같다.
 * 
 * Solution: DP
 * -> DP 로 해결하지 않으면 시간 초과 발생
 */
public class Prob1890 {

    static int n;
    static int[][] grid;
    static long[][] dpGrid;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.valueOf(br.readLine());
        grid = new int[n][n];
        dpGrid = new long[n][n];
        for (int y = 0; y < n; y++) {
            st = new StringTokenizer(br.readLine());
            for (int x = 0; x < n; x++) {
                grid[y][x] = Integer.parseInt(st.nextToken());
                dpGrid[y][x] = 0;
            }
        }
        dpGrid[0][0] = 1;

        /**
         * dpGrid[y][x] 에 해당 칸으로 갈 수 있는 경로의 갯수 저장
         * if:
         * grid =   1 1 1
         *          2 2 0
         * dpGrid = 1 0 1
         *          1 0 2
         * 이때 (1, 0) 에서 (2, 0) 으로 이동은 가능하지만, (1, 0) 까지 갈 수 있는 경로(dpGrid[0][1]) == 0
         *  -> 그러므로, dp[0][2] = dp[0][2] + dp[0][1] = 1 + 0 = 1
         */
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < n; x++) {
                if (grid[y][x] == 0) {
                    continue;
                }

                int nx = x + grid[y][x];
                int ny = y + grid[y][x];

                if (nx < n) {
                    dpGrid[y][nx] += dpGrid[y][x];
                }
                if (ny < n) {
                    dpGrid[ny][x] += dpGrid[y][x];
                }
            }
        }

        System.out.println(dpGrid[n - 1][n - 1]);
    }
}
