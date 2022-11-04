package Gold.Graph;

import java.io.*;
import java.util.*;

/**
 * Gold 3(다리 만들기)
 *
 * https://www.acmicpc.net/problem/2146
 *
 * Solution: BFS
 */
public class Prob2146 {

    static int n;
    static int[][] grid;
    static int[][] gridCnt;
    static int groupNum = 1;

    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};

    static int ans = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        grid = new int[n][n];
        gridCnt = new int[n][n];
        for (int y = 0; y < n; y++) {
            st = new StringTokenizer(br.readLine());
            for (int x = 0; x < n; x++) {
                grid[y][x] = Integer.parseInt(st.nextToken());
                gridCnt[y][x] = Integer.MAX_VALUE;
            }
        }

        /**
         * 같은 섬끼리 묵어주기
         * 1. 입력 받은 격자에서, 섬이 있는 위치에 도달하면 묶어 주기 시작
         * 2. 시작 지점부터, 끊기지 않고 도달 가능한 섬의 좌표들은 모두 하나의 섬
         * 3. BFS 로 도달 가능한 섬들을 찾고, 해당 섬들이 같은 섬이라는 것을 표기하기 위해 숫자를 매겨줌
         * 이때, 0은 바다, 1은 섬이므로, 2 이상의 숫자로 매겨주고, 각 섬들의 묶음이 서로 다른 섬이라는 것을 판별하기 위해 서로 다른 숫자를 매겨줌
         */
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < n; x++) {
                if (grid[y][x] == 1) {
                    groupGrid(x, y);
                }
            }
        }

        /**
         * 두 개의 섬을 연결시켜주는 최단 거리 다리 놓기
         * 1. 격자에서 섬인 부분 찾기 (숫자가 2이상)
         * 2. 해당 위치로부터 연결된 바다가 있으면, 해당 바다로 이동하기 (BFS)
         * 3. 만약, 바다가 아니라 섬에 도달했을때, 해당 섬에 매겨진 숫자 보기
         * 시작한 위치의 섬과 같은 숫자이면 같은 섬이므로 무시, 만약 다른 숫자이면 다른 섬이므로 해당 섬까지의 거리를 확인
         */
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < n; x++) {
                if(grid[y][x] >= 2){
                    buildBridge(x, y);
                }
            }
        }

        System.out.println(ans);
    }

    /**
     * BFS 로 시작한 섬의 위치로 부터 도달 가능한 모든 바다들을 탐색하고, 만약 바다가 아닌 곳에 도달하면 해당 위치에 매겨진 숫자를 통해 같은 섬인지 다른 섬인지 판별
     */
    public static void buildBridge(int x, int y) {
        Deque<Pos> queue = new ArrayDeque<>();
        queue.offer(new Pos(x, y, 0));
        int startGroup = grid[y][x];
        while (!queue.isEmpty()) {
            Pos curPos = queue.poll();
            int curX = curPos.x;
            int curY = curPos.y;
            int curCnt = curPos.cnt;

            for (int i = 0; i < 4; i++) {
                int nx = curX + dx[i];
                int ny = curY + dy[i];

                if (nx < 0 || nx >= n || ny < 0 || ny >= n) {
                    continue;
                }

                if (grid[ny][nx] == 0) {
                    if (gridCnt[ny][nx] > curCnt + 1) {
                        gridCnt[ny][nx] = curCnt + 1;
                        queue.offer(new Pos(nx, ny, curCnt + 1));
                    }
                }else{
                    if (grid[ny][nx] != startGroup) {
                        ans = Math.min(ans, curCnt);
                    }
                }
            }
        }
    }

    /**
     * BFS 로 끊기지 않고 도달 가능한 섬들 찾기
     */
    public static void groupGrid(int x, int y) {
        ++groupNum;
        grid[y][x] = groupNum;
        Deque<Pos> queue = new ArrayDeque<>();
        queue.offer(new Pos(x, y));
        while (!queue.isEmpty()) {
            Pos pos = queue.poll();
            int curX = pos.x;
            int curY = pos.y;

            for (int i = 0; i < 4; i++) {
                int nx = curX + dx[i];
                int ny = curY + dy[i];

                if (nx < 0 || nx >= n || ny < 0 || ny >= n) {
                    continue;
                }

                if (grid[ny][nx] != 1) {
                    continue;
                }

                grid[ny][nx] = groupNum;
                queue.offer(new Pos(nx, ny));
            }
        }
    }

    public static class Pos{
        int x;
        int y;
        int cnt;

        public Pos(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Pos(int x, int y, int cnt) {
            this.x = x;
            this.y = y;
            this.cnt = cnt;
        }
    }
}
