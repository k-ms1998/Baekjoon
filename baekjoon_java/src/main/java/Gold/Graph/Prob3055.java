package Gold.Graph;

import java.io.*;
import java.util.*;

/**
 * Gold 4(탈출)
 *
 * https://www.acmicpc.net/problem/3055
 *
 * Solution: BFS
 */
public class Prob3055 {

    static int n, m;
    static char[][] grid;
    static boolean[][] visited;

    static Pos src;
    static Pos dst;

    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        grid = new char[n][m];
        visited = new boolean[n][m];
        PriorityQueue<Pos> queue = new PriorityQueue<>(new Comparator<Pos>(){
            @Override
            public int compare(Pos p1, Pos p2) {
                if (p1.time == p2.time) {
                    if(grid[p1.y][p1.x] == 'S'){
                        return 1;
                    }else{
                        return -1;
                    }
                }
                return p1.time - p2.time;
            }
        });
        for (int y = 0; y < n; y++) {
            String curRow = br.readLine();
            for(int x = 0; x < m; x++){
                char c = curRow.charAt(x);
                if(c == 'S'){
                    src = new Pos(x, y, 0);
                    queue.offer(src);
                }else if(c == 'D'){
                    dst = new Pos(x, y, 0);
                }else if(c == '*'){
                    queue.offer(new Pos(x, y, 1));
                }

                grid[y][x] = c;
            }
        }

        /**
         * 고슴도치 위치를 옮겨줄지, 물을 먼저 업데이트 할지 순서가 중요한 BFS 문제
         * (물을 업데이트하기 위해서 추가로 Deque/Queue 를 생성해서 BFS 를 실행시키면 메모리 초과 발생)
         * 1. 처음에 고슴도치를 이동시키고 물을 업데이트 함 (1번 예제를 참고하면 더 이해하기 쉬움)
         * 2. 이후, 만약에 고슴도치가 움직인 자리가 물로 채워질 경우, 물을 업데이트 하는 과정에서 grid 에서 해당 좌표에 저장된 값고 물인 '*'로 업데이트 시키기 때문에,
         * 해당 위치에 있는 고슴도치를 덮어씌우는 것이 됨.
         * 해당 좌표의 값은 '*'이므로, 해당 위치에 고슴도치가 있어도 고슴도치가 아닌 물로 인식하고, 물을 업데이트 시킴
         * 3. 이때, time 이 가장 작은 값으로 Min Heap 이 유지되도록 했기 때문에, 만약에 현재 좌표가 'D'의 좌표이면 바로 탐색 중단
         */
        boolean unreachable = true;
        int curTime = 0;
        visited[src.y][src.x] = true;
        while (!queue.isEmpty()) {
            Pos curPos = queue.poll();
            int x = curPos.x;
            int y = curPos.y;
            int time = curPos.time;

            if (x == dst.x && y == dst.y) {
                curTime = time;
                unreachable = false;
                break;
            }

            /**
             * 물이면 물 업데이트
             */
            if (grid[y][x] == '*') {
                for (int i = 0; i < 4; i++) {
                    int nx = x + dx[i];
                    int ny = y + dy[i];

                    if (nx < 0 || nx >= m || ny < 0 || ny >= n) {
                        continue;
                    }

                    if (grid[ny][nx] == 'X' || grid[ny][nx] == 'D' || grid[ny][nx] == '*') {
                        continue;
                    }
                    grid[ny][nx] = '*';
                    queue.offer(new Pos(nx, ny, time + 1));
                }
            } else{
                /**
                 * 고슴도치이면 고슴도치 이동
                 */
                for (int i = 0; i < 4; i++) {
                    int nx = x + dx[i];
                    int ny = y + dy[i];

                    if (nx < 0 || nx >= m || ny < 0 || ny >= n) {
                        continue;
                    }

                    if (grid[ny][nx] == 'X' || grid[ny][nx] == '*' || visited[ny][nx]) {
                        continue;
                    }
                    visited[ny][nx] = true;
                    queue.offer(new Pos(nx, ny, time + 1));
                }
            }
        }

        if(unreachable){
            System.out.println("KAKTUS");
        }else{
            System.out.println(curTime);
        }
    }

    public static class Pos{
        int x;
        int y;
        int time;

        public Pos(int x, int y){
            this.x = x;
            this.y = y;
        }

        public Pos(int x, int y, int time) {
            this.x = x;
            this.y = y;
            this.time = time;
        }

        @Override
        public String toString() {
            return "Pos{" +
                    "x=" + x +
                    ", y=" + y +
                    ", time=" + time +
                    '}';
        }
    }
}
