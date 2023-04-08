package Gold.BitMasking;

import java.io.*;

/**
 * Gold 1(동전 뒤집기)
 *
 * https://www.acmicpc.net/problem/1285
 *
 * Solution: BruteForce + BitMasking
 */
public class Prob1285 {

    static int n;
    static int[][] grid;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(br.readLine());

        grid = new int[n][n];
        for (int y = 0; y < n; y++) {
            String cur = br.readLine();
            for (int x = 0; x < n; x++) {
                if(String.valueOf(cur.charAt(x)).equals("T")){ // H == 0, T == 1
                    grid[y][x] = 1;
                }else{
                    grid[y][x] = 0;
                }
            }
        }

        /**
         * 완전탐색: 비트마스킹으로 모든 경우의 수 확인
         * 0001 -> 1번째 행을 뒤집었을때, 0011 -> 1,2 번쨰 행을 뒤집었을때
         * n = 4이면, 0000 ~ 1111 확인
         * 
         */
        int ans = Integer.MAX_VALUE;
        for (int bit = 0; bit < (1 << n); bit++) {
            int cnt = 0;
            /*
            모든 경우의 수에 대해서, 열에서 시작해서 행을 아래로 내려가면서 확인
            ex: 100
                001
                101 일때 => (0,0) -> (0, 1) -> (0, 2) -> (1, 0) -> (1, 1) -> ... -> (2, 2) 순서대로 확인

                이렇게 각 열을 확인 후, tCnt 값 확인 (tCnt = 현재 열의 뒷면이 위를 보고 있는 동전의 수)
                이때, 만약 뒷면의 수가 앞면의 수보다 많으면 해당 열을 뒤집으면 됨 -> 그러면, 뒷면의 수는 tCnt 에서 n - tCnt가 됨
                    => Because, 항상 뒷면의 수 + 앞면의 수 = n 이 되어야 하기 때문에
             */
            for(int x = 0; x < n; x++){
                int tCnt = 0;
                for(int y = 0; y < n; y++){
                    boolean flipped = (bit & (1 << y)) == (1 << y) ? true : false;
                    if(flipped){
                        if(grid[y][x] == 0){
                            tCnt++;
                        }
                    }else{
                        if(grid[y][x] == 1){
                            tCnt++;
                        }
                    }
                }
                /*
                tCnt = 뒷면의 수, n - tCnt = 앞면의 수
                 => 만약 앞면의 수가 뒷면의 수보다 작으면 현재 열을 뒤집으면 됨
                 -> 그러면 뒷면의 수가 앞면의 수가 됨
                 -> 가장 적은 뒷면의 수를 항상 더할 수 있음
                 */
                cnt += Math.min(tCnt, n-tCnt);
                if(cnt >= ans){
                    cnt = Integer.MAX_VALUE;
                    break;
                }
            }

            ans = Math.min(ans, cnt);
        }

        System.out.println(ans);
    }

}
