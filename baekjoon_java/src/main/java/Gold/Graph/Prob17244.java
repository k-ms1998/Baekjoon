package Gold.Graph;

import java.io.*;
import java.util.*;

/**
 * Gold 2(아맞다우산)
 *
 * https://www.acmicpc.net/problem/17244
 *
 * Solution: Simulation + BFS + DFS + BruteForce
 *
 * 1. 시작 지점에서 각 좌표까지의 최단거리 구하기
 * 2. 각 물건에 대해서 모든 좌표까지 최단거리 구하기
 * 3. 시작지점에서 물건들을 챙길수 있는 모든 순서에 대해서 거리 구하기
 * 4. 그 중에서 가장 작은 값이 정답
 */
public class Prob17244 {

    static int n, m;
    static char[][] grid;
    static int ex, ey;
    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};
    static List<Info> stuff = new ArrayList<>();
    static int stuffCnt = 0;
    static final int INF = 100000007;

    static int[][] distFromStart;
    static int[][][] dist;

    static int answer = 0;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        int sx = 0;
        int sy = 0;
        grid = new char[m][n];
        for (int y = 0; y < m; y++) {
            String cur = br.readLine();
            for(int x = 0; x < n; x++){
                grid[y][x] = cur.charAt(x);
                if(grid[y][x] == 'S'){
                    sx = x;
                    sy = y;
                }
                if(grid[y][x] == 'E'){
                    ex = x;
                    ey = y;
                }
                if(grid[y][x] == 'X'){
                    stuff.add(new Info(x, y, 0));
                    stuffCnt++;
                }
            }
        }

        distFromStart = calculateDist(sx, sy);
        dist = new int[stuffCnt][m][n];
        for(int i = 0; i < stuffCnt; i++){
            Info info = stuff.get(i);
            dist[i] = calculateDist(info.x, info.y);
        }

        if(stuffCnt == 0){ // 챙길 물건이 없으면 바로 시작지점에서 도착지점까지 가면 됨
            answer = distFromStart[ey][ex];
        }else{
            answer = INF;
            for(int i = 0; i < stuffCnt; i++){
                Info info = stuff.get(i);
                int x = info.x;
                int y = info.y;
                findAnswer(distFromStart[y][x], (1 << i), i);
            }
        }

        System.out.println(answer);

    }

    public static void findAnswer(int cost, int visited, int idx) {
        if(visited >= (1 << stuffCnt) - 1){
            cost += dist[idx][ey][ex];
            answer = Math.min(answer, cost);
            return;
        }

        for(int i = 0; i < stuffCnt; i++){
            if((visited & (1 << i)) != (1 << i)){
                Info info = stuff.get(i);
                int x = info.x;
                int y = info.y;
                findAnswer(dist[idx][y][x] + cost, visited | (1 << i), i);
            }
        }

    }

    public static int[][] calculateDist(int sx, int sy) {
        int[][] dist = new int[m][n];
        for (int y = 0; y < m; y++) {
            for (int x = 0; x < n; x++) {
                dist[y][x] = INF;
            }
        }

        dist[sy][sx] = 0;
        Deque<Info> queue = new ArrayDeque<>();
        queue.offer(new Info(sx, sy, 0));
        while(!queue.isEmpty()){
            Info info = queue.poll();

            int x = info.x;
            int y = info.y;
            int cost = info.cost;

            for(int i = 0; i < 4; i++){
                int nx = x + dx[i];
                int ny = y + dy[i];

                if (nx < 0 || ny < 0 || nx >= n || ny >= m) {
                    continue;
                }
                if(grid[ny][nx] == '#'){
                    continue;
                }
                if(dist[ny][nx] > cost + 1){
                    dist[ny][nx] = cost + 1;
                    queue.offer(new Info(nx, ny, cost + 1));

                }
            }
        }

        return dist;
    }

    public static class Info{
        int x;
        int y;
        int cost;

        public Info(int x, int y, int cost) {
            this.x = x;
            this.y = y;
            this.cost = cost;
        }
    }
}
/*
7 6
#######
#S....#
#..####
#.....#
#.....#
#####E#
 */