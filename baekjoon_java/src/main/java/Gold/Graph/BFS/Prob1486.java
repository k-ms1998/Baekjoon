package Gold.Graph.BFS;

import java.io.*;
import java.util.*;

public class Prob1486 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int n, m, t, d;
    static int[][] mountainMap;
    static int[][] totalDist;
    static int[][] totalDistR;
    static final int INF = 100_000_000;

    static int[] dRow = {1, 0, -1, 0};
    static int[] dCol = {0, 1, 0, -1};

    public static void main(String args[]) throws IOException{
        st = new StringTokenizer(br.readLine().trim());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        t = Integer.parseInt(st.nextToken());
        d = Integer.parseInt(st.nextToken());
        mountainMap = new int[n][m];
        totalDist = new int[n][m];
        totalDistR = new int[n][m];

        for(int row = 0; row < n; row++){
            String curRow = br.readLine();
            for(int col = 0; col < m; col++){
                char c = curRow.charAt(col);
                if(c >= 'a' && c <= 'z'){
                    mountainMap[row][col] = c- 'a' + 26;
                }else{
                    mountainMap[row][col] = c- 'A';
                }
                totalDist[row][col] = INF;
                totalDistR[row][col] = INF;
            }
        }

        Deque<Point> queue = new ArrayDeque<>();
        queue.offer(new Point(0, 0, 0));
        totalDist[0][0] = 0;

        while(!queue.isEmpty()){
            Point p = queue.poll();
            int row = p.row;
            int col = p.col;
            int dist = p.dist;


            for(int dir = 0; dir < 4; dir++){
                int nRow = row + dRow[dir];
                int nCol = col + dCol[dir];

                if(nCol < 0 || nRow < 0 || nCol >= m || nRow >= n){
                    continue;
                }

                int diff = Math.abs(mountainMap[row][col] - mountainMap[nRow][nCol]);
                if(diff > t){
                    continue;
                }
                int toNextDist = 0;
                if(mountainMap[row][col] >= mountainMap[nRow][nCol]){
                    toNextDist = 1;
                }else{
                    toNextDist = diff * diff;
                }

                int nextDist = dist + toNextDist;
                if(totalDist[nRow][nCol] > nextDist){
                    totalDist[nRow][nCol] = nextDist;
                    queue.offer(new Point(nRow, nCol, nextDist));
                }
            }
        }

        Deque<Point> queueR = new ArrayDeque<>();
        queueR.offer(new Point(0, 0, 0));
        totalDistR[0][0] = 0;

        while(!queueR.isEmpty()){
            Point p = queueR.poll();
            int row = p.row;
            int col = p.col;
            int dist = p.dist;


            for(int dir = 0; dir < 4; dir++){
                int nRow = row + dRow[dir];
                int nCol = col + dCol[dir];

                if(nCol < 0 || nRow < 0 || nCol >= m || nRow >= n){
                    continue;
                }

                int diff = Math.abs(mountainMap[row][col] - mountainMap[nRow][nCol]);
                if(diff > t){
                    continue;
                }
                int toNextDist = 0;
                if(mountainMap[row][col] <= mountainMap[nRow][nCol]){
                    toNextDist = 1;
                }else{
                    toNextDist = diff * diff;
                }

                int nextDist = dist + toNextDist;
                if(totalDistR[nRow][nCol] > nextDist){
                    totalDistR[nRow][nCol] = nextDist;
                    queueR.offer(new Point(nRow, nCol, nextDist));
                }
            }
        }

        int answer = mountainMap[0][0];
        for(int row = 0; row < n; row++){
            for(int col = 0; col < m; col++){
                // System.out.print("{" + totalDist[row][col] + "," + totalDistR[row][col] + "}");
                if((totalDist[row][col] + totalDistR[row][col]) <= d){
                    answer = Math.max(answer, mountainMap[row][col]);
                }
            }
            // System.out.println();
        }
        System.out.println(answer);
    }

    public static class Point{
        int row;
        int col;
        int dist;

        public Point(int row, int col, int dist){
            this.row = row;
            this.col = col;
            this.dist = dist;
        }
    }

}
/*
7 4 5 1
BCDE
AJKF
AIHG
AAAA
AOMK
AQSI
ACEG
*/