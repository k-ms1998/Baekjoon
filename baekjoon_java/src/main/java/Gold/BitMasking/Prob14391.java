package Gold.BitMasking;

import java.io.*;
import java.util.*;

/**
 * Gold 3(종이 조각)
 *
 * https://www.acmicpc.net/problem/14391
 *
 * Solution: 완전탐색 + 비트마스킹
 * 1. 1<=n<=4, 1<=m<=4 이므로, 1<=n*m<=16
 * 2. 그러므로, n*m 배열을 최대 16자리인 비트로 표현 가능
 *  -> 123
 *     456
 *     789 => 123 456 789
 * 3. 가로로 확인할 숫자는 0, 세로로 확인할 숫자는 1이라고 할때:
 *  000     111
 *  000  ~  111
 *  000     111 를 확인해야 함 => 000 000 000 ~ 111 111 111 확인
 *  ex: 문제에서 주어진 예제처럼 주어진 배열이
 *   4937
 *   2591
 *   3846
 *   9150 일때, 문제 예시처럼 나누면
 *   => 0001
 *      1111
 *      1111
 *      0010 => 493 + 7160 + 23 + 58 + 9 + 45 + 91 = 7879 가됨
 *      
 * 비트마스킹을 통해, 배열을 나누느 모든 경우의 수를 쉽게 찾을 수 있음
 */
public class Prob14391 {

    static int n, m;
    static int[][] grid;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        grid = new int[n][m];
        for (int y = 0; y < n; y++) {
            String cur = br.readLine();
            for(int x = 0; x < m; x++){
                grid[y][x] = Integer.parseInt(String.valueOf(cur.charAt(x)));
            }
        }

        /**
         * n = 3, m = 3 이면:
         * 000 000 000 ~ 111 111 111 모두 탐색
         */
        int ans = 0;
        int maxBit = (1 << (n * m));
        for (int bit = 0; bit <= maxBit; bit++) {
            // 1. 가로 찾기
            int tmp = 0;
            for(int y = 0; y < n; y++){
                int cnt = 0;
                for(int x = 0; x < m; x++){
                    int idx = m * y + x; // (x, y)의 인덱스 = m *  y + x
                    /*
                     123    idx = 012
                     456 ->       345 (5 = 1*3 + 2)
                     */
                    if((bit & (1 << idx)) == 0){
                        cnt = 10 * cnt + grid[y][x];
                    }else{
                        /*
                         중간에 1을 만나면, 가로로 더 이상 탐색하는 것을 멈춰야함
                           ex: 1234         0010
                               5678를 나눠서  0010 가 됐으며, 세로로 탐색을 핼때,
                                            1,2탐색 후 4로 넘어가기 전에 3은 세로로 탐색을 하도록 나눴기 때문에 멈춰줘야함
                                            -> 12 + 37 + 4 + 67 + 8
                        */
                        tmp += cnt;
                        cnt = 0;
                    }
                }
                tmp += cnt;
            }
            // 2. 세로 찾기
            for(int x = 0; x < m; x++){
                int cnt = 0;
                for(int y = 0; y < n; y++){
                    int idx = m * y + x;
                    if((bit & (1 << idx)) != 0){
                        cnt = 10 * cnt + grid[y][x];
                    }else{
                        /*
                        가로 탐색과 마찬가지로, 가로로 탐색중인데 세로로 나눈 부분을 만나면 멈춰줘야함
                         */
                        tmp += cnt;
                        cnt = 0;
                    }
                }
                tmp += cnt;
            }
            ans = Math.max(ans, tmp);
        }

        System.out.println(ans);
    }
}
