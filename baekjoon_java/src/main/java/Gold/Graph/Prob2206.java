package Gold.Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * Gold 4
 *
 * https://www.acmicpc.net/problem/2206
 *
 * Solution: BFS
 *
 */
public class Prob2206 {

    static int n;
    static int m;
    static char[][] grid;

    static int[][] visitedA;
    static int[][] visitedB;
    static int[] tx = {1, -1, 0, 0};
    static int[] ty = {0, 0, 1, -1};

    static int ans = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        grid = new char[n + 1][m + 1];
        visitedA = new int[n + 1][m + 1];
        visitedB = new int[n + 1][m + 1];
        for (int y = 0; y < n + 1; y++) {
            String str = "";
            if (y > 0) {
                str = br.readLine();
            }
            for (int x = 0; x < m + 1; x++) {
                if (y == 0 || x == 0) {
                    grid[y][x] = '2';
                    continue;
                }
                grid[y][x] = str.charAt(x-1);
                visitedA[y][x] = Integer.MAX_VALUE;
                visitedB[y][x] = Integer.MAX_VALUE;
            }
        }


        /**
         * DFS 로 풀이시 시간초과 발생 -> BFS로 풀기
         */
        bfs();

        ans = Math.min(visitedA[n][m], visitedB[n][m]);
        if (ans == Integer.MAX_VALUE) {
            ans = -1;
        }
        System.out.println(ans);
    }

    public static void bfs() {
        Deque<Pos> queue = new ArrayDeque<>();
        queue.offer(new Pos(1, 1, 0, 1));
        visitedA[1][1] = 1;
        visitedB[1][1] = 1;

        while (!queue.isEmpty()) {
            Pos pos = queue.poll();
            int curX = pos.x;
            int curY = pos.y;
            int curFlag = pos.flag;
            int curCnt = pos.cnt;

            /**
             * visitedA: 벽을 부수지 않고 이동 했을떄의 최단 거리 저장
             * visitedB: 벽을 부수고 이동 했을때의 최단 거리 저장
             */

            if (curFlag == 0) {
                if (visitedA[curY][curX] < curCnt) {
                    continue;
                }
            } else {
                if (visitedB[curY][curX] < curCnt) {
                    continue;
                }
            }

            for (int i = 0; i < 4; i++) {
                int nx = curX + tx[i];
                int ny = curY + ty[i];

                if (nx >= 1 && nx <= m && ny >= 1 && ny <= n) {
                    if (grid[ny][nx] == '0') {
                        /**
                         * 다음으로 움직일 칸이 벽이 아닌 경우에는, 현재 벽을 부순 상태인지 아닌 상태인지 확인:
                         * 1. 벽을 부순 상태가 아니면, visitedA 의 값을 확인하고 업데이트 + queue 에 추가
                         * 2. 벽을 부순 상태이면 visitedB 의 값을 확인하고 업데이트 + queue 에 추가
                         */
                        if (curFlag == 0) {
                            if(visitedA[ny][nx] > curCnt + 1){
                                visitedA[ny][nx] = curCnt + 1;
                                queue.offer(new Pos(nx, ny, curFlag, curCnt + 1));
                            }
                        } else {
                            if(visitedB[ny][nx] > curCnt + 1){
                                visitedB[ny][nx] = curCnt + 1;
                                queue.offer(new Pos(nx, ny, curFlag, curCnt + 1));
                            }
                        }
                    } else if (grid[ny][nx] == '1') {
                        /**
                         * 다음으로 움직일 칸이 벽인 경우에는, 이미 벽을 부수지 않은 상태일때만 부수고 진행 가능
                         */
                        if (curFlag == 0) {
                            if(visitedB[ny][nx] > curCnt + 1){
                                visitedB[ny][nx] = curCnt + 1;
                                queue.offer(new Pos(nx, ny, 1, curCnt + 1));
                            }
                        }
                    }
                }
            }
        }
    }

    static class Pos{
        int x;
        int y;
        int flag;
        int cnt;

        public Pos(int x, int y, int flag, int cnt) {
            this.x = x;
            this.y = y;
            this.flag = flag;
            this.cnt = cnt;
        }

        @Override
        public String toString() {
            return "Pos{" +
                    "x=" + x +
                    ", y=" + y +
                    ", flag=" + flag +
                    ", cnt=" + cnt +
                    '}';
        }
    }
}
