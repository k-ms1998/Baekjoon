package Review.Gold.Graph.BFS;

import java.io.*;
import java.util.*;

/**
 * (재채점에 의해 다시 풀이)
 * Gold 3(레이저 통신)
 *
 * https://www.acmicpc.net/problem/6087
 *
 * Solution: BFS
 * -> 3차원 배열 dist 의 값을 업데이트 하면서 필요한 거울의 최솟값 구하기
 *  -> dist[y][x][d] => (x, y)에서 레이저가 d 방향으로 가고 있을때 필요한 최소의 거울 수 저장
 */
public class Prob6087 {

    static int n, m;
    static int[][] grid;
    static int[][][] dist;
    static int[] tx = {1, 0, -1, 0};
    static int[] ty = {0, 1, 0, -1};

    static int sx = -1;
    static int sy = -1;
    static int dx = -1;
    static int dy = -1;
    static final int INF = 1000000000;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        grid = new int[m][n];
        dist = new int[m][n][4];
        for(int y= 0 ; y < m; y++){
            String cur = br.readLine();
            for(int x = 0; x < n; x++){
                Arrays.fill(dist[y][x], INF);
                char c = cur.charAt(x);
                if(c == '.'){
                    grid[y][x] = 0;
                }else if(c == '*'){
                    grid[y][x] = 1;
                }else{
                    grid[y][x] = 2;
                    if(sx == -1){
                        sx = x;
                        sy = y;
                    }else{
                        dx = x;
                        dy = y;
                    }
                }
            }
        }

        /**
         * Init:
         * 시작 좌표(sx, sy) 에서 4방향으로 레이저 쏘기
         */
        Deque<Info> queue = new ArrayDeque<>();
        for(int i = 0; i < 4; i++){
            int nx = sx + tx[i];
            int ny = sy + ty[i];

            if(nx < 0 || ny < 0 || nx >= n || ny >= m){
                continue;
            }

            queue.offer(new Info(nx, ny, i, 0));
            dist[ny][nx][i] = 0;
            dist[sy][sx][i] = 0;
        }

        int answer = INF;
        while (!queue.isEmpty()) {
            Info info = queue.poll();
            int x = info.x;
            int y = info.y;
            int dir = info.dir;
            int cnt = info.cnt;

            if (answer <= cnt) {
                continue;
            }
            if(grid[y][x] == 1){
                continue;
            }
            if (x == dx && y == dy) { //도착 좌표에 도달
                answer = Math.min(answer, cnt);
                continue;
            }

            /**
             * 현재 좌표로 부터 4방향으로 레이저 쏘기
             */
            for(int i = 0; i < 4; i++){
                int nx = x + tx[i];
                int ny = y + ty[i];

                if(nx < 0 || ny < 0 || nx >= n || ny >= m){
                    continue;
                }
                
                if((i + 2) % 4 == dir){ // 거울로 레어저의 방향을 90도만 돌릴 수 있기 떄문에, 180도 돌리는 방향은 제외
                    continue;
                } else if(i == dir){ // 거울을 설치하지 않고, 그대로 현제 방향으로 움직일때
                    if(dist[ny][nx][i] > cnt && grid[ny][nx] != 1){
                        dist[ny][nx][i] = cnt;
                        queue.offer(new Info(nx, ny, i, cnt));
                    }
                }else{
                    /**
                     * 거울로 시계 또는 반시계 방향으로 90도 회전 시킬때
                     * 이떄, 확인할 dist[][][] 값은 dist[ny][nx][i]가 아니라 dist[y][x][i]임
                     * Because, 방향을 회전 시켜서 (nx, ny)에 도달하기 위해서 (x, y)에 거울을 설치함
                     *  -> 그러므로, (x, y)에서 i 방향으로 레이저를 쏠때 사용된 거울의 개수를 확인해함
                     */
                    if(dist[y][x][i] > cnt){
                        dist[y][x][i] = cnt;
                        queue.offer(new Info(nx, ny, i, cnt + 1));
                    }
                }
            }
        }

        System.out.println(answer);
    }


    public static class Info{
        int x;
        int y;
        int dir;
        int cnt;

        public Info(int x, int y, int dir, int cnt){
            this.x = x;
            this.y = y;
            this.dir = dir;
            this.cnt = cnt;
        }

        @Override
        public String toString() {
            return "Info{" +
                    "x=" + x +
                    ", y=" + y +
                    ", dir=" + dir +
                    ", cnt=" + cnt +
                    '}';
        }
    }
}
