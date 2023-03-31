package Gold;

import java.io.*;
import java.util.*;

/**
 * Gold 2(청소년 상어)
 *
 * https://www.acmicpc.net/problem/19236
 * 
 * Solution: 구현 + 백트래킹
 * 
 * 1. 상어를 (0,0)에 배치하고, (0,0)에 있던 방향으로 상어가 바라봄
 * 2. 물고기들 움직임
 *  -> 1번부터 16번 물고기까지 순서대로 움직임
 *  -> 이미 잡아 먹힌 물고기임 건너뛰기(gone = true)
 *  -> 물고기가 현재 바라보는 방향으로 움직일려고 할때, 해당 칸이 경계 밖이거나, 상어가 있는 칸이면 반시계 방향으로 회전
 *      -> 그러므로, dx랑 dy를 반시계 방향으로 회전되도록 선언 
 *   -> 현재 물고기가 바라보는 방향 업데이트
 *   -> 물고리가 움직일 다음 칸이 빈칸이면 현재 물고기의 위치만 업데이트
 *   -> 다른 물고기가 있는 칸이면, 두 물고기의 위치 둘 다 업데이트
 * 3. 상어가 바라보는 방향으로 이동
 *  -> 해다 방향에 있는 모든 물고기들은 잡아 먹을 수 있음
 *  -> 빈 칸이면 건너뛰기
 *  -> 경계 밖으면 탐색 종료
 */
public class Prob19236 {

    static int[][] orgGrid = new int[4][4];
    static Fish[] orgFishes = new Fish[17];
    static int[] dx = {0, -1, -1, -1, 0, 1, 1, 1};
    static int[] dy = {-1, -1, 0, 1, 1, 1, 0, -1};

    static int answer = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        for (int y = 0; y < 4; y++) {
            st = new StringTokenizer(br.readLine());
            for (int x = 0; x < 4; x++) {
                int num = Integer.parseInt(st.nextToken());
                int dir = Integer.parseInt(st.nextToken()) - 1;

                orgGrid[y][x] = num;
                orgFishes[num] = new Fish(num, x, y, dir, false);
            }
        }

        int cnt = orgGrid[0][0];
        int curDir = orgFishes[orgGrid[0][0]].dir;
        orgFishes[orgGrid[0][0]].gone = true;
        orgGrid[0][0] = -1; // -1은 상어

        findAnswer(0, 0, curDir, cnt, orgGrid, orgFishes);

        System.out.println(answer);
    }

    // x, y => 상어의 위치; d => 상어가 바라보는 방향; cnt => 현재까지 잡아먹은 물고기 번호들의 총 합
    public static void findAnswer(int x, int y, int d, int cnt, int[][] grid, Fish[] fishes) {
        answer = Math.max(answer, cnt);

        // 물고기들의 위치와 정보 복사
        int[][] copyGrid = new int[4][4];
        Fish[] copyFishes = new Fish[17];
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                copyGrid[i][j] = grid[i][j];
            }
        }
        for(int i = 1; i <= 16; i++){
            copyFishes[i] = new Fish(i, fishes[i].x, fishes[i].y, fishes[i].dir, fishes[i].gone);
        }

        moveFishes(copyGrid, copyFishes);

        int m = 0;
        while (true) {
            m++;
            int nx = x + m * dx[d];
            int ny = y + m * dy[d];
            
            // 상어가 경계 밖으로 갈때까지 이동
            if (nx < 0 || ny < 0 || nx >= 4 || ny >= 4) {
                break;
            }
            if (copyGrid[ny][nx] == 0) { // 이동할려는 칸이 빈칸이면 건너뛰기(상어는 빈칸으로 이동 X)
                continue;
            }

            int fishNum = copyGrid[ny][nx]; // 이동한 칸의 물고기 번호
            Fish fish = copyFishes[fishNum];

            copyGrid[y][x] = 0; // 상어가 다른 칸으로 이동하면, 현재칸은 빈칸
            copyGrid[ny][nx] = -1; // 상어가 다른 칸으로 이동해서, 해당 칸의 값을 -1로 업데이트
            copyFishes[fishNum].gone = true; // 이동한 칸에 있는 물고기는 잡아 먹힘

            findAnswer(fish.x, fish.y, fish.dir, cnt + fish.num,  copyGrid, copyFishes);

            // 원상 복구
            copyGrid[y][x] = -1;
            copyGrid[ny][nx] = fishNum;
            copyFishes[fishNum].gone = false;
        }
    }

    public static void moveFishes(int[][] grid, Fish[] fishes){
        for (int i = 1; i <= 16; i++) {
            Fish fish = fishes[i];
            if(fish.gone){
                continue;
            }

            int x = fish.x;
            int y = fish.y;
            int dir = fish.dir;
            
            // 움직일 수 있는 칸이 없어서 반시계 방향으로 회전하는데, 결국 다시 처음 방향으로 오게 되면 제자리에 있음
            for (int j = 0; j < 8; j++) {
                int nx = x + dx[dir];
                int ny = y + dy[dir];
                if(nx < 0 || ny < 0 || nx >= 4 || ny >= 4){
                    dir = (dir + 1) % 8; // 반시계 방향으로 회전
                    continue;
                }
                if(grid[ny][nx] == -1){
                    dir = (dir + 1) % 8; // 반시계 방향으로 회전
                    continue;
                }

                int nextFishNum = grid[ny][nx];
                grid[ny][nx] = i;
                grid[y][x] = nextFishNum;
                fishes[i].dir = dir; // 물고기가 바라보는 방향 업데이트

                if (nextFishNum == 0) {
                    // 물고기가 이동할려는 칸이 빈칸이므로, 현재 물고기의 위치만 업데이트
                    fishes[i].x = nx;
                    fishes[i].y = ny;
                } else {
                    // 물고기가 이동할려는 칸에 다른 쿨고기가 있으면 두 물고기의 위치 모두 업데이트
                    fishes[nextFishNum].x = x;
                    fishes[nextFishNum].y = y;
                    fishes[i].x = nx;
                    fishes[i].y = ny;
                }

                break;
            }

        }
    }

    public static class Fish {
        int num;
        int x;
        int y;
        int dir;
        boolean gone;

        public Fish(int num, int x, int y, int dir, boolean gone) {
            this.num = num;
            this.x = x;
            this.y = y;
            this.dir = dir;
            this.gone = gone;
        }

    }
}
