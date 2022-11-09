package Gold.Graph;

import java.io.*;
import java.util.*;

/**
 * Gold 4(빙산)
 *
 * https://www.acmicpc.net/problem/2573
 *
 * Solution: DFS
 * 연결되어 있는 노드(빙산)들을 BFS 가 아닌 DFS 로 풀어보기 좋은 문제 (BFS 로 해결시 메모리 초과 발생)
 */
public class Prob2573 {

    static int n, m;
    static int[][] grid;
    static int[][] grouped;

    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};

    static int time = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        grid = new int[n][m];
        grouped = new int[n][m];
        for (int y = 0; y < n; y++) {
            st = new StringTokenizer(br.readLine());
            for (int x = 0; x < m; x++) {
                grid[y][x] = Integer.parseInt(st.nextToken());
            }
        }

        while(true){
            int numOfGroups = groupIceberg();
            if (numOfGroups >= 2) {
                break;
            }
            if (numOfGroups == 0){
                time = 0;
                break;
            }
            meltIceberg();
            time++;
        }

        System.out.println(time);
    }

    public static void meltIceberg() {
        for (int y = 0; y < n; y++) {
            for(int x = 0; x < m; x++){
                if (grid[y][x] > 0) {
                    for (int i = 0; i < 4; i++) {
                        int nx = x + dx[i];
                        int ny = y + dy[i];

                        if (nx < 0 || nx >= m || ny < 0 || ny >= n) {
                            continue;
                        }

                        /**
                         * 빙산을 녹일때, 현재 빙산과 인접한 빙산이 녹아서, 현재 빙산을 녹이는데에 영향을 미칠수 있음
                         * ex: 1 2 0 이렇게 되어 있을때, 0 1 0 이 되어야 하는데, 1먼저 녹이면, 2가 바다와 인접한 면이 2개가 되는 것 처럼 보일 수도 있기 때문에 0 0 0이 될 수도 있음
                         * 그러므러, grid 의 값을 보지 않고, grouped 를 확인
                         * grouped 가 0이면, 빙산을 녹이기 전에 바다임을 나타냄
                         * 그러므로, 빙산이 녹아서 바다가 되더라도, 다른 빙산들에게 영향 X
                         */
                        if (grouped[ny][nx] <= 0) {
                            grid[y][x]--;
                            if (grid[y][x] <= 0) {
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 연결되어 있는 빙산끼리 찾기 위해서 보통 BFS 로 풀면되지만, 그렇게 할 경우 해당 문제에서는 메모리 초과 발생
     * 그러므로, DFS 로 풀이
     */
    public static int groupIceberg() {
        int num = 0;
        grouped = new int[n][m];
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < m; x++) {
                if (grid[y][x] > 0) {
                    if (grouped[y][x] == 0) {
                        ++num;
                        /**
                         * 어차피 num >= 2 이면, 현재 빙산이 2개 이상의 덩어리로 분리되어 있기 때문에 더 이상 탐색할 필요 X
                         */
                        if (num >= 2) {
                            return num;
                        }
                        numberIceberg(x, y, num);
                    }
                }
            }
        }

        return num;
    }

    public static void numberIceberg(int x, int y, int num) {
        grouped[y][x] = num;
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if (nx < 0 || nx >= m || ny < 0 || ny >= n) {
                continue;
            }

            if (grouped[ny][nx] > 0 || grid[ny][nx] <= 0) {
                continue;
            }
            numberIceberg(nx, ny, num);
        }
    }

    private static void printGrid(int[][] grouped) {
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < m; x++) {
                System.out.print(grouped[y][x] + " ");
            }
            System.out.println();
        }
        System.out.println("---------- ----------");
    }

    public static class Pos{

        int x;
        int y;

        public Pos(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
}
