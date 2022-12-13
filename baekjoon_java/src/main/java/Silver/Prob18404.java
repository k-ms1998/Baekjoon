package Silver;

import java.io.*;
import java.util.*;

/**
 * Silver 1(현명한 나이트)
 *
 * https://www.acmicpc.net/problem/18404
 *
 * Solution: BFS
 * 1. BFS 로 시작 지점에서 도달하고 싶은 위치들까지의 거리 계산 (단순 BFS 문제)
 * 2. 이때, 각 도달 시간마다 거리를 계산하면 시간 초과 발생
 *  -> 그러므로, 시작 지점부터 모든 위치까지의 최단 거리 계산해서 visited 에 저장 후, 도달하고 싶은 위치들의 visited 값 출력
 */
public class Prob18404 {

    static int n, m;
    static int[][] visited;

    static int sx, sy;
    static int[] dx = {1, 2, 2, 1, -1, -2, -2, -1};
    static int[] dy = {-2, -1, 1, 2, 2, 1, -1, -2};
    static StringBuilder ans = new StringBuilder();
    static final int INF = 100000000;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        sx = Integer.parseInt(st.nextToken());
        sy = Integer.parseInt(st.nextToken());

        visited = new int[n + 1][n + 1];
        for(int y = 1; y < n + 1; y++){
            for(int x = 1; x < n + 1; x++){
                visited[y][x] = INF;
            }
        }
        visited[sy][sx] = 0;
        bfs();

        for(int i = 0; i < m; i++){
            st = new StringTokenizer(br.readLine());
            int fx = Integer.parseInt(st.nextToken());
            int fy = Integer.parseInt(st.nextToken());

            ans.append(visited[fy][fx]).append(" ");
        }

        System.out.println(ans);
    }

    public static void bfs(){
        Deque<Pos> queue = new ArrayDeque<>();
        queue.offer(new Pos(sx, sy, 0));

        while(!queue.isEmpty()){
            Pos pos = queue.poll();
            int x = pos.x;
            int y = pos.y;
            int d = pos.dist;

            for(int i = 0; i < 8; i++){
                int nx = x + dx[i];
                int ny = y + dy[i];

                if(nx <= 0 || ny <= 0 || nx > n || ny > n){
                    continue;
                }
                if (visited[ny][nx] > d + 1) {
                    visited[ny][nx] = d + 1;
                    queue.offer(new Pos(nx, ny, d + 1));
                }
            }
        }
    }

    public static class Pos{
        int x;
        int y;
        int dist;

        public Pos(int x, int y, int dist){
            this.x = x;
            this.y = y;
            this.dist = dist;
        }
    }
}
