package Gold.Graph;

import java.io.*;
import java.util.*;

/**
 * Gold 1(벽 부수고 이동하기 3)
 *
 * https://www.acmicpc.net/problem/16933
 *
 * Solution: BFS
 */
public class Prob3108 {

    static int n, m, k;
    static int[][] grid;
    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};

    static int[][][] dist;
    static final int INF = 10000000;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        grid = new int[n][m];
        for(int y = 0; y < n; y++){
            String row = br.readLine();
            for(int x = 0; x < m; x++){
                grid[y][x] = Integer.parseInt(String.valueOf(row.charAt(x)));
            }
        }

        dist = new int[n][m][k + 1]; // (x, y, 벽 부순 갯수)
        for(int y = 0; y < n; y++){
            for(int x = 0; x < m; x++){
                for(int kk = 0; kk < k + 1; kk++){
                    dist[y][x][kk] = INF;
                }
            }
        }

        Deque<Info> queue = new ArrayDeque<>();
        queue.offer(new Info(0, 0, 0, 0, 1));
        dist[0][0][0] = 1;

        int answer = INF;
        while(!queue.isEmpty()){
            Info info = queue.poll();
            int x = info.x;
            int y = info.y;
            int broken = info.broken;
            int day = info.day;
            int d = info.d;

            if(x == m - 1 && y == n - 1){
                answer = Math.min(answer, d);

                continue;
            }

            int nDay = (day + 1) % 2;
            for(int i = 0; i < 4; i++){
                int nx = x + dx[i];
                int ny = y + dy[i];

                if(nx < 0 || ny < 0 || nx >= m || ny >= n){
                    continue;
                }

                if(grid[ny][nx] == 1){
                    if(broken + 1 <= k){
                        if(day == 0){
                            if(dist[ny][nx][broken + 1] > d + 1){
                                dist[ny][nx][broken + 1] = d + 1;
                                queue.offer(new Info(nx, ny, broken + 1, nDay, d + 1));
                            }
                        }else{
                            if(dist[ny][nx][broken + 1] > d + 2){
                                dist[ny][nx][broken + 1] = d + 2;
                                queue.offer(new Info(nx, ny, broken + 1, day, d + 2));
                            }
                        }
                    }

                }else{
                    if(dist[ny][nx][broken] > d + 1){
                        dist[ny][nx][broken] = d + 1;
                        queue.offer(new Info(nx, ny, broken, nDay, d + 1));
                    }
                }


            }
        }


        System.out.println(answer == INF ? -1 :answer);
    }

    public static class Info{
        int x;
        int y;
        int broken;
        int day;
        int d;

        public Info(int x, int y, int broken, int day, int d){
            this.x = x;
            this.y = y;
            this.broken = broken;
            this.day = day;
            this.d = d;
        }

        @Override
        public String toString(){
            return "{" + x + ", " + y + ", " + broken + ", " + day + ", " + d + "}";
        }
    }
}
/*
2 4 2
0111
0110
*/