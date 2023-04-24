package Gold.BruteForce;

import java.io.*;
import java.util.*;

/**
 * Gold 3(게리맨더링 2)
 *
 * https://www.acmicpc.net/problem/17779
 * 
 * Solution: BruteForce
 * -> 모든 기준점 (x, y)와 모든 d1, d2의 조합들에 대해서 값을 구해야함
 *
 * 1. 5구역 지정하기
 * 2. 그 외 구역들을 문제의 조간에 따라서 지정하기
 * 3. 구역을 나눴을때, 각 구역의 인구수 구해서, 최대와 최솟값의 차이 중 가장 작은 값 출력
 */
public class Prob17779 {

    static int n;
    static int[][] grid;
    static int total = 0;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());

        grid = new int[n + 1][n + 1];
        for(int x = 1; x < n + 1; x++){
            st = new StringTokenizer(br.readLine());
            for(int y = 1; y < n + 1; y++){
                grid[x][y] = Integer.parseInt(st.nextToken());
                total += grid[x][y]; // 전체 인구수
            }
        }

        int answer = Integer.MAX_VALUE;
        for(int x = 1; x < n + 1; x ++){
            for(int y = 1; y < n + 1; y++){
                for(int d1 = 1; d1 < n + 1; d1++){
                    for (int d2 = 1; d2 < n + 1; d2++) {
                        if(checkParams(x, y, d1, d2)){
                            int[][] area = new int[n + 1][n + 1];
                            int[] population = new int[6];
                            updateArea(area, x, y, d1, d2); // 5구역 지정하기
                            updateBorder(area, x, y, d1, d2, population); // 1~4구역 지정하기

                            population[5] = total - (population[1] + population[2] + population[3] + population[4]);
                            int maxP = 0;
                            int minP = Integer.MAX_VALUE;
                            for(int i = 1; i <= 5; i++){
                                maxP = Math.max(maxP, population[i]);
                                minP = Math.min(minP, population[i]);
                            }
                            answer = Math.min(answer, maxP - minP);
                        }
                    }
                }
            }
        }

        System.out.println(answer);
    }

    /**
     * 5구역 지정하기
     * 1. 경계 지정하기
     * 2. 경계 안에도 5구역으로 지정하기 위해서, 안쪽의 좌표들을 BFS로 찾기
     *  -> ex:         5 (1, 3)
     *              5  0  5
     *           5  0  0  0  5
     *              5  0  5
     *                 5
     *                 => 이렇게 되어 있을때, 큐에 (2, 3), (3, 2), (3, 4)를 추가하고, 각 지점으로 부터 BFS 로 값이 0인 인접노드들을 5로 변경해서 5구역 지정
     */
    public static void updateArea(int[][] area, int x, int y, int d1, int d2) {

        Deque<Point> queue = new ArrayDeque<>();
        for(int i = 0; i <= d1; i++){
            area[x+i][y-i] = 5;
            if(i != d1){
                queue.offer(new Point(x + i + 1, y - i));
            }
        }
        for(int i = 0; i <= d2; i++){
            area[x+i][y+i] = 5;
            if(i != d2){
                queue.offer(new Point(x + i + 1, y + i));
            }
        }
        for(int i = 0; i <= d2; i++){
            area[x+d1+i][y-d1+i] = 5;
        }
        for(int i = 0; i <= d1; i++){
            area[x+d2+i][y+d2-i] = 5;
        }

        while(!queue.isEmpty()){
            Point p = queue.poll();
            int px = p.x;
            int py = p.y;
            if (area[px][py] == 5) {
                continue;
            }

            area[px][py] = 5;
            for(int i = 0; i < 4; i++){
                int nx = px + dx[i];
                int ny = py + dy[i];

                if (nx <= 0 || ny <= 0 || nx > n || ny > n) {
                    continue;
                }

                if (area[nx][ny] != 5) {
                    queue.offer(new Point(nx, ny));
                }
            }
        }
    }

    public static void updateBorder(int[][] area, int x, int y, int d1, int d2, int[] population) {
        for(int r = 1; r < n + 1; r++){
            for(int c = 1; c < n + 1; c++){
                if(area[r][c] == 5){ // 5구역이면 건너뛰기
                    continue;
                }
                if(1 <= r && r < x + d1 && 1 <= c && c <= y){ // 1구역 경계
                    area[r][c] = 1;
                    population[1] += grid[r][c];
                }
                if(1 <= r && r <= x + d2 && y < c && c <= n){ // 2구역 경계
                    area[r][c] = 2;
                    population[2] += grid[r][c];
                }
                if(x + d1 <= r && r <= n && 1 <= c && c < y - d1 + d2){ // 3구역 경계
                    area[r][c] = 3;
                    population[3] += grid[r][c];
                }
                if(x + d2 < r && r <= n && y - d1 +d2 <= c && c <= n){ // 4구역 경계
                    area[r][c] = 4;
                    population[4] += grid[r][c];
                }
            }
        }
    }

    public static boolean checkParams(int x, int y, int d1, int d2) {
        if(1 <= x && x < x + d1 + d2 && x + d1 + d2 <= n){
            if(1 <= y - d1 && y - d1 < y && y < y + d2 && y + d2 <= n){
                return true;
            }
        }

        return false;
    }

    public static class Point{
        int x;
        int y;

        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
}
