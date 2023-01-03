package Gold;

import java.io.*;
import java.util.*;

/**
 * Gold 3(로봇)
 * 
 * https://www.acmicpc.net/problem/1726
 *
 * Solution: BFS
 * 3차원 배열을 사용해서, 각 좌표마다 바라보는 방향에 따라서 따로 최단거리 저장
 */
public class Prob1726 {

    public static final int INF = 1000000000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int m = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

        int[][] grid = new int[m + 1][n + 1];
        int[][][] cost = new int[m + 1][n + 1][5]; //(y, x, dir)
        for (int y = 1; y < m + 1; y++) {
            st = new StringTokenizer(br.readLine());
            for (int x = 1; x < n + 1; x++) {
                grid[y][x] = Integer.parseInt(st.nextToken());
                for (int d = 1; d < 5; d++) {
                    cost[y][x][d] = INF;
                }
            }
        }

        // 행, 열, 방향 입력 -> (y, x, dir)
        st = new StringTokenizer(br.readLine());
        int srcY = Integer.parseInt(st.nextToken());
        int srcX = Integer.parseInt(st.nextToken());
        int srcD = Integer.parseInt(st.nextToken());
        cost[srcY][srcX][srcD] = 0;

        st = new StringTokenizer(br.readLine());
        int dstY = Integer.parseInt(st.nextToken());
        int dstX = Integer.parseInt(st.nextToken());
        int dstD = Integer.parseInt(st.nextToken());

        /*
        동쪽: 1, 서쪽: 2, 남쪽:3, 북쪽: 4
         */
        int[] dx = {0, 1, -1, 0, 0};
        int[] dy = {0, 0, 0, 1, -1};

        /**
         * BFS 로 출발 지점부터 각 점까지의 최단거리 계산
         * 최단 거리 계산시, 바라보는 방향도 고려해서, 바라보고 있는 방향에 따라서도 따로 최단거리 저장 -> 3차원 배열 사용 (y, x, dir)
         */
        Deque<Info> queue = new ArrayDeque<>();
        queue.offer(new Info(srcX, srcY, srcD, 0));
        while (!queue.isEmpty()) {
            Info cur = queue.poll();
            int curX = cur.x;
            int curY = cur.y;
            int curD = cur.d;
            int curC = cur.c;

            if (curX == dstX && curY == dstY && curD == dstD) {
                continue;
            }

            /**
             * 방향을 바꾸지 않고, 바라보는 방향 앞으로 전진할때
             * 1~3 만큼 한번에 이동할 수 있는데, 중간에 벽이 있으면 더 이상 전진 X
             */
            for (int k = 1; k <= 3; k++) {
                int nx = curX + k * dx[curD];
                int ny = curY + k * dy[curD];

                if (nx > 0 && ny > 0 && nx <= n && ny <= m) {
                    if (grid[ny][nx] == 0) {
                        if (cost[ny][nx][curD] > curC + 1) {
                            cost[ny][nx][curD] = curC + 1;
                            queue.offer(new Info(nx, ny, curD, curC + 1));
                        }
                    } else {
                        /*
                        벽을 만나면 break
                         */
                        break;
                    }
                }
            }

            /**
             * 현재 방향에서 왼쪽 또는 오른쪽으로 90도 방향 전환
             */
            int rightD = 0;
            int leftD = 0;
            if (curD == 1) {
                rightD = 3;
                leftD = 4;
            } else if (curD == 2) {
                rightD = 4;
                leftD = 3;
            } else if (curD == 3) {
                rightD = 2;
                leftD = 1;
            } else if (curD == 4) {
                rightD = 1;
                leftD = 2;
            }
            /*
            오른쪽으로 방향 전환
             */
            if (cost[curY][curX][rightD] > curC + 1) {
                cost[curY][curX][rightD] = curC + 1;
                queue.offer(new Info(curX, curY, rightD, curC + 1));
            }
            /*
            왼쪽으로 방향 전환
             */
            if (cost[curY][curX][leftD] > curC + 1) {
                cost[curY][curX][leftD] = curC + 1;
                queue.offer(new Info(curX, curY, leftD, curC + 1));
            }
        }

        System.out.println(cost[dstY][dstX][dstD]);
    }

    public static class Info{
        int x;
        int y;
        int d;
        int c;

        public Info(int x, int y, int d, int c) {
            this.x = x;
            this.y = y;
            this.d = d;
            this.c = c;
        }

        @Override
        public String toString(){
            return "{x=" + x + ", y=" + y + ", d=" + d + ", c=" + c + "}";
        }
    }
}
