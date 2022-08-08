package Silver.DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Silver 2
 *
 * 문제
 * 준규는 N×M 크기의 미로에 갇혀있다. 미로는 1×1크기의 방으로 나누어져 있고, 각 방에는 사탕이 놓여져 있다. 미로의 가장 왼쪽 윗 방은 (1, 1)이고, 가장 오른쪽 아랫 방은 (N, M)이다.
 * 준규는 현재 (1, 1)에 있고, (N, M)으로 이동하려고 한다.
 * 준규가 (r, c)에 있으면, (r+1, c), (r, c+1), (r+1, c+1)로 이동할 수 있고, 각 방을 방문할 때마다 방에 놓여져있는 사탕을 모두 가져갈 수 있다. 또, 미로 밖으로 나갈 수는 없다.
 * 준규가 (N, M)으로 이동할 때, 가져올 수 있는 사탕 개수의 최댓값을 구하시오.
 *
 * 입력
 * 첫째 줄에 미로의 크기 N, M이 주어진다. (1 ≤ N, M ≤ 1,000)
 * 둘째 줄부터 N개 줄에는 총 M개의 숫자가 주어지며, r번째 줄의 c번째 수는 (r, c)에 놓여져 있는 사탕의 개수이다. 사탕의 개수는 0보다 크거나 같고, 100보다 작거나 같다.
 *
 * 출력
 * 첫째 줄에 준규가 (N, M)으로 이동할 때, 가져올 수 있는 사탕 개수를 출력한다.
 *
 * Solution: DP
 */
public class Prob11048 {

    static int n;
    static int m;
    /**
     * grid: 각 (x, y) 칸에 놓여진 사탕의 갯수 저장
     * ansGrid: DP 로 (x, y) 칸에서 가져올 수 있는 가장 많은 사탕의 개수 저장
     */
    static int[][] grid;
    static int[][] ansGrid;

    static int[] tx = {1, 0, 1};
    static int[] ty = {0, 1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        grid = new int[n + 1][m + 1];
        ansGrid = new int[n + 1][m + 1];
        for (int y = 1; y < n + 1; y++){
            st = new StringTokenizer(br.readLine());
            for (int x = 1; x < m + 1; x++) {
                grid[y][x] = Integer.parseInt(st.nextToken());
                ansGrid[y][x] = grid[y][x];
            }
        }

        /**
         * 시간 복잡도: O(NM)
         */
        for (int y = 1; y < n + 1; y++) {
            for (int x = 1; x < m + 1; x++) {
                for (int i = 0; i < 3; i++) {
                    int nx = x + tx[i];
                    int ny = y + ty[i];

                    if (nx > m || ny > n) {
                        continue;
                    }

                    ansGrid[ny][nx] = ansGrid[ny][nx] > ansGrid[y][x] + grid[ny][nx] ? ansGrid[ny][nx] : ansGrid[y][x] + grid[ny][nx];
                }
            }
        }

        System.out.println(ansGrid[n][m]);
    }
}
