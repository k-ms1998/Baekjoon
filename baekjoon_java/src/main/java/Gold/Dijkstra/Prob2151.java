package Gold.Dijkstra;

import java.io.*;
import java.util.*;

/**
 * Gold 3(거울 설치)
 *
 * https://www.acmicpc.net/problem/2151
 *
 * Solution: 다익스트라
 * 1. 시작 지점에서 네 방향으로 빛 쏘기
 * 2. 빛이 나가고 있는 각 방향과 위치에 대해서 사용된 최소의 거울 개수를 저장
 *  -> count[0][y][x] = 빛이 아래 방향으로 가고 있을때, (x, y)까지 가는데 필요한 최소의 거울의 수
 * 3. 현재 진행하고 있는 방향에 대해서 다음 좌표를 방문할 때, 빈 공간이면 방향을 그대로 해서 나아가고, 거울을 설치할 수 있으면 다음 3가지 확인:
 *  1. 거울을 설치하지 않고 지금 진행하고 있는 방향 그대로 갔을때 => 이 조건을 빼먹으면 안됨!!!!
 *  2. 거울을 설치해서 빛의 방향을 반시계 방향으로 바꿀때
 *  3.거울을 설치해서 빛의 방향을 시계 방향으로 바꿀때
 * 4. 도착 지점에 도달 했을때 사용된 최소의 거울의 수 확인
 */
public class Prob2151 {

    static int n;
    static char[][] grid;
    static List<Point> doors = new ArrayList<>();
    static int[][][] count;
    static final int INF = 100000000;

    /*
    왼쪽이나 오른쪽에서 빛이 와서 다른 거울과 만날때: 위쪽, 아래족 방향으로 바꿀수 있음
    위쪽이나 아래쪽에서 빛이 와서 다른 거울과 만날때: 왼쪽, 오른쪽 방향으로 바꿀수 있음
     */
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};


    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(br.readLine());
        grid = new char[n][n];
        count = new int[4][n][n];

        for(int y = 0; y < n; y++){
            String row = br.readLine();
            for(int x = 0; x < n; x++){
                grid[y][x] = row.charAt(x);
                if(grid[y][x] == '#'){
                    doors.add(new Point(x, y, 0, -1));
                }
                count[0][y][x] = INF;
                count[1][y][x] = INF;
                count[2][y][x] = INF;
                count[3][y][x] = INF;

            }
        }

        Point start = doors.get(0);
        Point end = doors.get(1);

        Deque<Point> queue = new ArrayDeque<>();
        queue.offer(new Point(start.x, start.y, 0, 0));
        queue.offer(new Point(start.x, start.y, 0, 1));
        queue.offer(new Point(start.x, start.y, 0, 2));
        queue.offer(new Point(start.x, start.y, 0, 3));

        int answer = INF;
        count[0][start.y][start.x] = 0;
        count[1][start.y][start.x] = 0;
        count[2][start.y][start.x] = 0;
        count[3][start.y][start.x] = 0;
        while (!queue.isEmpty()) {
            Point cur = queue.poll();
            int x = cur.x;
            int y = cur.y;
            int c = cur.c;
            int d = cur.d;

            int nx = x + dx[d];
            int ny = y + dy[d];

            if(nx < 0 || ny < 0 || nx >= n || ny >= n){
                continue;
            }

            if(grid[ny][nx] == '*'){
                continue;
            }else if(grid[ny][nx] == '!'){
                int d1 = (d + 1) % 4;
                int d2 = (d + 3) % 4;

                if(count[d][ny][nx] > c){ // !! 거울을 놓을 수 있는 위치더라도,놓지 않을 수도 있음 !!
                    count[d][ny][nx] = c;
                    queue.offer(new Point(nx, ny, c, d));
                }
                if(count[d1][ny][nx] > c + 1){
                    count[d1][ny][nx] = c + 1;
                    queue.offer(new Point(nx, ny, c + 1, d1));
                }
                if(count[d2][ny][nx] > c + 1){
                    count[d2][ny][nx] = c + 1;
                    queue.offer(new Point(nx, ny, c + 1, d2));
                }
            }else if(grid[ny][nx] == '.'){
                if(count[d][ny][nx] > c){
                    count[d][ny][nx] = c;
                    queue.offer(new Point(nx, ny, c, d));
                }
            }else{
                if(nx == end.x && ny == end.y){
                    answer = Math.min(answer, c);
                }
            }
        }
        
        System.out.println(answer);
    }

    public static class Point{
        int x;
        int y;
        int c;
        int d;

        public Point(int x, int y, int c, int d){
            this.x = x;
            this.y = y;
            this.c = c;
            this.d = d;
        }

        @Override
        public String toString(){
            return "{x=" + x + ", y=" + y + ", c=" + c + ", d=" + d + "}";
        }
    }

}
