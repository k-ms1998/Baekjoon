package Gold.BruteForce;

import java.io.*;
import java.util.*;

/**
 * Gold 2(Maze)
 * 
 * https://www.acmicpc.net/problem/16985
 * 
 * Solution: Simulation + BruteForce
 * 1. 각 층의 판에 대해서 0도, 90도, 180도, 270도 회전 했을때의 판 구해서 저장하기 (rotated)
 *  rotated[0][1][0][0] = 0번째 판을 90도 회전 시켰을때, (0, 0)의 값
 * 2. 각 판을 0도, 90도, 180도, 270도 돌렸을때의 조합 찾기 (DFS)
 * 3. 각 판의 위치의 조합을 찾기(0번째 판이 꼭 가장 위에 있을 필요 X) (DSF)
 * 4. 각 판의 회전 조합 찾고, 각 판의 위치의 조합을 찾아서 입구로부터 출구까지의 최단거리 찾기 (BFS)
 *  -> 이떄, 입구는 항상 (0,0,0), 출구는 (4, 4, 4) 입니다
 *      -> 어차피 각 판을 0도, 90도, 180도, 270도 회전 했을때의 모든 경우의 수들을 확인하기 때문에 입구를 그냥 (0,0,0)으로 고정 해도 됨
 */
public class Prob16985 {

    static int[][][] grid = new int[5][5][5];
    static int[][][][] rotated = new int[5][4][5][5];
    static int answer = 0;
    static final int INF = 100000000;

    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};
    static int[] dz = {-1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                st = new StringTokenizer(br.readLine());
                for (int r = 0; r < 5; r++) {
                    grid[i][j][r] = Integer.parseInt(st.nextToken());
                }
            }
        }
        for(int h = 0; h < 5; h++){
            for(int r = 0; r < 4; r++){
                rotated[h][r] = rotateBoard(h, r);
            }
        }

        answer = INF;
        dfs(0, new int[5]);

        System.out.println(answer == INF ? -1 : answer);
    }

    public static void dfs(int depth, int[] comb) {
        if(answer == 12){
            return;
        }
        if (depth >= 5) {
            orderComb(0, new int[5], 0, comb);

            return;
        }

        for (int i = 0; i < 4; i++) {
            comb[depth] = i;
            dfs(depth + 1, comb);
        }
    }

    public static void orderComb(int depth, int[] order, int visited, int[] comb) {
        if(answer == 12){
            return;
        }
        if(depth >= 5){
            int[][][] tmp = new int[5][5][5];
            for(int i = 0; i < 5; i++){
                int o = order[i];
                int r = comb[i];
                for(int y = 0; y < 5; y++){
                    for(int x = 0; x < 5; x++){
                        tmp[o][y][x] = rotated[i][r][y][x];
                    }
                }
            }

            checkAnswer(tmp);
            return;
        }

        for(int i = 0; i < 5; i++){
            if((visited & (1 << i)) == (1 << i)){
                continue;
            }
            order[depth] = i;
            orderComb(depth + 1, order, visited | (1 << i), comb);
        }
    }

    public static void checkAnswer(int[][][] board) {
        if(answer == 12){
            return;
        }
        for(int i = 0; i < 4; i++){
            if(board[0][0][0] == 0){
                continue;
            }
            int[][][] dist = new int[5][5][5];
            for (int z = 0; z < 5; z++) {
                for (int y = 0; y < 5; y++) {
                    for (int x = 0; x < 5; x++) {
                        dist[z][y][x] = INF;
                    }
                }
            }

            dist[0][0][0] = 0;
            Deque<Info> queue = new ArrayDeque<>();
            queue.offer(new Info(0, 0, 0, 0));

            while (!queue.isEmpty()) {
                Info info = queue.poll();
                int x = info.x;
                int y = info.y;
                int z = info.z;
                int cnt = info.cnt;

                if(x == 4 && y == 4 && z == 4){
                    answer = Math.min(answer, cnt);
                    break;
                }

                for(int ii = 0; ii < 4; ii++){
                    int nx = x + dx[ii];
                    int ny = y + dy[ii];

                    if (nx < 0 || ny < 0 || nx >= 5 || ny >= 5) {
                        continue;
                    }
                    if (board[z][ny][nx] == 0) {
                        continue;
                    }

                    if(dist[z][ny][nx] > cnt + 1){
                        dist[z][ny][nx] = cnt + 1;
                        queue.offer(new Info(nx, ny, z, cnt + 1));
                    }
                }

                for(int ii = 0; ii < 2; ii++){
                    int nz = z + dz[ii];

                    if (nz < 0 || nz >= 5) {
                        continue;
                    }
                    if (board[nz][y][x] == 0) {
                        continue;
                    }

                    if(dist[nz][y][x] > cnt + 1){
                        dist[z][y][x] = cnt + 1;
                        queue.offer(new Info(x, y, nz, cnt + 1));
                    }
                }
            }

        }
    }


    public static int[][] rotateBoard(int h, int r) {
        int[][] result = new int[5][5];
        int size = 5;
        if(r == 0){
            for(int y = 0; y < 5; y++){
                for(int x = 0; x < 5; x++){
                    result[y][x] = grid[h][y][x];
                }
            }
        }else if(r == 1){
            for(int y = 0; y < 5; y++){
                for(int x = 0; x < 5; x++){
                    int nx = size - 1 - y;
                    int ny = x;
                    result[ny][nx] = grid[h][y][x];
                }
            }
        }else if(r == 2){
            for(int y = 0; y < 5; y++){
                for(int x = 0; x < 5; x++){
                    int nx = size - 1 - x;
                    int ny = size - 1 - y;
                    result[ny][nx] = grid[h][y][x];
                }
            }
        }else if(r == 3){
            for(int y = 0; y < 5; y++){
                for(int x = 0; x < 5; x++){
                    int nx = y;
                    int ny = size - 1 - x;
                    result[ny][nx] = grid[h][y][x];
                }
            }
        }

        return result;
    }

    public static class Info{
        int x;
        int y;
        int z;
        int cnt;

        public Info(int x, int y, int z, int cnt) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.cnt = cnt;
        }

    }

}
