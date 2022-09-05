package Gold.Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Gold 5
 *
 * https://www.acmicpc.net/problem/16928
 *
 * Solution: BFS(DP)
 */
public class Prob16928 {

    static int n;
    static int m;

    static int grid[] = new int[101];
    static int[] shootsAndLadders = new int[101];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        for (int i = 0; i < 101; i++) {
            grid[i] = Integer.MAX_VALUE;
        }

        /**
         * 하나의 칸에는 최대 하나의 사다리 또는 뱀이 있고, 둘 다 동시에 있는 경우는 없기 때문에 하나의 배열에 정보 저장
         */
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            shootsAndLadders[x] = y;
        }
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            shootsAndLadders[u] = v;
        }


        bfs();
        System.out.println(grid[100]);
    }

    public static void bfs() {
        PriorityQueue<Pos> queue = new PriorityQueue<>(new Comparator<Pos>(){
            @Override
            public int compare(Pos p1, Pos p2) {
                return p1.cnt - p2.cnt;
            }
        });
        queue.offer(new Pos(1, 0));

        while (!queue.isEmpty()) {
            Pos curPos = queue.poll();
            int curX = curPos.x;
            int curCnt = curPos.cnt;

            if (curX == 100) {
                return;
            }

            for (int i = 1; i <= 6; i++) {
                int nx = curX + i;
                if (nx <= 100) {
                    if (shootsAndLadders[nx] != 0) {
                        nx = shootsAndLadders[nx];
                    }
                    if (grid[nx] > curCnt + 1) {
                        grid[nx] = curCnt + 1;
                        queue.offer(new Pos(nx, curCnt + 1));
                    }
                }
            }
        }

    }

    static class Pos{
        int x;
        int cnt;

        public Pos(int x, int cnt) {
            this.x = x;
            this.cnt = cnt;
        }

        @Override
        public String toString() {
            return "Pos{" +
                    "x=" + x +
                    ", cnt=" + cnt +
                    '}';
        }
    }
}
