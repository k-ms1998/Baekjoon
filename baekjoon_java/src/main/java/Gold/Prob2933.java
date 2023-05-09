package Gold;

import java.io.*;
import java.util.*;

/**
 * Gold 2(미네랄)
 *
 * https://www.acmicpc.net/problem/2933
 *
 * Solution: 구현 + BFS
 * 1. 조건에 따라서 미네랄 제거
 * 2. 클러스터 만들기(인접한 노드들 확인, Flood)
 *  -> (0, r - 1)부터 확인
 *  -> 공중에 떠 있는 클러슽가 존재하면 안되기 때문에, 바닥에서 가장 가까운 클러스터 부터 확인
 *  -> 바닥에서 부터 공중에 떠 있는 클러스터를 순차적으로 확인함으로써 미네랄들의 위치가 꼬이는 것을 방지
 * 3. 공중에 떠 있는 클러스터를 찾으면, 해당 클러스터이 바닥 또는 다른 클러스터와 연결될때까지 아래로 위치 업데이트
 */
public class Prob2933 {

    static int r, c;
    static char[][] grid;

    static int[][] island;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        grid = new char[r][c];
        for(int y = 0; y < r; y++){
            String cur = br.readLine();
            for(int x = 0; x < c; x++){
                grid[y][x] = cur.charAt(x);
            }
        }

        int t = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());

        for(int i = 0; i < t; i++){
            int h = r - Integer.parseInt(st.nextToken());

            boolean updateFlag = false;
            if (i % 2 == 0) {
                int x = 0;
                for (int j = 0; j < c; j++) {
                    if (grid[h][x] == 'x') {
                        grid[h][x] = '.';
                        updateFlag = true;
                        break;
                    }
                    x++;
                }
            }else{
                int x = c - 1;
                for (int j = 0; j < c; j++) {
                    if (grid[h][x] == 'x') {
                        grid[h][x] = '.';
                        updateFlag = true;
                        break;
                    }
                    x--;
                }
            }

            if(updateFlag){
                flood();
            }
        }

        StringBuilder answer = new StringBuilder();
        for (int i = 0; i < r; i++) {
            answer.append(grid[i]).append("\n");
        }
        System.out.print(answer);

    }

    public static void flood(){
        boolean[][] visited = new boolean[r][c];
        island = new int[r][c];

        int num = 0;
        for(int y = r - 1; y >= 0; y--){
            for(int x = 0; x < c; x++){
                if(grid[y][x] == 'x' && !visited[y][x]){
                    num++;
                    Deque<Point> queue = new ArrayDeque<>();
                    List<Point> list = new ArrayList<>(); // 현재 클러스터에 속해있는 미네랄들 위치 저장

                    visited[y][x] = true;
                    island[y][x] = num;
                    list.add(new Point(x, y));
                    queue.offer(new Point(x, y));

                    while(!queue.isEmpty()){
                        Point p = queue.poll();
                        int px = p.x;
                        int py = p.y;

                        for(int i = 0; i < 4; i++){
                            int nx = px + dx[i];
                            int ny = py + dy[i];

                            if(nx < 0 || ny < 0 || nx >= c || ny >= r){
                                continue;
                            }

                            if(!visited[ny][nx] && grid[ny][nx] == 'x'){
                                visited[ny][nx] = true;
                                island[ny][nx] = num;
                                queue.offer(new Point(nx, ny));
                                list.add(new Point(nx, ny));
                            }
                        }
                    }
                    int diff = 0;
                    while (checkList(list, num, diff)) {
                        for(Point p : list){ // 클러스터에 속한 미내럴들의 초기 위치는 빈칸(.)이 되도록 업데이트
                            int px = p.x;
                            int py = p.y + diff;
                            grid[py][px] = '.';
                        }
                        updateGrid(list, num, diff); // diff = 현재까지 아래로 움직인 횟수
                        diff++;
                    }

                    if(diff > 0){

                        return;
                    }
                }
            }
        }
    }

    public static void updateGrid(List<Point> list, int num, int diff){
        for(Point p : list){
            int x = p.x;
            int y = p.y;

            int ny = y + 1 + diff;

            grid[ny][x] = 'x';
            island[ny][x] = num;
        }
    }

    public static boolean checkList(List<Point> list, int num, int diff){
        for(Point p : list){
            int x = p.x;
            int y = p.y;

            int ny = y + 1 + diff;
            if(y + diff >= r - 1){
                return false;
            }
            if(grid[ny][x] == 'x'){
                if (island[ny][x] != num && island[ny][x] != 0) {
                    return false;
                }
            }
        }

        return true;
    }

    public static void printGrid(){
        for(int y = 0; y < r; y++){
            for(int x = 0; x < c; x++){
                System.out.print(island[y][x]);
            }
            System.out.println();
        }
        System.out.println("----------");
    }

    public static class Point{
        int x;
        int y;

        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString(){
            return "{x=" + x + ", y=" + y + "}";
        }
    }
}
/*
5 5
xxxxx
x....
xxxxx
x....
x....
10
1 2 3 4 5 1 2 3 4 5
=>
.....
.....
.....
.xxxx
xxxx.

10 10
xxxxxxxxxx
....x.....
...xxx....
.....x....
....xx....
.....x....
xxxxxx....
..x.......
.xxxx.....
...xxxxxxx
10
9 8 7 6 5 4 3 2 1 1
=>
..........
..........
..........
..........
..........
..........
xxxxxxxxxx
....xx....
xxxxxx....
.xxxxxxxx.

3 3
...
..x
x.x
2
1 1
=>
...
...
..x

4 4
...x
..xx
xx.x
x..x
2
1 1
=>
....
...x
..xx
xx.x

4 4
...x
..xx
.xxx
xxxx
10
1 2 3 4 1 2 3 4 3 4
=>
....
....
.xxx
..xx
 */