package Gold.Dijkstra;

import java.io.*;
import java.util.*;

public class Prob4485 {

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int order = 0;
        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};
        StringBuilder ans = new StringBuilder();
        while(true){
            ++order;
            int n = Integer.parseInt(br.readLine());
            if (n == 0) {
                break;
            }

            int grid[][] = new int[n][n];
            boolean visited[][] = new boolean[n][n];
            for (int y = 0; y < n; y++) {
                st = new StringTokenizer(br.readLine());
                for (int x = 0; x < n; x++) {
                    grid[y][x] = Integer.parseInt(st.nextToken());
                }
            }
            PriorityQueue<Pos> queue = new PriorityQueue<>(new Comparator<Pos>(){
                @Override
                public int compare(Pos p1, Pos p2) {
                    return p1.d - p2.d;
                }
            });
            queue.offer(new Pos(0, 0, grid[0][0]));
            visited[0][0] = true;

            while (!queue.isEmpty()) {
                Pos cur = queue.poll();
                int x = cur.x;
                int y = cur.y;
                int d = cur.d;

                if (x == n - 1 && y == n - 1) {
                    ans.append("Problem ").append(order).append(": ").append(d).append("\n");
                    break;
                }

                for (int i = 0; i < 4; i++) {
                    int nx = x + dx[i];
                    int ny = y + dy[i];

                    if(nx < 0 || ny < 0 || nx >= n || ny >= n){
                        continue;
                    }

                    if(!visited[ny][nx]){
                        visited[ny][nx] = true;
                        queue.offer(new Pos(nx, ny, d + grid[ny][nx]));
                    }
                }
            }
        }

        System.out.println(ans);
    }

    public static class Pos{
        int x;
        int y;
        int d;

        public Pos(int x, int y, int d) {
            this.x = x;
            this.y = y;
            this.d = d;
        }
    }
}
