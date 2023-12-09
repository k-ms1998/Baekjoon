package Gold.Graph.BFS;

import java.io.*;
import java.util.*;

public class Prob5213{

    static int n;
    static int[][] grid;
    static int[][] nums;
    static int[][] dist;
    static int[][] prev;

    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};

    static StringBuilder ans = new StringBuilder();

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        grid = new int[n + 1][2*n + 1];
        dist = new int[n + 1][2*n + 1];
        nums = new int[n + 1][2*n + 1];
        prev = new int[n + 1][2*n + 1];

        int yy = 0;
        int xx = 0;
        int inputSize = n * n - n/2;
        for(int i = 1; i <= inputSize; i++){
            // System.out.println("i=" + i + ", xx=" + xx + ", yy=" + yy);
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            if(yy % 2 == 0){
                grid[yy][xx] = a;
                grid[yy][xx + 1] = b;
                nums[yy][xx] = i;
                nums[yy][xx + 1] = i;
                xx += 2;
                if(xx >= 2*n){
                    yy++;
                    xx = 1;
                    grid[yy][0] = 0;
                }
            }else{
                grid[yy][xx] = a;
                grid[yy][xx + 1] = b;
                nums[yy][xx] = i;
                nums[yy][xx + 1] = i;
                xx += 2;
                if(xx >= 2*(n-1)){
                    yy++;
                    xx = 0;
                }
            }
        }

        Deque<Point> q = new ArrayDeque<>();
        q.offer(new Point(0, 0, 1));
        dist[0][0] = 1;
        q.offer(new Point(1, 0, 1));
        dist[0][1] = 1;

        int aNum = -1;
        int aX = -1;
        int aY = -1;
        while(!q.isEmpty()){
            Point p = q.poll();
            int x = p.x;
            int y = p.y;
            int d = p.d;

            if(aNum < nums[y][x]){
                aX = x;
                aY = y;
                aNum = nums[y][x];
            }

            for(int i = 0; i < 4; i++){
                int nx = x + dx[i];
                int ny = y + dy[i];

                if(nx < 0 || ny < 0 || nx >= 2*n || ny >= n){
                    continue;
                }
                if(grid[ny][nx] == 0){
                    continue;
                }

                if(nums[ny][nx] != nums[y][x]){
                    if(grid[ny][nx] == grid[y][x] &&
                            (dist[ny][nx] > d + 1 || dist[ny][nx] == 0)){
                        dist[ny][nx] = d + 1;
                        prev[ny][nx] = (i + 2) % 4;

                        int ny1 = ny + dy[0];
                        int nx1 = nx + dx[0];
                        int ny2 = ny + dy[2];
                        int nx2 = nx + dx[2];

                        if(nx1 >= 0 && ny1 >= 0 && nx1 < 2*n && ny1 < n){
                            if(nums[ny1][nx1] == nums[ny][nx]){
                                dist[ny1][nx1] = d + 1;
                                prev[ny1][nx1] = 2;
                                q.offer(new Point(nx1, ny1, d + 1));
                            }
                        }
                        if(nx2 >= 0 && ny2 >= 0 && nx2 < 2*n && ny2 < n){
                            if(nums[ny2][nx2] == nums[ny][nx]){
                                dist[ny2][nx2] = d + 1;
                                prev[ny2][nx2] = 0;
                                q.offer(new Point(nx2, ny2, d + 1));
                            }
                        }

                        q.offer(new Point(nx, ny, d + 1));
                    }
                }
            }
        }

        ans.append(dist[aY][aX]).append("\n");
        findRoute(aX, aY, String.valueOf(nums[aY][aX]) + " ");

        System.out.println(ans);
    }

    public static void findRoute(int x, int y, String route){
        if(nums[y][x] == 1){
            String[] arr = route.split(" ");
            for(int i = arr.length - 1; i >= 0; i--){
                ans.append(arr[i]).append(" ");
            }
            return;
        }

        int d = prev[y][x];
        int nx = x + dx[d];
        int ny = y + dy[d];

        if(nums[y][x] == nums[ny][nx]){
            findRoute(nx, ny, route);
        }else{
            findRoute(nx, ny, route + nums[ny][nx] + " ");
        }
    }

    public static class Point{
        int x;
        int y;
        int d;

        public Point(int x, int y, int d){
            this.x = x;
            this.y = y;
            this.d = d;
        }

        @Override
        public String toString(){
            return "{x=" + x + ", y=" + y + ", d=" + d + "}";
        }
    }
}