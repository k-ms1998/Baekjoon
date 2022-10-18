package Gold.Graph;

import java.util.*;
import java.io.*;

/**
 * Gold 1(구슬 탈출 2)
 *
 * https://www.acmicpc.net/problem/13460
 *
 * Solution: BFS
 * 핵심:
 * 1. 장남감을 기우는 방향의 순서
 * 2. 두 구슬의 위치를 통해 방문한 경우의 수인지 아닌지 저장하는 것
 * 3. 두 구슬을 옮겼을때 위치가 겹치지 않는 것
 */
public class Prob13460 {

    static int h;
    static int w;

    static Pos red;
    static Pos blue;
    static Pos hole;
    static char[][] grid;
    static boolean[][][][] visited;

    /**
     * 상 -> 우 -> 하 -> 좌로 기울도록 하는 순서 중요 !!
     * 다른 순서로 할 시, 오답 발생
     */
    static int[] dy = {-1, 0, 1, 0};
    static int[] dx = {0, 1, 0, -1};

    static int ans = -1;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        h = Integer.parseInt(st.nextToken());
        w = Integer.parseInt(st.nextToken());

        grid = new char[h][w];
        visited = new boolean[w][h][w][h];
        for(int y = 0; y < h; y++){
            String curRow = br.readLine();
            for(int x = 0; x < w; x++){
                grid[y][x] = curRow.charAt(x);
                if(grid[y][x] == 'R'){
                    red = new Pos(x, y, 0);
                    grid[y][x] = '.';
                }else if(grid[y][x] == 'B'){
                    blue = new Pos(x, y, 0);
                    grid[y][x] = '.';
                }else if(grid[y][x] == 'O'){
                    hole = new Pos(x, y, 0);
                }
            }
        }

        bfs();
         if(ans > 10){
             ans = -1;
         }
        System.out.println(ans);
    }

    public static void bfs(){
        Deque<Ball> queue = new ArrayDeque<>();

        queue.offer(new Ball(red.x, red.y, blue.x, blue.y, 1));
        visited[red.x][red.y][blue.x][blue.y] = true;
        while(!queue.isEmpty()){

            Ball curMarble = queue.poll();

            int rx = curMarble.rx;
            int ry = curMarble.ry;
            int bx = curMarble.bx;
            int by = curMarble.by;
            int curCnt = curMarble.cnt;

            for(int i = 0; i < 4; i++){
                /**
                 * 벽 또는 구멍을 찾을때까지 빨간 구슬과 파란 구슬을 옮긴다
                 */
                Pos nextRed = moveBall(i, rx, ry, curCnt);
                Pos nextBlue = moveBall(i, bx, by, curCnt);

                if(nextBlue.x == hole.x && nextBlue.y == hole.y){
                    continue;
                }else if(nextRed.x == hole.x && nextRed.y == hole.y){
                    ans = ans == -1 ? curCnt : Math.min(ans, curCnt);
                }else{
                    /**
                     * 만약에 옮겼을때 둘의 위치가 같다면 둘중 하나의 위치를 조정
                     * Because, 두 구슬이 겹칠수는 없기 때문에
                     * 
                     * 위치를 조정하는 구슬은, 옮긴 방형 기준 뒤에 있던 구슬
                     * ex: 빨간 구슬이 (1,0), 파랑 구슬이 (2,0)에 있고, 왼쪽으로 옮기면 일단 둘다 (0,0)이 됨
                     *      하지만, 왼쪽 기준 파랑 구슬이 빨강 구슬 뒬에 있기 때문에 파랑 구슬을 (1,0) 으로 조정
                     */
                    if(nextRed.x == nextBlue.x && nextRed.y == nextBlue.y){
                        boolean isRedFirst = redFirst(i, new Pos(rx, ry), new Pos(bx, by));
                        if(isRedFirst){
                            nextBlue.x -= dx[i];
                            nextBlue.y -= dy[i];
                        }else{
                            nextRed.x -= dx[i];
                            nextRed.y -= dy[i];
                        }
                    }

                    /**
                     * 두 구슬의 위치를 기준으로 방문한 경우의 수인지 아닌지 확인 및 저장
                     */
                    if(!visited[nextRed.x][nextRed.y][nextBlue.x][nextBlue.y]){
                        visited[nextRed.x][nextRed.y][nextBlue.x][nextBlue.y] = true;
                        queue.offer(new Ball(nextRed.x,nextRed.y,nextBlue.x,nextBlue.y, curCnt + 1));
                    }
                }

            }
        }
    }

    public static boolean redFirst(int dir, Pos curRed, Pos curBlue){
        int rx = curRed.x;
        int ry = curRed.y;
        int bx = curBlue.x;
        int by = curBlue.y;

        if(dir == 0){
            // 상
            if(ry < by){
                return true;
            }else{
                return false;
            }
        }else if(dir == 1){
            // 우
            if(rx > bx){
                return true;
            }else{
                return false;
            }
        }else if(dir == 2){
            // 하
            if(ry > by){
                return true;
            }else{
                return false;
            }
        }else{
            // 좌
            if(rx < bx){
                return true;
            }else{
                return false;
            }
        }
    }

    public static Pos moveBall(int dir, int sx, int sy, int sCnt){
        while(grid[sy + dy[dir]][sx + dx[dir]] != '#'){
            sx += dx[dir];
            sy += dy[dir];

            if(grid[sy][sx] == 'O'){
                break;
            }
        }

        return new Pos(sx, sy);
    }

    public static class Pos{
        int x;
        int y;
        int cnt;

        public Pos(int x, int y){
            this.x = x;
            this.y = y;
        }

        public Pos(int x, int y, int cnt){
            this.x = x;
            this.y = y;
            this.cnt = cnt;
        }

        @Override
        public String toString(){
            return "Pos=[x:"+ x + ", y:"+ y +"]";
        }
    }

    public static class Ball{
        int rx;
        int ry;
        int bx;
        int by;
        int cnt;

        public Ball(int rx, int ry, int bx, int by, int cnt){
            this.rx = rx;
            this.ry = ry;
            this.bx = bx;
            this.by = by;
            this.cnt = cnt;
        }
    }
}