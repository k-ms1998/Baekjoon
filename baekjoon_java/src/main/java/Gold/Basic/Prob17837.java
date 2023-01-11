package Gold.Basic;

import java.io.*;
import java.util.*;

/**
 * Gold 2(새로운 게임 2)
 *
 * https://www.acmicpc.net/problem/17837
 *
 * Solution: 구현
 */
public class Prob17837 {

    static int n, k;
    static int[][] grid;
    static Deque<Integer>[][] pawns;
    static Pawn[] pawnList;

    static int[] dx = {0, 1, -1, 0, 0};
    static int[] dy = {0, 0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        /**
         * grid: 게임판
         * pawns: 게임판에서 (x, y) 위치에 순서대로 쌓인 말들
         * pawnList: 1~k 번째 말들에 대한 정보
         */
        grid = new int[n + 1][n + 1];
        pawns = new Deque[n + 1][n + 1];
        pawnList = new Pawn[k + 1];
        for (int y = 1; y < n + 1; y++) {
            st = new StringTokenizer(br.readLine());
            for (int x = 1; x < n + 1; x++) {
                grid[y][x] = Integer.parseInt(st.nextToken());
                pawns[y][x] = new ArrayDeque<>();
            }
        }

        for (int i = 1; i < k + 1; i++) {
            st = new StringTokenizer(br.readLine());
            int y = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());

            pawnList[i] = new Pawn(x, y, d);
            pawns[y][x].offer(i);
        }

        int cnt = 0;
        boolean isNotOver = true;
        while(isNotOver && cnt < 1000){
            ++cnt;
            /**
             * 문제의 조건대로 1번 말부터 차례대로 이동 시킴
             */
            for (int i = 1; i < k + 1; i++) {
                int num = i;
                int x = pawnList[i].x;
                int y = pawnList[i].y;
                int d = pawnList[i].d;

                int nx = x + dx[d];
                int ny = y + dy[d];

                /**
                 * 먼저, 이동하려는 칸이 범위 밖 또는 파란색인지 확인
                 * 이동 할려는 칸이 범위 밖 또는 파란색이면 방향을 반대 방향으로 바꿈
                 */
                if(nx <= 0 || ny <= 0 || nx > n || ny > n){
                    d = changeDirection(d);
                    pawnList[num].d = d;
                    nx = x + dx[d];
                    ny = y + dy[d];
                }else if(grid[ny][nx] == 2){
                    d = changeDirection(d);
                    pawnList[num].d = d;
                    nx = x + dx[d];
                    ny = y + dy[d];
                }

                /**
                 * 다시 한번 이동할려는 칸 확인
                 * 1. 범위 밖이면 무시
                 *
                 * 2. 흰색칸(0)이면 현재 탐색 중인 말을 포함한 위로 쌓인 말들을 옮기면 됨
                 *  -> 그로므로, 현재 말이 놓인 칸에서 탐색 중인 말보다 아래에 있는 말들은 임시로 tmp에 저장
                 *  -> 그러고 나서, 탐색 중인 말부터 위로 쌓인 말들을 순서대로 이동할 흰색 칸에 이동
                 *  -> tmp 쌓인 말들을 순서대로 현재 칸에 원상 복구
                 *
                 * 3. 빨간색칸(1)이면, 재 탐색 중인 말을 포함한 위로 쌓인 말들을 역순으로 옮기면 됨
                 *  -> 그러므로, pollLast()로 위에서 부터 이동시키는데, 현재 탐색 중인 말에 도달하면 멈춤
                 *  
                 * 4. 파란색칸(2)이면 무시
                 */
                Deque<Integer> curPawns = pawns[y][x];
                if(nx <= 0 || ny <= 0 || nx > n || ny > n){
                    continue;
                }
                if (grid[ny][nx] == 0) { // 이동 할려는 칸이 흰색 칸
                    int tmpLength = 0;
                    int[] tmp = new int[curPawns.size()];
                    while (!curPawns.isEmpty()) {
                        int nextPawnIdx = curPawns.peekFirst();
                        if (nextPawnIdx == num) {
                            break;
                        }
                        tmp[tmpLength] = curPawns.pollFirst();
                        ++tmpLength;
                    }
                    while(!curPawns.isEmpty()){
                        int nextPawnIdx = curPawns.pollFirst();
                        pawnList[nextPawnIdx].x = nx;
                        pawnList[nextPawnIdx].y = ny;
                        pawns[ny][nx].offer(nextPawnIdx);
                    }
                    for (int t = 0; t < tmpLength; t++) {
                        curPawns.offer(tmp[t]);
                    }
                } else if (grid[ny][nx] == 1) { // 이동 할려는 칸이 빨간색 칸
                    while(!curPawns.isEmpty()){
                        int nextPawnIdx = curPawns.pollLast();
                        pawnList[nextPawnIdx].x = nx;
                        pawnList[nextPawnIdx].y = ny;
                        pawns[ny][nx].offer(nextPawnIdx);
                        if (nextPawnIdx == num) {
                            break;
                        }
                    }
                }

                /**
                 * 어느 시점이든 어떤 칸에 4개 이상의 말이 쌓이면 종료
                 */
                if (pawns[ny][nx].size() >= 4) {
                    isNotOver = false;
                    break;
                }
            }
        }

        /**
         * 턴 횟수가 1000 이상이면 -1 출력
         */
        System.out.println(cnt >= 1000 ? -1 : cnt);
    }

    public static int changeDirection(int d) {
        if(d == 1){
            return 2;
        }else if(d == 2){
            return 1;
        } else if (d == 3) {
            return 4;
        }else if(d == 4){
            return 3;
        }

        return d;
    }

    public static class Pawn{
        int x;
        int y;
        int d;

        public Pawn(int x, int y, int d) {
            this.x = x;
            this.y = y;
            this.d = d;
        }

    }
}
