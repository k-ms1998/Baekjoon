package Gold.Graph.BFS;

import java.io.*;
import java.util.*;

/**
 * Gold 1(수영장 만들기)
 *
 * https://www.acmicpc.net/problem/1113
 *
 * Solution: 구현 + BFS(Flood)
 */
public class Prob1113 {

    static int n, m;
    static int[][] grid;
    static int maxW = 0;

    static int[][] ans;
    static int answer = 0;

    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        grid = new int[n][m];
        ans = new int[n][m];
        for(int y = 0; y < n; y++){
            String row = br.readLine();
            for(int x = 0; x < m; x++){
                grid[y][x] = Integer.parseInt(String.valueOf(row.charAt(x)));
                maxW = Math.max(maxW, grid[y][x]);
            }
        }

        for(int w = maxW; w >= 2; w--){
            int[][] tmp = new int[n][m];
            for(int y = 0; y < n; y++){
                for(int x = 0; x < m; x++){
                    tmp[y][x] = w <= grid[y][x] ? 0 : w - grid[y][x];
                }
            }

            boolean[][] visited = new boolean[n][m];
            for(int y = 0; y < n; y++){
                for(int x = 0; x < m; x++){
                    if(!visited[y][x] && tmp[y][x] > 0 && ans[y][x] == 0){
                        boolean flag = false; // (x, y)의 좌표에서 도달 가능한 좌표들 중에서 가장 자리에 있는 좌표가 있는지 없는지 판별

                        Deque<Point> queue = new ArrayDeque<>();
                        List<Point> list = new ArrayList<>();

                        queue.offer(new Point(x, y));
                        visited[y][x] = true;
                        list.add(new Point(x, y));

                        while(!queue.isEmpty()){
                            Point p = queue.poll();
                            int xx = p.x;
                            int yy = p.y;

                            if(xx == 0 || yy == 0 || xx == m - 1 || yy == n - 1){ // 가장 바깥 쪽에 있는 좌표일 경우
                                flag = true;
                            }

                            for(int d = 0; d < 4; d++){
                                int nx = xx + dx[d];
                                int ny = yy + dy[d];

                                if(nx < 0 || ny < 0 || nx >= m || ny >= n){
                                    continue;
                                }

                                if(!visited[ny][nx] && tmp[ny][nx] > 0){
                                    list.add(new Point(nx, ny));
                                    visited[ny][nx] = true;
                                    queue.offer(new Point(nx, ny));
                                }
                            }
                        }

                        for(Point p : list){
                            int xx = p.x;
                            int yy = p.y;

                            for(int d = 0; d < 4; d++){
                                int nx = xx + dx[d];
                                int ny = yy + dy[d];
                                if(nx < 0 || ny < 0 || nx >= m || ny >= n){
                                    flag = true;
                                    break;
                                }
                            }
                            if(flag){
                                break;
                            }
                        }

                        if(!flag){
                            for(Point p : list){
                                int xx = p.x;
                                int yy = p.y;

                                ans[yy][xx] = Math.max(ans[yy][xx], tmp[yy][xx]);
                                answer += ans[yy][xx];
                            }
                        }
                    }
                }
            }

        }

        System.out.println(answer);
    }

    public static class Point{
        int x;
        int y;

        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }

    }

    public static void printGrid(int[][] tmp){
        for(int y = 0; y < n; y++){
            for(int x = 0; x < m; x++){
                System.out.print(tmp[y][x]);
            }
            System.out.println();
        }
        System.out.println("----------");
    }

}