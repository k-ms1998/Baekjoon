package Gold.Graph.BFS;
import java.io.*;
import java.util.*;

public class Prob2636 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int height, width;
    static int[][] grid;
    static int[][] time;
    static Deque<Point> cheese = new ArrayDeque<>();

    static final int[] dCol = {1, 0, -1, 0};
    static final int[] dRow = {0, 1, 0, -1};
    static final int INF = 100_000_000;

    public static void main(String[] args) throws IOException{
        st = new StringTokenizer(br.readLine());
        height = Integer.parseInt(st.nextToken());
        width = Integer.parseInt(st.nextToken());

        grid = new int[height][width];
        time = new int[height][width];
        for(int row = 0; row < height; row++) {
            st = new StringTokenizer(br.readLine());
            for(int col = 0; col < width; col++) {
                grid[row][col] = Integer.parseInt(st.nextToken());
                time[row][col] = INF;
            }
        }

        flood(0, 0, 1);
        int curTime = 1;
        while(!cheese.isEmpty()) {
            int curSize = cheese.size();
            while(curSize-- > 0) {
                Point c = cheese.poll();
                int col = c.col;
                int row = c.row;
                flood(col, row, curTime);
            }
            curTime++;
        }

        int lastCheeseCount = 0;
        for(int row = 0; row < height; row++) {
            for(int col = 0; col < width; col++) {
                if(time[row][col] == curTime - 1 && grid[row][col] == 1) {
                    lastCheeseCount++;
                }
            }
        }

        sb.append(curTime - 1).append("\n");
        sb.append(lastCheeseCount);

        System.out.println(sb);
    }

    /*
     * 입력으로 주어진 좌표에서 시작해서 공기로 채울수 있는 부분들 확인
     * 현재 입력으로 주어진 좌표는 공기이며, 큐에는 현재 좌표에서 시작해서 도달할 수 있는 모든 공기의 좌표들을 넣는다
     *
     * 큐에서 순서대로 하나씩 좌표를 빼서 인접한 좌표들을 확인
     * 	1. 인접한 좌표가 공기이면 방문 체크를 해서 큐에 넣는다(방문 체크는 time을 0으로 설정)
     * 	2. 인접한 좌표가 치즈이면, 지금으로 부터 한 시간이 지났을때 녹기 때문에 cheese 큐에 넣고 방문 체크(방문 체크는 time을 현재 시간으로 설정)
     */
    public static void flood(int sCol, int sRow, int curTime) {
        Deque<Point> queue = new ArrayDeque<>();
        queue.offer(new Point(sCol, sRow));
        time[sRow][sCol] = curTime;

        while(!queue.isEmpty()){
            Point p = queue.poll();
            int col = p.col;
            int row = p.row;

            for(int dir = 0; dir < 4; dir++) {
                int nCol = col + dCol[dir];
                int nRow = row + dRow[dir];

                if(nCol < 0 || nRow < 0 || nCol >= width || nRow >= height) {
                    continue;
                }

                if(time[nRow][nCol] == INF) {
                    if(grid[nRow][nCol] == 0) {
                        time[nRow][nCol] = 0;
                        queue.offer(new Point(nCol, nRow));
                    }else {
                        time[nRow][nCol] = curTime;
                        cheese.offer(new Point(nCol, nRow));
                    }
                }
            }
        }
    }

    public static void printTime() {
        for(int row = 0; row < height; row++) {
            for(int col = 0; col < width; col++) {
                System.out.print(time[row][col] + " ");
            }
            System.out.println();
        }
        System.out.println("----------");
    }

    public static class Point{
        int col;
        int row;

        public Point(int col, int row) {
            this.col = col;
            this.row = row;
        }

        @Override
        public String toString() {
            return String.format("[col=%d, row=%d]", col, row);
        }
    }

}
/*
5 5
0 0 0 0 0
0 1 1 1 0
0 1 0 1 0
0 1 1 1 0
0 0 0 0 0

answer:
1
8
*/
