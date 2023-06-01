package Gold.Basic;

import java.io.*;
import java.util.*;

/**
 * Gold 3(소용돌이 예쁘게 출력하기)
 *
 * https://www.acmicpc.net/problem/1022
 *
 * Solution: 구현(Simulation)
 * 1. 음수인 좌표들을 양수로 변환해서 생각
 * 2. 소용돌이 만들기
 *  -> 이때, 정사각형의 최대 길이 10000이기 때문에, 최대 10000 * 10000 배열을 생성해야함
 *      -> 메모리 초과 발생
 *          -> 소용돌이는 만들때, 배열을 생성해서 배열에 값을 저장하는 것이 아닌, 현재의 x랑 y좌표를 확인하고,
 *          (r1, c1) ~ (r2, c2) 사이에 있는지 확인
 */
public class Prob1022 {

    static int r1,c1, r2, c2;
    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, -1, 0, 1};
    static int[][] ansGrid;
    static int targetR1;
    static int targetR2;
    static int targetC1;
    static int targetC2;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        r1 = Integer.parseInt(st.nextToken());
        c1 = Integer.parseInt(st.nextToken());
        r2 = Integer.parseInt(st.nextToken());
        c2 = Integer.parseInt(st.nextToken());

        int l = 2 * Math.max(Math.abs(r1),
                Math.max(Math.abs(r2),
                        Math.max(Math.abs(c1), Math.abs(c2)))) + 1;

        int w = c2 - c1;
        int h = r2 - r1;
        ansGrid = new int[h + 1][w + 1];

        targetR1 = r1 + l/2; // 음수 좌표를 양수로 바꾸기 -> l = 7, r1 = -3 일때, r1 + l/2 = 0
        targetR2 = r2 + l/2;
        targetC1 = c1 + l/2;
        targetC2 = c2 + l/2;
        initGrid(l, l/2, l/2, 1, 0, 1);

        int maxNum = 0;
        for(int y = 0; y <= h; y++){
            for (int x = 0; x <= w; x++) {
                maxNum = Math.max(maxNum, ansGrid[y][x]);
            }
        }

        int maxLen = (int) Math.log10(maxNum); // 숫자의 자릿수를 쉽게 구할 수 있음
        StringBuilder ans = new StringBuilder();
        for(int y = 0; y <= h; y++){
            for (int x = 0; x <= w; x++) {
                int len = maxLen - (int) Math.log10(ansGrid[y][x]);
                for (int k = 0; k < len; k++) {
                    ans.append(" ");
                }
                ans.append(String.format("%d", ansGrid[y][x])).append(" ");
            }
            ans.append("\n");
        }

        System.out.println(ans);
    }

    // d == 0 -> 오른쪽, d == 1 -> 위쪽, d == 2 -> 왼쪽, d == 3 -> 아래쪽
    public static void initGrid(int l, int x, int y, int num, int d, int move) {
        if(x >= l || y >= l){
            return;
        }

        for(int i = 0; i < move; i++){
            if(x < 0 || y < 0 || x >= l || y >= l){
                continue;
            }
            if(x >= targetC1 && x <= targetC2 && y >= targetR1 && y <=targetR2){
                int tmpC = x - targetC1;
                int tmpR = y - targetR1;
                ansGrid[tmpR][tmpC] = num;
            }
            x += dx[d];
            y += dy[d];
            num++;
        }

        int nextD = (d + 1) % 4;
        initGrid(l, x, y, num, nextD, nextD == 0 || nextD == 2 ? move + 1 : move);
    }

}
