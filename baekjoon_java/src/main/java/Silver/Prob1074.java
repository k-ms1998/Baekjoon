package Silver;

import java.util.Scanner;

/**
 * Silver 1
 *
 * https://www.acmicpc.net/problem/1074
 *
 * 입력
 * 첫째 줄에 정수 N, r, c가 주어진다.
 *
 * 출력
 * r행 c열을 몇 번째로 방문했는지 출력한다.
 */
public class Prob1074 {

    public static int ans = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String cond = sc.nextLine();
        String[] condSplit = cond.split(" ");
        int n = Integer.valueOf(condSplit[0]);
        int r = Integer.valueOf(condSplit[1]);
        int c = Integer.valueOf(condSplit[2]);

        calculateZ((int) Math.pow(2,n), r, c, 0, 0, 0);
        System.out.println(ans);
    }

    /**
     * 1*1 칸으로 좁혀질때까지 (c, r) 이 몇 사분면에 있는지 확인
     * 1. 재귀로 풀이
     * 2. 재귀로 다음 단계로 넘어갈때마다 n 의 크기는 2배 씩 줄어듬 => ex) n = 8 -> n = 4 -> n = 2 -> n = 1
     *  -> Because, (c, r)이 속한 사분면으로 이동하기 때문에 가로 세로가 반씩 줄어듬
     * 3. 재귀로 다음 단계로 넘어갈때마다 (x, y) 업데이트 시켜줌
     * 3. 1*1 칸이 되면 (c, r) 의 위치 찾음
     * 
     * @param n : 현재 n*n 칸
     * @param r
     * @param c
     * @param x : 현재 n*n 칸의 가장 좌측 상단의 x 좌표
     * @param y : 현재 n*n 칸의 가장 좌측 상단의 y 좌표
     * @param num : (x, y)의 좌표 칸의 숫자
     */
    private static void calculateZ(int n, int r, int c, int x, int y, int num) {
        if (n == 1) {
            ans = num;
            return;
        }

        int tx = n / 2 + x - 1;
        int ty = n / 2 + y - 1;

        /**
         * 규칙에 의해 baseNum 계산 됨
         */
        int baseNum = n * n / 4;
        if (c <= tx && r <= ty) {
            /**
             * 제 1사분면인지 확인
             */
//            System.out.println("1st Quadrant");
            num += (baseNum * 0);
            calculateZ(n / 2, r, c, x, y, num);
        }
        if (c > tx && r <= ty) {
            /**
            * 제 2사분면인지 확인
            */
            num += (baseNum * 1);
//            System.out.println("2nd Quadrant");
            calculateZ(n / 2, r, c, tx + 1, y, num);
        }
        if (c <= tx && r > ty) {
            /**
             * 제 3사분면인지 확인
             */
            num += (baseNum * 2);
//            System.out.println("3rd Quadrant");
            calculateZ(n / 2, r, c, x, ty + 1, num);
        }
        if (c > tx && r > ty) {
            /**
             * 제 4사분면인지 확인
             */
            num += (baseNum * 3);
//            System.out.println("4th Quadrant");
            calculateZ(n / 2, r, c, tx + 1, ty + 1, num);
        }
    }
}
