package Gold.Graph;

import java.io.*;
import java.util.*;

/**
 * Gold 2(2048(Easy))
 *
 * https://www.acmicpc.net/problem/12100
 *
 * Solution: DFS + BackTracking
 */
public class Prob12100 {

    static int n;
    static long[][] board;

    /**
     * 상,하,좌,우
     */
    static int[] dirX = {0, 0, 1, -1};
    static int[] dirY = {1, -1, 0, 0};
    static long ans = 0L;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        board = new long[n][n];
        for(int y = 0; y < n; y++){
            st = new StringTokenizer(br.readLine());
            for(int x = 0; x < n; x++){
                board[y][x] = Integer.parseInt(st.nextToken());
            }
        }
        dfs(1);

        System.out.println(ans);
    }

    public static void dfs(int depth){
        if(depth > 5){
            ans = Math.max(ans, findMax());
            return;
        }

        /**
         * 현재 상태의 게임판 복사하기
         */
        long[][] tmpBoard = new long[n][n];
        for(int y = 0; y < n; y++){
            for(int x = 0; x < n; x++){
                tmpBoard[y][x] = board[y][x];
            }
        }

        for (int i = 0; i < 4; i++) {
            /**
             * 게임판 움직이기
             */
            moveBlocks(i);
            dfs(depth + 1);
            /**
             * BackTracking: 게임 판을 직전 상태로 복구
             */
            resetBoard(tmpBoard);
        }

    }

    public static void moveBlocks(int direction){
        int dx = dirX[direction];
        int dy = dirY[direction];

        /**
         * 상,하 또는 좌,우 에 따라 판별
         */
        if (direction == 0 || direction == 1) {
            for (int x = 0; x < n; x++) {
                int y = direction == 0 ? 0 : n - 1;
                while (y >= 0 && y < n) {
                    if (board[y][x] != 0) {
                        findSame(x, y, dx, dy, direction);
                    }
                    y += dy;
                }
                updateBoard(x, direction == 0 ? 0 : n - 1, dx, dy, direction);
            }
        }else{
            for (int y = 0; y < n; y++) {
                int x = direction == 2 ? 0 : n - 1;
                while(x >= 0 && x < n){
                    if(board[y][x] != 0){
                        findSame(x, y, dx, dy, direction);
                    }

                    x += dx;
                }
                updateBoard(direction == 2 ? 0 : n - 1, y, dx, dy, direction);
            }
        }
    }

    /**
     * (x, y)의 값과 같은 값이 같은 줄에 있는지 확인하기
     * ex: 판을 위로 움직이고 현재 (0,0)일때, (0,1)~(0,n-1) 중에서 (0,0)과 같은 값이 있는지 확인(이때, (0,0)의 값은 0이면 안됨)
     */
    public static void findSame(int x, int y, int dx, int dy, int direction) {
        if (direction == 0 || direction == 1) {
            int yy = y + dy;
            while (yy >= 0 && yy < n) {
                /**
                 * (0,1)~(0,n-1) 중 0이 아닌 값이 가장 먼저 나올때 멈춤
                 * (0,0)과 같으면 값들을 업데이트
                 * 이떄, 0이 아닌 값이 처음으로 나올때 멈추는 이유는, 만약에 멈추지 않으면 해당 값을 건너뛰고 같은 값을 찾는 것이기 때문에 오류
                 * ex: 1 2 0 1 이고, 왼쪽으로 옮기면 1 2 1 0 이 되어야한다.
                 *      만약에, 0이 아닌 값이 처음으로 나올때 break 를 해주지 않으면 2 2 0 0 이 되어버림
                 */
                if(board[yy][x] != 0){
                    if (board[y][x] == board[yy][x]) {
                        board[y][x] *= 2;
                        board[yy][x] = 0;
                    }
                    break;
                }
                yy += dy;
            }
            return;
        } else {
            int xx = x + dx;
            while (xx >= 0 && xx < n) {
                if(board[y][xx] != 0){
                    if(board[y][x] == board[y][xx]){
                        board[y][x] *= 2;
                        board[y][xx] = 0;
                    }
                    break;
                }
                xx += dx;
            }
            return;
        }
    }

    /**
     * 방향에 따라서 한쪽으로 숫자들을 다 몰아주기
     * ex: 0 1 0 2 를 왼쪽으로 다 몰아주면 -> 1 2 0 0 이 된다
     */
    public static void updateBoard(int x, int y, int dx, int dy, int dir) {
        if(dir == 0 || dir == 1){
            int yy = y;
            while (yy >= 0 && yy < n) {
                /**
                 * 현재 값이 0 이면, 가장 첨으로 나온 0이 아닌 숫자로 대체해야됨
                 * ex: 0 0 1 이거, 현재 (0,0) 이면, (0,2) 에 있는 1을 (0,0)으로 옮겨야함
                 */
                if(board[yy][x] == 0){
                    int curY = yy;
                    while (board[yy][x] == 0) {
                        yy += dy;
                        if (yy < 0 || yy >= n) {
                            break;
                        }
                    }
                    /**
                     * ex: 0 0 1 2 일때
                     * -> 1 0 0 2 가 되고 나서, 다음으로 (1, 0)에 (3, 0)의 값인 2가 들어와야됨
                     *  => yy 의 값을 업데이트
                     */
                    if (yy >= 0 && yy < n) {
                        board[curY][x] = board[yy][x];
                        board[yy][x] = 0;
                        yy = curY;
                    }
                }
                yy += dy;
            }
        }else {
            int xx = x;
            while(xx >= 0 && xx < n){
                if (board[y][xx] == 0) {
                    int curX = xx;
                    while (board[y][xx] == 0) {
                        xx += dx;
                        if (xx < 0 || xx >= n) {
                            break;
                        }
                    }
                    if (xx >= 0 && xx < n) {
                        board[y][curX] = board[y][xx];
                        board[y][xx] = 0;
                        xx = curX;
                    }
                }
                xx += dx;
            }
        }
    }

    public static long findMax(){
        long curMax = 0;

        for(int y = 0; y < n; y++){
            for(int x = 0; x < n; x++){
                curMax = Math.max(curMax, board[y][x]);
            }
        }

        return curMax;
    }

    public static void resetBoard(long[][] tmpBoard){
        for(int y = 0; y < n; y++){
            for(int x = 0; x < n; x++){
                board[y][x] = tmpBoard[y][x];
            }
        }
    }
}
/*
3
8 4 2
8 4 2
8 4 2

3
256 256 128
32 16 128
128 128 128

4
2 0 2 8
0 0 2 2
0 0 0 0
0 0 0 0

4
2 0 2 0
0 0 2 0
0 0 0 0
0 2 0 0

5
2 2 4 8 16
0 0 0 0 0
0 0 0 0 0
0 0 0 0 0
2 2 4 8 16

5
2 0 0 0 0
2 0 0 0 0
4 0 0 0 0
2 0 0 0 0
2 0 0 0 0
 */
