package Gold.Basic;

import java.io.*;
import java.util.*;

/**
 * Gold 2(모노미노도미노 2)
 *
 * https://www.acmicpc.net/problem/20061
 *
 * Solution: 구현(Simulation)
 */
public class Prob20061 {

    static int[] dx = {1, 0};
    static int[] dy = {0, 1};
    static int[][] board = {
            {1, 1, 1, 1, 2, 2, 2, 2, 2, 2},
            {1, 1, 1, 1, 2, 2, 2, 2, 2, 2},
            {1, 1, 1, 1, 2, 2, 2, 2, 2, 2},
            {1, 1, 1, 1, 2, 2, 2, 2, 2, 2},
            {3, 3, 3, 3, -1, -1, -1, -1, -1, -1},
            {3, 3, 3, 3, -1, -1, -1, -1, -1, -1},
            {3, 3, 3, 3, -1, -1, -1, -1, -1, -1},
            {3, 3, 3, 3, -1, -1, -1, -1, -1, -1},
            {3, 3, 3, 3, -1, -1, -1, -1, -1, -1},
            {3, 3, 3, 3, -1, -1, -1, -1, -1, -1}
    };

    static int score = 0;
    static int cnt = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;


        int n = Integer.parseInt(br.readLine());
        for(int i = 0; i < n; i++){
            st = new StringTokenizer(br.readLine());
            int t = Integer.parseInt(st.nextToken());
            int x1 = Integer.parseInt(st.nextToken());
            int y1 = Integer.parseInt(st.nextToken());

            int x2 = x1;
            int y2 = y1;
            if(t == 2){
                y2++;
            }else if(t == 3){
                x2++;
            }

            updateBoard(x1, y1, x2, y2, 0); // 아래쪽(초록색) 쪽으로 타일 움직이기
            updateBoard(x1, y1, x2, y2, 1); // 오른쪽(파란색) 쪽으로 타일 움직이기

            checkGreen();
            checkBlue();

            updateGreen();
            updateBlue();
        }
        calculateCnt();

        System.out.println(score);
        System.out.println(cnt);
    }

    public static void calculateCnt() {
        for(int x= 4; x < 10; x++){
            for(int y = 0; y < 4; y++){
                if(board[x][y] == 0){
                    cnt++;
                }
            }
        }
        for(int x= 0; x < 4; x++){
            for(int y = 4; y < 10; y++){
                if(board[x][y] == 0){
                    cnt++;
                }
            }
        }
    }

    public static void updateBoard(int x1, int y1, int x2, int y2, int d) {
        int nx1 = x1;
        int ny1 = y1;
        int nx2 = x2;
        int ny2 = y2;

        while(true){
            nx1 += dx[d];
            ny1 += dy[d];
            nx2 += dx[d];
            ny2 += dy[d];

            if (checkPoint(nx1, ny1, nx2, ny2)) {
                board[x1][y1] = 0;
                board[x2][y2] = 0;
                break;
            }

            x1 = nx1;
            y1 = ny1;
            x2 = nx2;
            y2 = ny2;
        }
    }

    // 열 4~9, 행: 0~3
    public static void checkGreen() {
        boolean flag = false;
        for(int x = 9; x >= 6; x--){
            if(board[x][0] == 0 && board[x][1] == 0 && board[x][2] == 0 && board[x][3] == 0){
                flag = true;
                score++;

                for(int x1 = x; x1 > 4; x1--){
                    board[x1][0] = board[x1 - 1][0];
                    board[x1][1] = board[x1 - 1][1];
                    board[x1][2] = board[x1 - 1][2];
                    board[x1][3] = board[x1 - 1][3];
                }
                board[4][0] = 3;
                board[4][1] = 3;
                board[4][2] = 3;
                board[4][3] = 3;

                break;
            }
        }
        if(flag){
            checkGreen(); // 초록색에 더 이상 꽉찬 행이 없을때까지 탐색
        }
    }

    // 연한 칸
    // 열 4~5, 행: 0~3
    public static void updateGreen() {
        while (true) {
            if(board[4][0] == 0 || board[4][1] == 0 || board[4][2] == 0 || board[4][3] == 0 ||
                    board[5][0] == 0 || board[5][1] == 0 || board[5][2] == 0 || board[5][3] == 0){
                for(int x1 = 9; x1 > 4; x1--){
                    board[x1][0] = board[x1 - 1][0];
                    board[x1][1] = board[x1 - 1][1];
                    board[x1][2] = board[x1 - 1][2];
                    board[x1][3] = board[x1 - 1][3];
                }
                board[4][0] = 3;
                board[4][1] = 3;
                board[4][2] = 3;
                board[4][3] = 3;
            }else{
                return;
            }
        }
    }

    // 열 0~3, 행: 4~9
    public static void checkBlue() {
        boolean flag = false;
        for(int y = 9; y >= 6; y--){
            if(board[0][y] == 0 && board[1][y] == 0 && board[2][y] == 0 && board[3][y] == 0){
                flag = true;
                score++;

                for(int y1 = y; y1 > 4; y1--){
                    board[0][y1] = board[0][y1 - 1];
                    board[1][y1] = board[1][y1 - 1];
                    board[2][y1] = board[2][y1 - 1];
                    board[3][y1] = board[3][y1 - 1];
                }
                board[0][4] = 2;
                board[1][4] = 2;
                board[2][4] = 2;
                board[3][4] = 2;

                break;
            }
        }
        if(flag){
            checkBlue(); // 파란색에 더 이상 꽉찬 열이 없을때까지 탐색
        }
    }

    // 연한 칸
    // 열: 0~3 핼 4~5,
    public static void updateBlue() {
        while (true) {
            if(board[0][4] == 0 || board[1][4] == 0 || board[2][4] == 0 || board[3][4] == 0 ||
                    board[0][5] == 0 || board[1][5] == 0|| board[2][5] == 0 || board[3][5] == 0){
                for(int y1 = 9; y1 > 4; y1--){
                    board[0][y1] = board[0][y1 - 1];
                    board[1][y1] = board[1][y1 - 1];
                    board[2][y1] = board[2][y1 - 1];
                    board[3][y1] = board[3][y1 - 1];
                }
                board[0][4] = 2;
                board[1][4] = 2;
                board[2][4] = 2;
                board[3][4] = 2;
            }else{
                break;
            }
        }
    }

    public static boolean checkPoint(int x1, int y1, int x2, int y2) {
        if(x1 < 0 || y1 < 0 || x1 >= 10 || y1 >= 10 ||
                x2 < 0 || y2 < 0 || x2 >= 10 || y2 >= 10){
            return true;
        }else{
            if (board[x1][y1] == 0 || board[x2][y2] == 0) {
                return true;
            }

            return false;
        }
    }
}
