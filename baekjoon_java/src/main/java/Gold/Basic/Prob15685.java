package Gold.Basic;

import java.io.*;
import java.util.*;

/**
 *
 * Gold 4(드래곤 커브)
 * 
 * https://www.acmicpc.net/problem/15685
 * 
 * Solution: 구현
 * 1. 각 세대마다 바뀌는 방향을 계산 후, 현재까지 움직인 경로에 추가된 역순으로 추가
 * 2. 해당 경로를 처음부터 순서대로 따라가면서 방문한 좌표는 grid에 업데이트
 * 3. grid를 처음부터 끝까지 보면서, (x, y) & (x + 1, y) & (x, y + 1) & (x + 1, y + 1) 에 모두 방문 했으면 네 꼭짓점이 드래곤 커브의 일부
 */
public class Prob15685 {

    static int n;
    static boolean[][] grid = new boolean[102][102];
    static int[] dx = {1, 0, -1, 0}; // 0 = right, 1 = up, 2 = left, 3 = down
    static int[] dy = {0, -1, 0, 1};
    static int maxX = 0;
    static int maxY = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        for(int i = 0; i < n; i++){
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            int g = Integer.parseInt(st.nextToken());

            grid[y][x] = true;
            List<Integer> dir = new ArrayList<>();
            dir.add(d);
            drawDragonCurve(0, x, y, g, dir);
        }


        int answer = 0;
        for(int y = 0; y < maxY + 1; y++){
            for(int x = 0; x < maxX + 1; x++){
                if(grid[y][x] && grid[y + 1][x] && grid[y][x + 1] && grid[y + 1][x + 1]){
                    answer++;
                }
            }
        }
        System.out.println(answer);
    }

    public static void drawDragonCurve(int depth, int x, int y, int g, List<Integer> dir) {
        if(depth == g){
            for(int d : dir){
                x += dx[d];
                y += dy[d];
                grid[y][x] = true;

                maxX = Math.max(maxX, x);
                maxY = Math.max(maxY, y);
            }

            return;
        }

        Deque<Integer> next = new ArrayDeque<>();
        for(int d : dir){
            next.offer((d + 1) % 4);
        }
        while(!next.isEmpty()){
            dir.add(next.pollLast());
        }

        drawDragonCurve(depth + 1, x, y, g, dir);
    }


}
