package Gold.BruteForce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Gold 4
 *
 * https://www.acmicpc.net/problem/14502
 *
 * Solution: Brute Force + DFS + BFS
 */
public class Prob14502 {

    static int n;
    static int m;
    static Integer[][] grid;

    static int[] tx = {1, -1, 0, 0};
    static int[] ty = {0, 0, 1, -1};

    static int ans = 0;
    static int initSafeZoneCnt = 0;
    static List<Pos> initList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        grid = new Integer[n][m];
        for (int y = 0; y < n; y++) {
            st = new StringTokenizer(br.readLine());
            for (int x = 0; x < m; x++) {
                int cur = Integer.parseInt(st.nextToken());
                if (cur == 0) {
                    initSafeZoneCnt++;
                } else if (cur == 2) {
                    initList.add(new Pos(x, y));
                }
                grid[y][x] = cur;
            }
        }

        installWalls(0, 0, 0);

        System.out.println(ans);

    }

    /**
     * DFS 로 새로운 벽 3개 설치하기
     */
    static void installWalls(int sx, int sy, int cnt) {
        if (cnt == 3) {
            spreadViruses();
            return;
        }

        /**
         * (0,0) 부터 시작해서, (n, m)까지 탐색하면서 새로운 벽을 설치할 장소 탐색
         * 현재 줄 보다 낮은 y 값에 새로운 벽을 설치하는 경우는 없기 때문에 y는 sy 부터 탐색
         * But, y값이 변하면 x는 0부터 탐색하면서 새로운 벽을 설치할 장소 탐색
         */
        for (int y = sy; y < n; y++) {
            for (int x = 0; x < m; x++) {
                if (grid[y][x] == 0) {
                    grid[y][x] = 1;
                    installWalls(x, y, cnt + 1);
                    grid[y][x] = 0;
                }
            }
        }
    }

    static void spreadViruses() {
        /**
         * Shallow Copy
         */
        Integer[][] tmpGrid = new Integer[n][m];
        for (int y = 0; y < n; y++) {
            tmpGrid[y] = grid[y].clone();
        }

        int curVirusCnt = 0;
        Queue<Pos> queue = new LinkedList<>();
        for(Pos initPos : initList){
            queue.offer(initPos);
        }

        /**
         * BFS 로 바이러스 퍼트리기
         */
        while (!queue.isEmpty()) {
            Pos cur = queue.poll();
            int curX = cur.x;
            int curY = cur.y;

            for (int i = 0; i < 4; i++) {
                int nx = curX + tx[i];
                int ny = curY + ty[i];

                if (nx >= 0 && nx < m && ny >= 0 && ny < n) {
                    if (tmpGrid[ny][nx] == 0) {
                        tmpGrid[ny][nx] = 2;
                        curVirusCnt++;
                        queue.offer(new Pos(nx, ny));
                    }
                }
            }
        }

        countSafeZone(curVirusCnt);
    }

    static public void countSafeZone(int curVirusCnt) {
        /**
         * 바이러스가 퍼진 후 남아 있는 빈 칸 = 초기 상태의 빈칸(initSafeZoneCnt) - 3(새로 설치한 벽의 갯수) - 바이러스가 차지하는 칸(curVirusCnt)
         * 이때, 초기 상태의 빈칸은 이미 초기 상태에서 바이러스가 존재하는 장소도 제외된 칸의 갯수이기 때문에, curVirusCnt 에 초기 상태의 바이러스 칸의 갯수를 더해주지 않는다
         */
        int cnt = (initSafeZoneCnt - 3) - curVirusCnt;

        ans = Math.max(ans, cnt);
    }

    static class Pos{
        int x;
        int y;

        public Pos(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
}
