package Gold.Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Gold 5
 *
 * 문제
 * 로봇 청소기가 주어졌을 때, 청소하는 영역의 개수를 구하는 프로그램을 작성하시오.
 * 로봇 청소기가 있는 장소는 N×M 크기의 직사각형으로 나타낼 수 있으며, 1×1크기의 정사각형 칸으로 나누어져 있다. 각각의 칸은 벽 또는 빈 칸이다.
 * 청소기는 바라보는 방향이 있으며, 이 방향은 동, 서, 남, 북중 하나이다. 지도의 각 칸은 (r, c)로 나타낼 수 있고, r은 북쪽으로부터 떨어진 칸의 개수, c는 서쪽으로 부터 떨어진 칸의 개수이다.
 * 로봇 청소기는 다음과 같이 작동한다.
 *
 * 1. 현재 위치를 청소한다.
 * 2. 현재 위치에서 현재 방향을 기준으로 왼쪽방향부터 차례대로 탐색을 진행한다.
 *  2-1. 왼쪽 방향에 아직 청소하지 않은 공간이 존재한다면, 그 방향으로 회전한 다음 한 칸을 전진하고 1번부터 진행한다.
 *  2-2. 왼쪽 방향에 청소할 공간이 없다면, 그 방향으로 회전하고 2번으로 돌아간다.
 *  2-3. 네 방향 모두 청소가 이미 되어있거나 벽인 경우에는, 바라보는 방향을 유지한 채로 한 칸 후진을 하고 2번으로 돌아간다.
 *  2-4. 네 방향 모두 청소가 이미 되어있거나 벽이면서, 뒤쪽 방향이 벽이라 후진도 할 수 없는 경우에는 작동을 멈춘다.
 * 로봇 청소기는 이미 청소되어있는 칸을 또 청소하지 않으며, 벽을 통과할 수 없다.
 *
 * 입력
 * 첫째 줄에 세로 크기 N과 가로 크기 M이 주어진다. (3 ≤ N, M ≤ 50)
 * 둘째 줄에 로봇 청소기가 있는 칸의 좌표 (r, c)와 바라보는 방향 d가 주어진다. d가 0인 경우에는 북쪽을, 1인 경우에는 동쪽을, 2인 경우에는 남쪽을, 3인 경우에는 서쪽을 바라보고 있는 것이다.
 * 셋째 줄부터 N개의 줄에 장소의 상태가 북쪽부터 남쪽 순서대로, 각 줄은 서쪽부터 동쪽 순서대로 주어진다. 빈 칸은 0, 벽은 1로 주어진다. 지도의 첫 행, 마지막 행, 첫 열, 마지막 열에 있는 모든 칸은 벽이다.
 * 로봇 청소기가 있는 칸의 상태는 항상 빈 칸이다.
 *
 * 출력
 * 로봇 청소기가 청소하는 칸의 개수를 출력한다.
 *
 * Solution: DFS
 */
public class Prob14503 {

    static int n;
    static int m;
    static int sx;
    static int sy;
    static int[][] grid;
    static int ans = 0;

    /**
     * d == 0 -> 북
     * d == 1 -> 동
     * d == 2 -> 남
     * d == 3 -> 서
     * <p>
     * 규칙 상, 현재 방향의 왼쪽을 탕색해야됨
     * ->d는 -1 씩 변해야됨 -> 그러므로, 방향이 변할 경우 => d = (d+3)%4
     *
     * d == 0 -> (tx, ty) == (0, -1)
     * d == 1 -> (tx, ty) == (1, 0)
     * d == 2 -> (tx, ty) == (0, 1)
     * d == 3 -> (tx, ty) == (-1, 0)
     */
    static int[] tx = {0, 1, 0, -1};
    static int[] ty = {-1, 0, 1, 0};

    /**
     * 2-4 규칙에 의해, 후진을 할 수 없으면 최종 종료
     */
    static boolean finalDeadEnd = false;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        sy = Integer.parseInt(st.nextToken());
        sx = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());

        grid = new int[n][m];
        for (int y = 0; y < n; y++) {
            st = new StringTokenizer(br.readLine());
            for (int x = 0; x < m; x++) {
                grid[y][x] = Integer.parseInt(st.nextToken());
            }
        }

        dfs(sx, sy, d);
        System.out.println(ans);
    }

    public static void dfs(int x, int y, int d) {
        /**
         * findDeadEnd == true -> 최종 종료 조건에 도달 -> 더 이상 탐색 필요 X
         */
        if(finalDeadEnd){
            return;
        }

        if (grid[y][x] == 0) {
            ans++;
            /**
             * 청소 X == 0, 벽 == 1, 청소 O == 2
             * 2-4 규칙에 의해, 후진 할때 벽인지 아니면 이미 청소한 구역인지 구별을 해야됨
             */
            grid[y][x] = 2;
        }

        /**
         * 네 방향이 모두 벽이거나 청소되어 있는 경우인지 확인
         */
        boolean deadEnd = true;
        for (int i = 0; i < 4; i++) {
            int nx = x + tx[i];
            int ny = y + ty[i];

            /**
             * 이동할 수 있는 방향이 있는지 확인
             * -> 이동할 수 있는 방향이 있으면 2-3 또는 2-4 고려 X
             *  => deadEnd = false
             */
            if (nx >= 0 && nx < m && ny >= 0 && ny < n) {
                if (grid[ny][nx] == 0) {
                    deadEnd = false;
                    break;
                }
            }
        }


        if (deadEnd) {
            int nx = x - tx[d];
            int ny = y - ty[d];

            if(nx < 0 || nx >= m || ny < 0 || ny >= n ){
                return;
            } else if (grid[ny][nx] == 1) {
                finalDeadEnd = true;
                return;
            } else {
                dfs(nx, ny, d);
            }
        }
        else {
            for (int i = 0; i < 4; i++) {
                /**
                 * 방향 회전
                 */
                d = (d + 3) % 4;
                int nx = x + tx[d];
                int ny = y + ty[d];

                if (grid[ny][nx] == 0) {
                    dfs(nx, ny, d);
                }
            }
        }
    }
}
