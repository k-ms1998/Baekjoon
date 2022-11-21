package Gold.Dijkstra;

import java.io.*;
import java.util.*;

/**
 * Gold 3 (레이저 통신)
 *
 * https://www.acmicpc.net/problem/6087
 *
 * Solution: Dijkstra
 * DFS 로 풀이할때보다 훨씬 더 빠르게 해결
 */
public class Prob6087_Dijkstra {

    static int w, h;
    static char[][] grid;
    static int[][] mirrors;
    static int x1, y1, x2, y2;

    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};
    static int ans;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        w = Integer.parseInt(st.nextToken());
        h = Integer.parseInt(st.nextToken());

        grid = new char[h][w];
        mirrors = new int[h][w];
        int cCount = 0;
        for (int y = 0; y < h; y++) {
            String curRow = br.readLine();
            for (int x = 0; x < w; x++) {
                grid[y][x] = curRow.charAt(x);
                mirrors[y][x] = Integer.MAX_VALUE;
                if(grid[y][x] == 'C'){
                    if (cCount == 0) {
                        x1 = x;
                        y1 = y;
                    } else {
                        x2 = x;
                        y2 = y;
                    }
                    cCount++;
                }
            }
        }

        mirrors[y1][x1] = 0;
        dijkstra();
        System.out.println(ans);
    }

    /**
     * 거울의 수가 최소인 경우를 유지하면서 다익스트라로 풀이
     */
    public static void dijkstra() {
        PriorityQueue<Info> queue = new PriorityQueue<>(new Comparator<Info>(){
            @Override
            public int compare(Info i1, Info i2) {
                return i1.cnt - i2.cnt;
            }
        });
        queue.offer(new Info(x1, y1, 0, -1));
        while (!queue.isEmpty()) {
            Info curInfo = queue.poll();
            int x = curInfo.x;
            int y = curInfo.y;
            int cnt = curInfo.cnt;
            int dir = curInfo.dir;
            if (x == x2 && y == y2) {
                ans = cnt;
                return;
            }
            /**
             * mirrors 는 (x, y) 좌표까지 도달하는데 필요한 최소한의 거울의 수를 저장하고 있음
             * 현재 (x, y) 까지 도달하는데 필요한 거울의 수(cnt)보다 mirrors[y][x]가 작으면 continue
             *  -> Because, cnt 가 더 크면, 현재 탐색하고 있는 경로로는 최소한의 거울을 사용해서 도착지점에 도달할 수 없기 때문에
             * But, cnt == mirrors[y][x]이면 계속 탐색을 이어간다
             *  -> Because, 이전에 놓인 거울의 방향과 현재 놓이는 거울의 방향이 다를 경우, 나중에 필요한 거울의 수가 다를 수도 있기 때문에
             */
            if (mirrors[y][x] < cnt) {
                continue;
            }
            mirrors[y][x] = Math.min(mirrors[y][x], cnt);
            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];

                if (nx < 0 || nx >= w || ny < 0 || ny >= h) {
                    continue;
                }
                if(grid[ny][nx] == '*'){
                    continue;
                }

                /**
                 * queue 에 x, y 좌표, 현재까지 사용된 거울의 수, 그리고 레이저가 이동하는 방향을 저장
                 * 레이저의 이동 방향(dir)의 변화가 없거나(dir == i), 시작 지점(dir == -1)이면 cnt 사용된 거울의 수가 증가하지 않았음 -> 그대로 cnt 넘겨줌
                 * 레이저의 이동 방향(dir)의 변화가 있으면(dir != i) 거울이 추가가됨 -> cnt + 1 넘겨줌
                 */
                queue.offer(new Info(nx, ny, dir == i || dir == -1 ? cnt : cnt + 1, i));
            }

        }
    }

    public static class Info {
        int x;      // x 좌표
        int y;      // y 좌표
        int cnt;    // (x,y)까지 도달하는데 사용된 거울의 수
        int dir;    // 현재 레이저의 이동방향

        public Info(int x, int y, int cnt, int dir) {
            this.x = x;
            this.y = y;
            this.cnt = cnt;
            this.dir = dir;
        }

        @Override
        public String toString() {
            return "Info: {x="+x+", y="+y+", cnt="+cnt+", dir="+dir+"}";
        }
    }

}
